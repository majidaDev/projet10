package com.majida.mbook.service;

import com.majida.mbook.entity.Copy;
import com.majida.mbook.entity.Loan;
import com.majida.mbook.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private  CopyService copyService;

    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<Loan>();
        loanRepository.findAll()
                .forEach(loans::add);
        return loans;
    }
    public List<Loan> getLoansByPersonId(Long id) {
        List<Loan> loans = loanRepository.getAllLoansPersonId(id);
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

    public void closeLoan(Loan l) {
        Copy copy = l.getCopy();
        copy.setIsAvailable(0);
        int is = copy.getIsAvailable();
        copyService.updateCopy((Long.valueOf(is)), copy);
        l.setClose(true);
        loanRepository.save(l);
    }


}
