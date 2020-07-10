package com.majida.mbook.service;


import com.majida.mbook.entity.Loan;
import com.majida.mbook.repository.LoanRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Test the Loan Service Implementation: test the service logic
 *
 * @author majida
 */

@RunWith(MockitoJUnitRunner.class)

public class LoanServiceTest {
    private static final Long LOAN_ID = 4L;
    private static final int PERSON_ID = 5;


    @Mock
    private LoanRepository repoMock;

    @InjectMocks
    private LoanService loanService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Loan loan;

    @Before
   public void init() {
        Loan loan = new Loan();
        loan.setId(LOAN_ID);
        loan.setIsSecondLoan(0);
        loan.setIdPerson(PERSON_ID);


    }

    @Test
    public void getAllLoans() {

        // Data preparation
        List<Loan> loans = Arrays.asList(loan, loan, loan);
        Mockito.when(repoMock.findAll()).thenReturn(loans);

        // Method call
        List<Loan> loanList = loanService.getAllLoans();

        // Verification
        Assert.assertThat(loanList, Matchers.hasSize(3));
        Mockito.verify(repoMock, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(repoMock);
    }

    @Test
    public void getLoansByPersonId() {

        // Data preparation
        List<Loan> loans = Arrays.asList(loan,loan,loan);
        Mockito.when(repoMock.getAllLoansPersonId(ArgumentMatchers.anyLong())).thenReturn(loans);

        // Method call
        List<Loan> loanList = loanService.getLoansByPersonId(Long.valueOf(PERSON_ID));

        // Verification
        Assert.assertThat(loanList, Matchers.hasSize(3));
        Mockito.verify(repoMock, Mockito.times(1)).getAllLoansPersonId(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(repoMock);
    }

   /* @Test
    public void getLoanAndLoanExists() {
        // Data preparation
        Mockito.when(repoMock.findById(LOAN_ID)).thenReturn(Optional.of((loan)));

        // Method call
        Optional<Loan> loan = loanService.getLoan(LOAN_ID);

        // Verification
        Assert.assertNotNull(loan);
        Mockito.verify(repoMock, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(repoMock);
    }*/


    @Test
    public void updateLoan() {
        // Method call
        loanService.updateLoan(LOAN_ID, loan);
        repoMock.save(loan);

        // Verification
        Mockito.verify(repoMock, Mockito.times(2)).save(ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(repoMock);
    }

}