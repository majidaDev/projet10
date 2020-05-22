package com.majida.mbook.entity;

import java.io.Serializable;

public class ReservationPerson implements Serializable {

    private Long id;

    private Long idPerson;

    private Integer position;

    private String bookTitle;

    private String nearestreturnDate;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getNearestreturnDate() {
        return nearestreturnDate;
    }

    public void setNearestreturnDate(String nearestreturnDate) {
        this.nearestreturnDate = nearestreturnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

