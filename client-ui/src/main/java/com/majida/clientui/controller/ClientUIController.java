package com.majida.clientui.controller;

import com.majida.clientui.entity.*;
import com.majida.clientui.proxies.MicroserviceBookProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ClientUIController {

    private static final Logger LOGGER = LogManager.getLogger(ClientUIController.class);

    // créer une instance
    @Autowired
    private MicroserviceBookProxy microserviceBookProxy;


    /**
     * HOME PART
     */

    /**
     * Get Home page
     * @param model
     * @return Home page
     */
    @RequestMapping("/")
    public String home(Model model) {
        LOGGER.info("get homepage");
//appeler le microservice BookProxy et la méthode getbooks
        //cet appel va générer la requete Http adéquate, va communiquer avec le microservice book
        //récupérer la liste et la mettre dans une liste des objets books et noua l a renvoyé
        List<Book> books = microserviceBookProxy.getBooks();
        //pour passer cette liste des books vers la template
        model.addAttribute("books", books);

        List<Category> categories = microserviceBookProxy.getCategories();
        model.addAttribute("categories", categories);

        return "home";
    }

    /**
     * BOOK PART
     */

    @RequestMapping(value = {"/book/{id}"}, method = RequestMethod.GET)
    public String site(@PathVariable("id") final Long id, Model model) {
        Book book = microserviceBookProxy.getBook(id);
        model.addAttribute("book", book);

        List<Copy> copies = microserviceBookProxy.getCopiesById(id);
        model.addAttribute("copies", copies);
        return "book";
    }

}
