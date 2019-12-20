package com.majida.mbook.controller;

import com.majida.mbook.entity.Book;
import com.majida.mbook.entity.Category;
import com.majida.mbook.entity.Copy;
import com.majida.mbook.exception.BookNotFoundException;
import com.majida.mbook.service.BookService;
import com.majida.mbook.service.CategoryService;
import com.majida.mbook.service.CopyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}
