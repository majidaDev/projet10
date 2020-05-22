package com.majida.mbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "author")
    private String author;

    @Column(name = "image")
    private String image;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    @OrderBy
    @JsonIgnore
    private Set<Copy> copies = new HashSet<Copy>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @OrderBy
    private Set<Category> bookCategories  = new HashSet<Category>();

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "book")
    @OrderBy
    private Set<Reservation> reservations = new HashSet<Reservation>();;

    @Transient
    private String nearestreturnDate;

    @Transient
    private Integer numberOfReservation;

    @Transient
    private Boolean isReservation;

    public Boolean isAvaibleBook() {
        Boolean isAvaibleBook = false;
        for (Copy bookCopy : copies) {
            if (bookCopy.getIsAvailable() == 1) {
                isAvaibleBook = true;
                break;
            }
        }
        return isAvaibleBook;

    }

    public Book() {
    }

    public Book(String title, String description, String author, String image, Set<Copy> copies, Set<Category> bookCategories) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.image = image;
        this.copies = copies;
        this.bookCategories = bookCategories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Copy> getCopies() {
        return copies;
    }

    public void setCopies(Set<Copy> copies) {
        this.copies = copies;
    }

    public Set<Category> getBookCategories() {
        return bookCategories;
    }

    public void setBookCategories(Set<Category> bookCategories) {
        this.bookCategories = bookCategories;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getNearestreturnDate() {
        return nearestreturnDate;
    }

    public void setNearestreturnDate(String nearestreturnDate) {
        this.nearestreturnDate = nearestreturnDate;
    }


    public Integer getNumberOfReservation() {
        return numberOfReservation;
    }

    public void setNumberOfReservation(Integer numberOfReservation) {
        this.numberOfReservation = numberOfReservation;
    }

    public Boolean getIsReservation() {
        return isReservation;
    }

    public void setIsReservation(Boolean reservation) {
        isReservation = reservation;
    }
}
