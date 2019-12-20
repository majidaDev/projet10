package com.majida.clientui.entity;

import java.util.HashSet;
import java.util.Set;

public class Loan {

    private Long id;

    private String date;

    private int isSecondLoan;

    private Copy copy;

    private Set<Person> loanPerson  = new HashSet<Person>();

    public Loan() {
    }

    public Loan(String date, int isSecondLoan, Copy copy) {
        this.date = date;
        this.isSecondLoan = isSecondLoan;
        this.copy = copy;
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

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public Set<Person> getLoanPerson() {
        return loanPerson;
    }

    public void setLoanPerson(Set<Person> loanPerson) {
        this.loanPerson = loanPerson;
    }
}
