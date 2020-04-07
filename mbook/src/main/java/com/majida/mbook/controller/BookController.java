package com.majida.mbook.controller;

import com.majida.mbook.entity.Book;
import com.majida.mbook.entity.Category;
import com.majida.mbook.entity.Copy;
import com.majida.mbook.entity.Loan;
import com.majida.mbook.exception.BookNotFoundException;
import com.majida.mbook.service.BookService;
import com.majida.mbook.service.CategoryService;
import com.majida.mbook.service.CopyService;
import com.majida.mbook.service.LoanService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

// Ce Controlleur englobe toutes les méthodes qui répondent au URI de notre microservices
@RestController
public class BookController {

    private static final Logger LOGGER = LogManager.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private CategoryService categoryService;

     @Autowired
     private LoanService loanService;

    /**
     * Get list of books by category
     * @param categoryId
     * @return List<Book>
     */
    private List<Book> getListBookByCategory(@PathVariable Long categoryId) {
        Category category;
        try {
            category = categoryService.getCategory(categoryId).get();
        } catch (Exception e) {
            LOGGER.error("There is no category in database for this category id"+categoryId+" "+e);
            throw new BookNotFoundException("There is no category in database for this category id"+categoryId+" "+e);
        }
        List<Book> books;
        try {
            books = category.getBooks();
        } catch (Exception e) {
            LOGGER.error("There is no books in database for this category id"+categoryId+" "+e);
            throw new BookNotFoundException("There is no books in database for this category id"+categoryId+" "+e);
        }
        return books;
    }

    /**
     * Get all books
     * @return List<Book>
     */
    @RequestMapping(value = {"/books"}, method = RequestMethod.GET)
    public  List<Book> getBooks() {
        LOGGER.info("getBooks was called");
        List<Book> books = bookService.getAllBooks();
        if(books == null) {
            LOGGER.error("There is any books in database...");
            throw new BookNotFoundException("There is any books in database...");
        }
        return books;
    }

    /**
     * Get book by id
     * @param id
     * @return Book
     */
    @RequestMapping(value = {"/book/{id}"}, method = RequestMethod.GET)
    public Book getBook(@PathVariable Long id) {
        LOGGER.info("getBook was called");
        Book book = null;
        try {
            book = bookService.getBook(id).orElseThrow (() -> new BookNotFoundException("There is any books in database with this id "+id));
        }
        catch(Exception e) {
            LOGGER.error("There is any book in database with this id "+id+" "+e);
        }
        return book;
    }

    /**
     * Get all copies of a book by his id
     * @param id
     * @return List<Book>
     */
    @GetMapping(value = {"/copies/{id}"})
    public  List<Copy> getCopiesById(@PathVariable Long id) {
        LOGGER.info("getCopiesById was called");
        List<Copy> copies;
        try {
            copies = copyService.getCopiesByBookId(id);
        } catch (Exception e) {
            LOGGER.error("There is no copies in database with this book id "+id+" "+e);
            throw new BookNotFoundException("There is no copies in database with this book id "+id);
        }

        return copies;
    }

    /**
     * Get all categories
     * @return List<Category>
     */
    @RequestMapping(value = {"/categories"}, method = RequestMethod.GET)
    public List<Category> getCategories() {
        LOGGER.info("getCategories was called");
        List<Category> categories;
        try {
            categories = categoryService.getAllCategories();
        } catch (Exception e) {
            LOGGER.error("There is no catagories in database..."+e);
            throw new BookNotFoundException("There is no catagories in database...");
        }
        return categories;
    }

    /**
     * Get books by category
     * @param categoryId
     * @return List<Book>
     */
    @RequestMapping(value = {"/category/{categoryId}"}, method = RequestMethod.GET)
    public List<Book> getBooksByCategoryId(@PathVariable Long categoryId) {
        LOGGER.info("getBooksByCategoryId was called");
        return getListBookByCategory(categoryId);
    }

    /**
     * Get books by category
     * @param categoryId
     * @return List<Book>
     */
    @RequestMapping(value = {"/category"}, method = RequestMethod.POST)
    public List<Book> getBooksByCategory(@RequestParam Long categoryId) {
        LOGGER.info("getBooksByCategory was called");
        Category category;
        try {
            category = categoryService.getCategory(categoryId).get();
        } catch (Exception e) {
            LOGGER.error("There is no category in database for this category id"+categoryId+" "+e);
            throw new BookNotFoundException("There is no category in database for this category id"+categoryId+" "+e);
        }
        List<Book> books;
        try {
            books = category.getBooks();
        } catch (Exception e) {
            LOGGER.error("There is no books in database for this category id"+categoryId+" "+e);
            throw new BookNotFoundException("There is no books in database for this category id"+categoryId+" "+e);
        }
        return books;
    }

    /**
     * Get books by author
     * @param author
     * @return List<Book>
     */
    @RequestMapping(value = {"/author"}, method = RequestMethod.POST)
    public List<Book> getBooksByAuthor(@RequestParam String author) {
        LOGGER.info("getBooksByAuthor was called");
        List<Book> books;
        try {
            books = bookService.getBooksByAuthor(author);
        } catch (Exception e) {
            LOGGER.error("There is no books in database with this author "+author+" "+e);
            throw new BookNotFoundException("There is no books in database with this author "+author+" "+e);
        }
        return books;
    }

    /**
     * Get books by keyword
     * @param keyword
     * @return Set<Book>
     */
    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    public List<Book> getBooksByKeyword(@RequestParam String keyword) {
        LOGGER.info("getBooksByKeyword was called");
        List<Book> books;
        try {
            books = bookService.getBooksByKeyword(keyword);
        } catch (Exception e) {
            LOGGER.error("There is no books in database with this keyword "+keyword+" "+e);
            throw new BookNotFoundException("There is no books in database with this keyword "+keyword+" "+e);
        }
        return books;
    }

    /**
     * Get all loans by person id
     * @param personId
     * @return Set<Loan>
     */
    @RequestMapping(value = {"/myLoans/{personId}"}, method = RequestMethod.GET)
    public List<Loan> getLoansById(@PathVariable Long personId) {
        LOGGER.info("getLoansById was called");
        List<Loan> loans;
        try {
            loans = loanService.getLoansByPersonId(personId);
        } catch (Exception e) {
            LOGGER.error("There is no loans in database with this person id "+personId+" "+e);
            throw new BookNotFoundException("There is no loans in database with this person id "+personId);
        }


        return loans;
    }
    /**
     * Get all loans
     * @return List<Loan>
     */
    @RequestMapping(value = {"/allLoans"}, method = RequestMethod.GET)
    public List<Loan> getAllLoans() {
        LOGGER.info("getAllLoans was called");
        List<Loan> loans = null;
        try {
            loans = loanService.getAllLoans();
        } catch(Exception e) {
            LOGGER.error("There is no loans in database "+e);
            throw new BookNotFoundException("There is no loans in database "+e);
        }
        return loans;
    }

   /* *//**
     * Get all loans's person
     * @return List<Person>
     */
    @RequestMapping(value = {"/allLoansPersonsLate"}, method = RequestMethod.GET)
    public List<Integer> getAllLoansPersonsLate() {
        LOGGER.info("getAllLoansPersons was called");

        List<Loan> allLoans = getAllLoans();
        List<Integer> persons = new ArrayList<Integer>();

        Date todayDate = new Date();

        for (int i = 0; i < allLoans.size(); i++) {
            Date loanDate = allLoans.get(i).getDate();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(todayDate);

            calendar.add(Calendar.DATE, 30);

            Date loanEndingDate = calendar.getTime();

            if (todayDate.compareTo(loanEndingDate) > 0) {
                LOGGER.info("Sending an email to user");
                List<Integer> person = null;
                try {
                     allLoans.get(i).getId(); //  person = loanPerson(allLoans.get(i).getId());
                } catch (Exception e) {
                    LOGGER.error("There isn't person for this loan... " + allLoans.get(i).getIdPerson());
                    throw new BookNotFoundException("There isn't person for this loan..." + allLoans.get(i).getIdPerson() + ' ' + e);
                }
                int personT = person.get(0);

                persons.add(personT);
            }
        }
        return persons;
    }

    /**
     * Set a loan by copy id
     * @param copyId
     * @return Loan
     */
    @RequestMapping(value = {"/loan"}, method = RequestMethod.POST)
    public Loan setLoan(
            @RequestParam Long copyId
    ) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Copy copy = null;
            copy = copyService.getCopy(copyId).orElseThrow (() ->
                    new BookNotFoundException("There is no copy in database with this id "+copyId));
            if(copy.getIsAvailable()==0) {
                LOGGER.info("This copy "+copyId+" is already loan");
                return null;
            }
            copy.setIsAvailable(1);
            copyService.updateCopy(copyId, copy);
            Loan loan = new Loan();
            loan.setDate(date);
            loan.setIsSecondLoan(0);
            loan.setCopy(copy);

            loanService.addLoan(loan);

        return loan;
    }
    /**
     *Retour Loan
     * @param loanId
     * @return
     */
    @RequestMapping(value = {"/retourLoan/{loanId}"}, method = RequestMethod.POST)
    public  void retourLoan(
            @PathVariable Long loanId
    ) {
        Copy copy = null;
        Loan loan = null;
            loan = loanService.getLoan(loanId).orElseThrow(() ->
                    new BookNotFoundException("There is no loan in database with this id " + loanId));

            loanService.closeLoan(loan);

    }

    /**
     * Extend a loan by loan id
     * @param loanId
     * @return Loan
     */
    @RequestMapping(value = {"/extendLoan/{loanId}"}, method = RequestMethod.POST)
    public Loan extendLoan(
            @PathVariable Long loanId
    ) {
        Loan loan = null;
        try {
            loan = loanService.getLoan(loanId).orElseThrow (() ->
                    new BookNotFoundException("There is no loan in database with this id "+loanId));
            if(loan.getIsSecondLoan()==1) {
                LOGGER.info("This is your second loan, you can't extend anymore");
                return null;
            }

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            loan.setIsSecondLoan(1);
            loan.setDate(date);
            loanService.updateLoan(loanId, loan);

        } catch(Exception e) {
            LOGGER.error("There is no loan in database with this id "+loanId+" "+e);
        }

        return loan;
    }
    /**
     * Get Loan's person
     * @param PersonId
     * @return
     */
    // passer en param userid
    //
   @RequestMapping(value = {"/getLoanPerson/{PersonId}"}, method = RequestMethod.GET)
    public Integer loanPerson(
            @PathVariable Long PersonId
    ) {
        int person;
        try {
            Optional<Loan> optionalLoan = loanService.getLoan(PersonId);
            Loan loan = optionalLoan.get();
            try {
                person = loan.getIdPerson();
            } catch (Exception e) {
                LOGGER.error("There is no person for this id "+PersonId+" "+e);
                throw new BookNotFoundException("There is no person for this id "+PersonId+" "+e);
            }
        } catch(Exception e) {
            LOGGER.error("There is no loans for this id "+PersonId+" "+e);
            throw new BookNotFoundException("There is no loans for this person id "+PersonId+" "+e);
        }

        return person;
    }
}


