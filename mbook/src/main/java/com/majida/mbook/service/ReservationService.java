package com.majida.mbook.service;


import com.majida.mbook.entity.Reservation;
import com.majida.mbook.entity.Status;
import com.majida.mbook.exception.BookNotFoundException;
import com.majida.mbook.repository.BookRepository;
import com.majida.mbook.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private  CopyService copyService;

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<Reservation>();
        reservationRepository.findAll()
                .forEach(reservations::add);
        return reservations;
    }
    public List<Reservation> getReservationByPersonId(Long id) {
        List<Reservation> reservations = reservationRepository.getAllReservationsPersonId(id);
        return reservations;
    }
    public List<Reservation> getReservationByBookId(Long id) {
        List<Reservation> reservations = reservationRepository.getAllReservationsBookId(id);
        return reservations;
    }
    public List<Reservation> getAllReservationsBookIdOrOrderByDate(Long id) {
        List<Reservation> reservations = reservationRepository.getAllReservationsBookIdOrOrderByDate(id);
        return reservations;
    }

    public Optional<Reservation> getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public void addReservation(Reservation person) {
        reservationRepository.save(person);
    }

    public void updateReservtaion(Long id, Reservation person) {
        reservationRepository.save(person);
    }

    public Reservation deleteReservationByPerson(Long id) {
        // Find reservation by id
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if (!reservation.isPresent())
            throw new BookNotFoundException("La réservation avec l'id : " + id + " n'a pas été retrouvé.");

        Reservation reservationend = reservation.get();

        reservationend.setStatus(Status.AnnuleP);

        // Save the reservation in the database
        reservationRepository.save(reservationend);

        return reservationend;
    }
    public void deleteReservationAfterMail(Long id) {
        // Find reservation
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if (!reservation.isPresent())
            throw new BookNotFoundException("La réservation avec l'id : " + id + " n'a pas été retrouvé.");

        Reservation reservationnd = reservation.get();

        reservationnd.setStatus(Status.AnnuleS);

        // Save the reservation in the database
        reservationRepository.save(reservationnd);
    }
}
