package com.majida.clientui.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

public class Book {

    private Long id;

    private String title;

    private String description;

    private String author;

    private String image;

    private Integer numberOfReservation;

    private Integer maxNumberOfReservation;

    private String nearestreturnDate;

    private Boolean isAvaibleBook;

    private Boolean reservationBook;

    private Set<Copy> copies = new HashSet<Copy>();

    private Set<Category> bookCategories  = new HashSet<Category>();

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

    public Integer getNumberOfReservation() {
        return numberOfReservation;
    }

    public void setNumberOfReservation(Integer numberOfReservation) {
        this.numberOfReservation = numberOfReservation;
    }

    public Integer getMaxNumberOfReservation() {
        return maxNumberOfReservation;
    }

    public void setMaxNumberOfReservation(Integer maxNumberOfReservation) {
        this.maxNumberOfReservation = maxNumberOfReservation;
    }

    public String getNearestreturnDate() {
        return nearestreturnDate;
    }

    public void setNearestreturnDate(String nearestreturnDate) {
        this.nearestreturnDate = nearestreturnDate;
    }

    public Boolean getAvaibleBook() {
        return isAvaibleBook;
    }

    public void setAvaibleBook(Boolean avaibleBook) {
        isAvaibleBook = avaibleBook;
    }

    public Boolean getReservationBook() {
        return reservationBook;
    }

    public void setReservationBook(Boolean reservationBook) {
        this.reservationBook = reservationBook;
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

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", description=" + description + ", image=" + image
                + ", bookCategories=" + bookCategories + ", numberofReservation=" + numberOfReservation + ", maxNumberOfBooking="
                + maxNumberOfReservation + ", nearestreturnDate=" + nearestreturnDate + "]";
    }
}
