package com.majida.mbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
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
    private String date;

    @Column(name = "is_second")
    private int isSecondLoan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="copy_id", referencedColumnName = "id")
    @OrderBy
    @JsonIgnore
    private Copy copy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_borrow",
            joinColumns = @JoinColumn(name = "loan_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @OrderBy
    @JsonIgnore
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
