package com.majida.mbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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

    @Column(name = "dateloan")
    @Temporal( TemporalType.DATE )
    @DateTimeFormat( iso = ISO.DATE )
    private Date dateloan;


    @Column(name="deadline")
    @Temporal( TemporalType.DATE )
    @DateTimeFormat( iso = ISO.DATE )
    private Date deadline;

    @Column(name = "is_second")
    private int isSecondLoan;

    @Column(name = "id_person")
    private int IdPerson;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="copy_id", referencedColumnName = "id")
    @OrderBy
    private Copy copy;

    public Loan() {
    }

    public Loan(Date dateloan, int isSecondLoan, Copy copy, int idPerson, Date dateline) {
        this.dateloan = dateloan;
        this.isSecondLoan = isSecondLoan;
        this.copy = copy;
        this.IdPerson= idPerson;
        this.deadline=dateline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public Status getStatus() {
        return status;
    }

    public Date getDateloan() {
        return dateloan;
    }

    public void setDateloan(Date dateloan) {
        this.dateloan = dateloan;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
