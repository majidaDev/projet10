package com.majida.mbook.service;

import com.majida.mbook.entity.*;
import com.majida.mbook.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CopyRepository copyRepository;
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ReservationRepository reservationRepository;



    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        bookRepository.findAll()
                .forEach(books::add);
        return books;
    }

    public List<Book> getBooksByAuthor(String author){
        List<Book> books = bookRepository.findByAuthorLike(author);
        return books;
    }

    public List<Book> getBooksByKeyword(String keyword){
        List<Book> books = bookRepository.findByKeywordLike(keyword);
        return books;
    }
    public void nearestreturnDate (Book book) {

        List<Date> dates = new ArrayList<Date>();

        List<Copy> copies = copyRepository.getAllCopiesByBookId(book.getId());

        for (Copy copie : copies) {
            List<Loan> loans = loanRepository.findByBookCopyAndStatus(copie, Arrays.asList(Status.EnCours, Status.Renouvele, Status.EnRetard));
            if(loans.size() > 0) {
                Loan loan = loans.get(0);
                dates.add(loan.getDeadline());
            }
        }

        Collections.sort(dates);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (dates.size() > 0) {
            Date nearestreturnDate = dates.get(0);

            book.setNearestreturnDate(dateFormat.format(nearestreturnDate));
        } else {
            LocalDate ld = LocalDate.of( 2001 , 01 , 01 ) ;
            book.setNearestreturnDate(dateFormat.format(java.sql.Date.valueOf(ld)));
        }
    }

    public void numberOfReservation (Book book) {

        List<Reservation> reservations = reservationRepository.findReservationByBookAndStatusOrderByDateCreate(book, Status.Waiting);

        book.setNumberOfReservation(reservations.size());

    }

    public void reservationBook (Book book) {

        List<Reservation> reservations = reservationRepository.findReservationByBookAndStatusOrderByDateCreate(book, Status.Waiting);

        if(reservations.size() > 0) {
            book.setIsReservation(true);
        } else {
            book.setIsReservation(false);
        }
    }

    public Optional <Book> getBook(Long id) {
        return bookRepository.findById(id);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Long id, Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
