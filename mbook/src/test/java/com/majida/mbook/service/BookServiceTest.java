package com.majida.mbook.service;

import com.majida.mbook.entity.Book;
import com.majida.mbook.repository.BookRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * Test the Book Service Implementation: test the service logic
 *
 * @author majida
 */

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    private static final Long BOOK_ONE_ID = 1L;


    @Mock
    private BookRepository repoMock;

    @InjectMocks
    private BookService bookService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private  Book book;


@Before
    public void init() {
        book = new Book();
        book.setId(BOOK_ONE_ID);
        book.setTitle("la force");
        book.setDescription("c'est mon premier livre");
        book.setAuthor("CARLOS");
        book.setImage("Image1");
    }

@Test
    public void getAllBooks() {
        // Data preparation
        List<Book> books = Arrays.asList(book, book, book);
        Mockito.when(repoMock.findAll()).thenReturn(books);

        // Method call
        List<Book> bookList = bookService.getAllBooks();

        // Verification
        Assert.assertThat(bookList, Matchers.hasSize(3));
        Mockito.verify(repoMock, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(repoMock);
    }

@Test
    public void getBooksByAuthorAndBooksExists() {

        // Data preparation
        List<Book> books = Arrays.asList(book,book,book);
        Mockito.when(repoMock.findByAuthorLike(ArgumentMatchers.anyString())).thenReturn(books);

        // Method call
        List<Book> bookList = bookService.getBooksByAuthor("CARLOS");

        // Verification
        Assert.assertThat(bookList, Matchers.hasSize(3));
        Mockito.verify(repoMock, Mockito.times(1)).findByAuthorLike(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(repoMock);
    }

@Test
    public void getBooksByKeywordAndBooksExists() {

        // Data preparation
        List<Book> books = Arrays.asList(book,book,book);
        Mockito.when(repoMock.findByKeywordLike(ArgumentMatchers.anyString())).thenReturn(books);

        // Method call
        List<Book> bookList = bookService.getBooksByKeyword("la force");

        // Verification
        Assert.assertThat(bookList, Matchers.hasSize(3));
        Mockito.verify(repoMock, Mockito.times(1)).findByKeywordLike(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(repoMock);
    }

@Test
   public void getBookAndBookExists() {
        // Data preparation
        Mockito.when(repoMock.findById(BOOK_ONE_ID)).thenReturn(Optional.of(book));

        // Method call
        Optional<Book> book = bookService.getBook(BOOK_ONE_ID);

        // Verification
        Assert.assertNotNull(book);
        Mockito.verify(repoMock, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(repoMock);

    }

@Test
   public void updateBookAndBookNotExists() throws Exception {
    // Method call
    bookService.updateBook(BOOK_ONE_ID, book);
    repoMock.save(book);

    // Verification
    Mockito.verify(repoMock, Mockito.times(2)).save(ArgumentMatchers.any());
    Mockito.verifyNoMoreInteractions(repoMock);
    }

@Test
    public void deleteBookAndBookNotExists() {

    // Method call
    bookService.deleteBook(BOOK_ONE_ID);


    // Verification
    Mockito.verify(repoMock, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
    Mockito.verifyNoMoreInteractions(repoMock);



}
}