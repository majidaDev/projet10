package com.majida.mloanmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="copy_id", referencedColumnName = "id")
    @OrderBy
    private Copy copy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_borrow",
            joinColumns = @JoinColumn(name = "loan_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @OrderBy
    @JsonIgnore
    private List<Person> loanPerson  = new ArrayList<Person>();

    public Loan() {
    }

    public Loan(Date date, int isSecondLoan, Copy copy) {
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

    public List<Person> getLoanPerson() {
        return loanPerson;
    }

    public void setLoanPerson(List<Person> loanPerson) {
        this.loanPerson = loanPerson;
    }
}
