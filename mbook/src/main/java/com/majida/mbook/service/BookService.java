package com.majida.mbook.service;

import com.majida.mbook.entity.Book;
import com.majida.mbook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

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

    public Optional<Book> getBook(Long id) {
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
