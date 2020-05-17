package com.majida.mbook.repository;

import com.majida.mbook.entity.Book;
import com.majida.mbook.entity.Reservation;
import com.majida.mbook.entity.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {


    @Query(value = "SELECT * FROM Reservation r WHERE r.id_person = ?", nativeQuery = true)
    List<Reservation> getAllReservationsPersonId(Long id);

    @Query(value = "SELECT * FROM Reservation r WHERE r.book_id = ?", nativeQuery = true)
    List<Reservation> getAllReservationsBookId(Long id);

    @Query(value = "SELECT * FROM Reservation r WHERE r.book_id = ? order by date asc ", nativeQuery = true)
    List<Reservation> getAllReservationsBookIdOrOrderByDate(Long id);

    @Query("Select r from Reservation r where r.sendMail =:sendMail and r.status =:status")
    List<Reservation> findReservationBySendMailAndStatus(Boolean sendMail, Status status);

    @Query("Select r from Reservation r where r.book =:book and r.status =:status order by r.dateCreate")
    List<Reservation> findReservationByBookAndStatusOrderByDateCreate(Book book, Status status);

    @Query("Select r from Reservation r where r.IdPerson =:idPerson and r.status =:status order by r.dateCreate")
    List<Reservation> findByIdPersonAndStatusOrderByDateCreate(int idPerson, Status status);

}
