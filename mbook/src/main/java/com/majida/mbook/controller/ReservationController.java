package com.majida.mbook.controller;

import com.majida.mbook.entity.*;
import com.majida.mbook.exception.BookNotFoundException;
import com.majida.mbook.repository.BookRepository;
import com.majida.mbook.repository.LoanRepository;
import com.majida.mbook.repository.ReservationRepository;
import com.majida.mbook.service.BookService;
import com.majida.mbook.service.ReservationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class ReservationController {


    private static final Logger LOGGER = LogManager.getLogger(ReservationController.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReservationService reservationService;


    /**
     * Get all reservations
     *
     * @return List<Reservation>
     */

    @RequestMapping(value = {"/reservations"}, method = RequestMethod.GET)
    public List<Reservation> listreservations() {

        List<Reservation> reservations = reservationService.getAllReservations();

        if (reservations.isEmpty()) throw new BookNotFoundException("Aucune réservation dans la base de données.");

        return reservations;
    }

    /**
     * add  reservation
     *
     * @param idPerson
     * @param idBook
     * @return Reservation
     */
    @RequestMapping(value = {"/reservation/addReservation"}, method = RequestMethod.POST)
    public Reservation addReservation(@RequestParam Long idPerson, @RequestParam Long idBook) {

        //Create object Reservation
        Reservation reservation = new Reservation();

        //Setting  reservation

        reservation.setBook(bookService.getBook(idBook).get());

        reservation.setIdPerson(idPerson);

        //Setting the maximum number of reservations
        Integer maxreservation = (reservation.getBook().getCopies().size()) * 2;

        //Check if person has not already reserv this book
        List<Reservation> reservationBook = reservationRepository.findReservationByBookAndStatusOrderByDateCreate(reservation.getBook(), Status.Waiting);

        for (Reservation reservationr : reservationBook) {
            if ((reservationr.getIdPerson()) == (reservation.getIdPerson())) {
                throw new BookNotFoundException("Réservation déja faite pour ce livre ");
            }
        }

        //Check if person has not already loan this book
        List<Loan> loansPerson = loanRepository.findByIdPersonAndStatus(idPerson, Arrays.asList(Status.EnCours, Status.Renouvele, Status.EnRetard));

        for (Loan loanl : loansPerson) {
            if ((loanl.getCopy().getBook().getId()).equals(reservation.getBook().getId())) {
                throw new BookNotFoundException("vous avez déja un emprunt en cours pour ce livre");
            }
        }

        //Checks that if the maximum number of reservation  is attain
        if (reservationBook.size() >= maxreservation) {
            throw new BookNotFoundException("le nombre maximum de réservation est atteint");
        }

        LocalDate localDate = LocalDate.now();

        LocalDate ld = LocalDate.of(2001, 01, 01);

        reservation.setDateCreate(java.sql.Date.valueOf(localDate));

        reservation.setDateMail(java.sql.Date.valueOf(ld));

        reservation.setStatus(Status.Waiting);

        Reservation newreservation = reservationRepository.save(reservation);

        if (newreservation == null) throw new BookNotFoundException("Exception");

        return newreservation;
    }
    /**
     * Get all reservations by person id
     * @param personId
     * @return Set<Reservation>
     */
    @RequestMapping(value = {"/reservation/{idUser}"}, method = RequestMethod.GET)
    public List<ReservationPerson> listReservationsByPerson(@PathVariable int personId) {

        List<Reservation> reservationsperson = reservationRepository.findByIdPersonAndStatusOrderByDateCreate(personId, Status.Waiting);

        List<ReservationPerson> reservations = new ArrayList<ReservationPerson>();

        for (Reservation reservation : reservationsperson) {
            ReservationPerson reservationD = new ReservationPerson();

            reservationD.setId(reservation.getId());
            reservationD.setIdPerson(reservation.getIdPerson());
            reservationD.setBookTitle(reservation.getBook().getTitle());
            reservationD.setStatus(reservation.getStatus().stateName);

            List<Reservation> reservationList = reservationRepository.findReservationByBookAndStatusOrderByDateCreate(reservation.getBook(), Status.Waiting);

            for (int i = 0; i < reservationList.size(); i++) {
                if (reservationList.get(i).getId().equals(reservationD.getId())) {
                    reservationD.setPosition(i+1);
                    break;
                }
            }

            bookService.nearestreturnDate(reservation.getBook());

            reservationD.setNearestreturnDate(reservation.getBook().getNearestreturnDate());

            reservations.add(reservationD);
        }


        return reservations;
    }

    /**
     * delete reservation
     *
     * @param id reservation
     */
    @RequestMapping(value = {"/reservation/{id}/delete-reservation"}, method = RequestMethod.POST)
    public Reservation deleteReservationByPerson(@PathVariable Long id) {

        Reservation reservation = reservationService.deleteReservationByPerson(id);

        return reservation;
    }

    /**
     * Get all reservation by person id
     *
     * @param personId
     * @return Set<Loan>
     */
    @RequestMapping(value = {"/myreservations/{personId}"}, method = RequestMethod.GET)
    public List<Reservation> listReservationsPerson(@PathVariable Long personId) {

        LOGGER.info("getReservationsById was called");
        List<Reservation> reservations;
        try {
            reservations = reservationService.getReservationByPersonId(personId);
        } catch (Exception e) {
            LOGGER.error("There is no reservation in database with this person id " + personId + " " + e);
            throw new BookNotFoundException("There is no reservations in database with this person id " + personId);
        }


        return reservations;
    }

}
