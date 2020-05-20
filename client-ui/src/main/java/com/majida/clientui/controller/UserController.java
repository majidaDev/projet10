package com.majida.clientui.controller;

import com.majida.clientui.entity.Copy;
import com.majida.clientui.entity.Loan;
import com.majida.clientui.entity.Person;
import com.majida.clientui.entity.Reservation;
import com.majida.clientui.proxies.MicroservicePersonProxy;
import com.majida.clientui.proxies.MicroserviceBookProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private MicroservicePersonProxy microservicePersonProxy;
    @Autowired
    private MicroserviceBookProxy microserviceBookProxy;

    /**
     * USER MANAGEMENT
     */

    /**
     * Post create new person
     *
     * @param session
     * @param redirectAttributes
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     */
    @RequestMapping(value = {"/newPerson"}, method = RequestMethod.POST)
    public ModelAndView setPerson(
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        if (password.length() < 8) {
            LOGGER.info("message", "Le mot de passe est trop petit");
            redirectAttributes.addFlashAttribute(
                    "messageFail", "Le mot de passe est trop petit");
            return new ModelAndView("redirect:/");
        } else {
            microservicePersonProxy.setPerson(firstname, lastname, email, password);
            redirectAttributes.addFlashAttribute("messageSuccess", "Inscription finalisée avec succès. Veuillez vous connecter :) ");
        }
        return new ModelAndView("redirect:/");
    }

    /**
     * Signin method
     *
     * @param session
     * @param model
     * @param redirectAttributes
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = {"/signin"}, method = RequestMethod.POST)
    public String signIn(
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        Person person = microservicePersonProxy.signIn(email, password);

        if (person == null) {
            redirectAttributes.addFlashAttribute(
                    "messageFail", "L'username ou le mot de passe est mauvais");
            return "redirect:/";
        }

        model.addAttribute("person", person);
        session.setAttribute("id", person.getId());
        session.setAttribute("firstname", person.getFirstname());
        session.setAttribute("lastname", person.getLastname());
        redirectAttributes.addFlashAttribute(
                "messageFail", "Votre Espace Privé :) !");
        return "redirect:/person/" + session.getAttribute("id");
    }

    /**
     * Post signout
     *
     * @param redirectAttributes
     * @param session
     * @return
     */
    @RequestMapping(value = {"/signout"}, method = RequestMethod.GET)
    public String signOut(
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        LOGGER.debug("Deconnexion page OK");
        session.removeAttribute("firstname");
        session.removeAttribute("lastname");
        session.removeAttribute("id");
        LOGGER.debug("message", "Vous êtes déconnecté");
        redirectAttributes.addFlashAttribute(
                "messageSuccess", "Déconnexion avec succès :)");

        return "redirect:/";
    }

    /**
     * Return user page with his informations
     *
     * @param model
     * @param session
     * @param id
     */
    @RequestMapping("/person/{id}")
    public ModelAndView person(
            Model model,
            HttpSession session,
            @PathVariable("id") final Long id

    ) {
        LOGGER.info("get homepage");

        Person person = microservicePersonProxy.getPersonPage(id);
        model.addAttribute("person", person);

        List<Loan> loans = microserviceBookProxy.getLoansById(id);
        model.addAttribute("loans", loans);

        return new ModelAndView("connectedPage");
    }

    /**
     * Extend loan
     *
     * @param session
     * @param redirectAttributes
     * @param loanId
     * @return
     */
    @RequestMapping("/extendLoan/{loanId}")
    public ModelAndView person(
            HttpSession session,
            RedirectAttributes redirectAttributes,
            @PathVariable("loanId") final Long loanId
    ) {
        LOGGER.info("get homepage");

        Loan loan = microserviceBookProxy.extendLoan(loanId);

        redirectAttributes.addFlashAttribute(
                "messageSuccess", "Emprunt prolongé avec succès :)");

        return new ModelAndView("redirect:/person/" + session.getAttribute("id"));
    }


    /**
     * Set a loan by copy id
     *
     * @param bookId
     * @return Loan
     */
    @RequestMapping(value = {"/loan"})
    public ModelAndView person(
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("bookId") Long bookId
    ) {
        Loan loan = microserviceBookProxy.setLoan(bookId);
        redirectAttributes.addFlashAttribute(
                "messageSuccess", "Emprunt prolongé avec succès :)");
        model.addAttribute("loan", loan);
        return new ModelAndView("loan");
    }

/*

    @RequestMapping(value = {"/reservations"})
    public ModelAndView person(
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {


        List<Reservation> reservations = microserviceBookProxy.listreservations();

        model.addAttribute("reservations", reservations);

        return new ModelAndView("redirect:/person/" + session.getAttribute("id"));

    }
*/
/**
 * add  reservation
 *
 * @param idPerson
 * @param idBook
 * @return Reservation
 *//*

        @RequestMapping(value = {"/reservation/addReservation"}, method = RequestMethod.POST)
        public String addReservation (HttpSession session,
                                      Model model,
                                      RedirectAttributes redirectAttributes,
                                      @RequestParam("idPerson") Long idPerson,
                                      @RequestParam("idBook") Long idBook)
        {


            if (session.getAttribute("id") != null) {
                Person person = microservicePersonProxy.getPersonPage(idPerson);
                try {
                    microserviceBookProxy.addReservation(person.getId(), idBook);
                    String acceptMessage = "Votre demande de réservation a bien été pris en compte.";
                    redirectAttributes.addFlashAttribute("acceptMessage", acceptMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (e instanceof Exception) {
                        String message = e.getMessage();
                        redirectAttributes.addFlashAttribute("errorMessage", message);
                    }
                }
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Merci de vous connecter pour effectuer une réservation.");
            }

            return "book";
        }

    @RequestMapping(value = {"/reservation/{id}/delete-reservation"}, method = RequestMethod.POST)
    public String deleteReservation(HttpSession session,
                                    Model model,
                                    RedirectAttributes redirectAttributes,
                                    @PathVariable("id") Long id) {


        microserviceBookProxy.deleteReservationByPerson(id);

        return "connectedPage";
    }
*/


}
