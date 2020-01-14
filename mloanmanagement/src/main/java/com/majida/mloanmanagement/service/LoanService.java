package com.majida.mloanmanagement.service;

import com.majida.mloanmanagement.entity.Loan;
import com.majida.mloanmanagement.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<Loan>();
        loanRepository.findAll()
                .forEach(loans::add);
        return loans;
    }

    public Optional<Loan> getLoan(Long id) {
        return loanRepository.findById(id);
    }

    public void addLoan(Loan person) {
        loanRepository.save(person);
    }

    public void updateLoan(Long id, Loan person) {
        loanRepository.save(person);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}
