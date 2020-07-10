package com.majida.clientui.proxies;

import com.majida.clientui.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Declare Feign Client
 */
@FeignClient(contextId = "microserviceBookProxy", name = "zuul-server")
public interface MicroserviceBookProxy {
    /**
     * Get all books
     * @return Set<Book>
     */
    @RequestMapping(value = {"/microservice-book/books"}, method = RequestMethod.GET)
    List<Book> getBooks();

    /**
     * Get book by id
     * @param id
     * @return Book
     */
    @RequestMapping(value = {"/microservice-book/book/{id}"}, method = RequestMethod.GET)
    Book getBook( @PathVariable Long id);

    /**
     * Get all copies of a book by his id
     * @param id
     * @return Set<Copy>
     */
    @RequestMapping(value = {"/microservice-book/copies/{id}"}, method = RequestMethod.GET)
    List<Copy> getCopiesById(@PathVariable Long id);

    /**
     * Get all categories
     * @return List<Category>
     */
    @RequestMapping(value = {"/microservice-book/categories"}, method = RequestMethod.GET)
    List<Category> getCategories();

    /**
     * Get books by category
     * @param categoryId
     * @return List<Book>
     */
    @RequestMapping(value = {"/microservice-book/category/{categoryId}"}, method = RequestMethod.GET)
    List<Book> getBooksByCategoryId(@PathVariable Long categoryId);

    /**
     * Get books by category
     * @param categoryId
     * @return Set<Book>
     */
    @RequestMapping(value = {"/microservice-book/category"}, method = RequestMethod.POST)
    List<Book> getBooksByCategory(@RequestParam Long categoryId);

    /**
     * Get books by author
     * @param author
     * @return Set<Book>
     */
    @RequestMapping(value = {"/microservice-book/author"}, method = RequestMethod.POST)
    List<Book> getBooksByAuthor(@RequestParam String author);

    /**
     * Get books by keyword
     * @param keyword
     * @return Set<Book>
     */
    @RequestMapping(value = {"/microservice-book/search"}, method = RequestMethod.POST)
    List<Book> getBooksByKeyword(@RequestParam String keyword);

    /**
     * Get all loans by person id
     * @param personId
     * @return Set<Loan>
     */
    @RequestMapping(value = {"/microservice-book/myLoans/{personId}"}, method = RequestMethod.GET)
    List<Loan> getLoansById(@PathVariable Long personId);

    /**
     * Get all loans
     * @return List<Loan>
     */
    @RequestMapping(value = {"/microservice-book/allLoans"}, method = RequestMethod.GET)
    @ResponseBody
    List<Loan> getAllLoans();

    /**
     * Set a loan by loan
     * @param loan
     * @return Loan
     */
    @RequestMapping(value = {"/microservice-book/loan"}, method = RequestMethod.POST)
    Loan setLoan(@RequestParam Loan loan);

    /**
     * Extend a loan by loan id
     * @param loanId
     * @return Loan
     */
    @RequestMapping(value = {"/microservice-book/extendLoan/{loanId}"}, method = RequestMethod.POST)
    Loan extendLoan(@PathVariable Long loanId);

    /**
     * renewal a loan
     *
     * @param loanId
     * @return Loan
     */
    @RequestMapping( value = {"/microservice-book/renewalLoan/{loanId}/"},method = RequestMethod.POST)
    public Loan renewalLoan(@PathVariable Long loanId);

    /**
     * Get all reservations
     * @return List<Reservation>
     */
    @RequestMapping(value = {"/microservice-book//reservations"}, method = RequestMethod.GET)
    @ResponseBody
    List<Reservation> listreservations();


    /**
     * Get all Reservations by person id
     * @param personId
     * @return Set<Reservation>
     */
    @RequestMapping(value = {"/microservice-book/myReservations/{personId}"}, method = RequestMethod.GET)
    List<Reservation> getReservationsById(@PathVariable Long personId);

/**
 * add  reservation
 *
 * @param idPerson
 * @param idBook
 * @return Reservation
 */
    @RequestMapping(value = {"/microservice-book/reservation/{idBook}/{idPerson}"}, method = RequestMethod.POST)
    Reservation addReservation(@PathVariable Long idPerson, @PathVariable Long idBook);

    /**
     * Get all reservations by person id
     * @param personId
     * @return Set<Reservation>
     */
    @RequestMapping(value = {"/microservice-book/reservation/{personId}"}, method = RequestMethod.GET)
    List<Reservation> listReservationsByPerson(@PathVariable Long personId);

    /**
     * delete reservation by system
     *
     * @param id reservation
     */
    @RequestMapping(value = {"/microservice-book/deleteReservation/{id}"}, method = RequestMethod.POST)
     Reservation deleteReservationByPerson(@PathVariable Long id);

    /**
     * delete reservation by person
     *
     * @param id reservation
     */
    @RequestMapping(value = {"/microservice-book/cancelReservation/{id}"}, method = RequestMethod.POST)
    Reservation cancelReservationByPerson(@PathVariable Long id);
}


