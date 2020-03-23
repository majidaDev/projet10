package com.majida.mbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "copy")
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reference_number")
    private int referenceNumber;

    @Column(name = "is_available")
    private int isAvailable;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="book_id", referencedColumnName = "id")
    @OrderBy
    private Book book;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "copy")
    @OrderBy
    @JsonIgnore
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
