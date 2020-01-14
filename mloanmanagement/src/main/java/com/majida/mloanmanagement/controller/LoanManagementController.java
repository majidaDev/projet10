package com.majida.mloanmanagement.controller;

import com.majida.mloanmanagement.entity.Copy;
import com.majida.mloanmanagement.entity.Loan;
import com.majida.mloanmanagement.entity.Person;
import com.majida.mloanmanagement.exception.LoanNotFoundException;
import com.majida.mloanmanagement.service.CopyService;
import com.majida.mloanmanagement.service.LoanService;
import com.majida.mloanmanagement.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@RestController
public class LoanManagementController {

    private static final Logger LOGGER = LogManager.getLogger(LoanManagementController.class);

    @Autowired
    LoanService loanService;

    @Autowired
    CopyService copyService;

    @Autowired
    PersonService personService;

    /**
     * Get all loans by person id
     * @param personId
     * @return Set<Loan>
     */
    @RequestMapping(value = {"/myLoans/{personId}"}, method = RequestMethod.GET)
    public Set<Loan> getLoansById(@PathVariable Long personId) {
        LOGGER.info("getLoansById was called");
        Person person;
        Set<Loan> loans = null;
        try {
            person = personService.getPerson(personId).orElseThrow (() ->
                    new LoanNotFoundException("There is no person in database with this id "+personId));
            try {
                loans = person.getLoans();
            } catch(Exception e) {
                LOGGER.error("There is no loans for this person id "+personId+" "+e);
                throw new LoanNotFoundException("There is no loans for this person id "+personId+" "+e);
            }
        }
        catch(Exception e) {
            LOGGER.error("There is no person in database with this id "+personId+" "+e);
        }

        return loans;
    }

    /**
     * Get all loans
     * @return List<Loan>
     */
    @RequestMapping(value = {"/allLoans"}, method = RequestMethod.GET)
    public List<Loan> getAllLoans() {
        LOGGER.info("getAllLoans was called");
        List<Loan> loans = null;
        try {
                loans = loanService.getAllLoans();
        } catch(Exception e) {
            LOGGER.error("There is no loans in database "+e);
            throw new LoanNotFoundException("There is no loans in database "+e);
        }
        return loans;
    }

    /**
     * Get all loans's person
     * @return List<Person>
     */
    @RequestMapping(value = {"/allLoansPersonsLate"}, method = RequestMethod.GET)
    public List<Person> getAllLoansPersonsLate() {
        LOGGER.info("getAllLoansPersons was called");

        List<Loan> allLoans = getAllLoans();
        List<Person> persons = new ArrayList<Person>();

        Date todayDate = new Date();

        for (int i = 0; i < allLoans.size(); i++) {
            Date loanDate = allLoans.get(i).getDate();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(loanDate);

            calendar.add(Calendar.DATE, 30);

            Date loanEndingDate = calendar.getTime();

            if (todayDate.compareTo(loanEndingDate) > 0) {
                LOGGER.info("Sending an email to user");
                List<Person> person = null;
                try {
                    person = loanPerson(allLoans.get(i).getId());
                } catch (Exception e) {
                    LOGGER.error("There isn't person for this loan... " + allLoans.get(i).getId());
                    throw new LoanNotFoundException("There isn't person for this loan..." + allLoans.get(i).getId() + ' ' + e);
                }
                Person personT = person.get(0);

                persons.add(personT);
            }
        }
        return persons;
    }

    /**
     * Set a loan by copy id
     * @param copyId
     * @return Loan
     */
    @RequestMapping(value = {"/loan"}, method = RequestMethod.POST)
    public Loan setLoan(
            @RequestParam Long copyId
    ) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Copy copy = null;
        try {
            copy = copyService.getCopy(copyId).orElseThrow (() ->
                    new LoanNotFoundException("There is no copy in database with this id "+copyId));
            if(copy.getIsAvailable()==0) {
                LOGGER.info("This copy "+copyId+" is already loan");
                return null;
            }
            copy.setIsAvailable(1);
            copyService.updateCopy(copyId, copy);
        } catch(Exception e) {
            LOGGER.error("There is no copy in database with this id "+copyId+" "+e);
        }

        Loan loan = new Loan();
        loan.setDate(date);
        loan.setIsSecondLoan(0);
        loan.setCopy(copy);

        loanService.addLoan(loan);

        return loan;
    }

    /**
     * Extend a loan by loan id
     * @param loanId
     * @return Loan
     */
    @RequestMapping(value = {"/extendLoan/{loanId}"}, method = RequestMethod.POST)
    public Loan extendLoan(
            @PathVariable Long loanId
    ) {
        Loan loan = null;
        try {
            loan = loanService.getLoan(loanId).orElseThrow (() ->
                    new LoanNotFoundException("There is no loan in database with this id "+loanId));
            if(loan.getIsSecondLoan()==1) {
                LOGGER.info("This is your second loan, you can't extend anymore");
                return null;
            }

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            loan.setIsSecondLoan(1);
            loan.setDate(date);
            loanService.updateLoan(loanId, loan);

        } catch(Exception e) {
            LOGGER.error("There is no loan in database with this id "+loanId+" "+e);
        }

        return loan;
    }

    /**
     * Get Loan's person
     * @param loanId
     * @return
     */
    @RequestMapping(value = {"/getLoanPerson/{loanId}"}, method = RequestMethod.GET)
    public List<Person> loanPerson(
            @PathVariable Long loanId
    ) {
        List<Person> person;
        try {
            Optional<Loan> optionalLoan = loanService.getLoan(loanId);
            Loan loan = optionalLoan.get();
            try {
                person = loan.getLoanPerson();
            } catch (Exception e) {
                LOGGER.error("There is no person for this id "+loanId+" "+e);
                throw new LoanNotFoundException("There is no person for this id "+loanId+" "+e);
            }
        } catch(Exception e) {
            LOGGER.error("There is no loans for this id "+loanId+" "+e);
            throw new LoanNotFoundException("There is no loans for this person id "+loanId+" "+e);
        }

        return person;
    }
}
