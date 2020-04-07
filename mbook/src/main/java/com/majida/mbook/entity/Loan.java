package com.majida.mbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "is_second")
    private int isSecondLoan;

    @Column(name = "id_person")
    private int IdPerson;

    @Column(name = "close")
    private Boolean close;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="copy_id", referencedColumnName = "id")
    @OrderBy
    private Copy copy;

    public Loan() {
    }

    public Loan(Date date, int isSecondLoan, Copy copy, int idPerson) {
        this.date = date;
        this.isSecondLoan = isSecondLoan;
        this.copy = copy;
        this.IdPerson= idPerson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIsSecondLoan() {
        return isSecondLoan;
    }

    public void setIsSecondLoan(int isSecondLoan) {
        this.isSecondLoan = isSecondLoan;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public int getIdPerson() {
        return IdPerson;
    }

    public void setIdPerson(int idPerson) {
        this.IdPerson = idPerson;
    }

    public Boolean getClose() {
        return close;
    }

    public void setClose(Boolean close) {
        this.close = close;
    }

}
