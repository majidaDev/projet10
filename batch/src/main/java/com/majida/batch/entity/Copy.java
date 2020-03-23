package com.majida.batch.entity;

import java.util.HashSet;
import java.util.Set;

public class Copy {

    private Long id;

    private int referenceNumber;

    private int isAvailable;

    private Book book;

    private Set<Loan> loan = new HashSet<Loan>();

    public Copy() {
    }

    public Copy(int referenceNumber, Book book, int isAvailable) {
        this.referenceNumber = referenceNumber;
        this.book = book;
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Set<Loan> getLoan() {
        return loan;
    }

    public void setLoan(Set<Loan> loan) {
        this.loan = loan;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }
}
