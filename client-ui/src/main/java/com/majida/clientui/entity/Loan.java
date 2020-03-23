package com.majida.clientui.entity;

import java.util.HashSet;
import java.util.Set;

public class Loan {

    private Long id;

    private String date;

    private int isSecondLoan;

    private int IdPerson;

    private Copy copy;

    public Loan() {
    }

    public Loan(String date, int isSecondLoan, Copy copy, int idPerson) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public int getIdPerson() {
        return IdPerson;
    }
    public void setIdPerson(int idPerson) {
        IdPerson = idPerson;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

}
