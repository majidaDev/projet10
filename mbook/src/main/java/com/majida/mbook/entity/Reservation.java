package com.majida.mbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "id_person")
    private Long IdPerson;

    @Column(name = "dateCreate")
    @Temporal( TemporalType.DATE )
    @DateTimeFormat( iso = ISO.DATE )
    private Date dateCreate;

    @Column(name = "sendMail")
    private Boolean sendMail = false;

    @Column(name = "dateMail")
    @Temporal( TemporalType.DATE )
    @DateTimeFormat( iso = ISO.DATE )
    private Date dateMail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="book_id", referencedColumnName = "id")
    @OrderBy
    private Book book;

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Boolean getSendMail() {
        return sendMail;
    }

    public void setSendMail(Boolean sendMail) {
        this.sendMail = sendMail;
    }

    public Date getDateMail() {
        return dateMail;
    }

    public void setDateMail(Date dateMail) {
        this.dateMail = dateMail;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getIdPerson() {
        return IdPerson;
    }

    public void setIdPerson(Long idPerson) {
        IdPerson = idPerson;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
