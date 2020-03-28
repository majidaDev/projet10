package com.majida.mbook.service;


import com.majida.mbook.entity.Loan;
import com.majida.mbook.entity.Person;
import com.majida.mbook.proxies.MicroservicePersonProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.majida.mbook.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class Batch {

    private static final Logger LOGGER = LogManager.getLogger(Batch.class);

    @Autowired
    LoanService loanService;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MicroservicePersonProxy microservicePersonProxy;


    @Scheduled(cron= "1 * * * * ?")
    public void execute()
    {
        List<Person> persons = getAllLoansPersonsLate();
        persons.stream().forEach(p -> {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(p.getEmail());

            simpleMailMessage.setSubject("Retard dans vos emprunts");
            LOGGER.info("now Sending an email to user");
            simpleMailMessage.setText(
                    "Bonjour " + p.getFirstname() + " " + p.getLastname() + ", \n\n"
                            + "Vous avez des retards d'emprunt : \n"
                            + this.getListBook(loanService.getLoansByPersonId(p.getId()))
                            + "\nMerci de faire le nécessaire pour être en règle. \n"
                            + "\nCordialement, \n\n"
                            + "La direction de la médiathèque au Mille et un Livres."
            );
            javaMailSender.send(simpleMailMessage);
        });
    }

    private String getListBook(List<Loan> list) {

        String textList = "";

        for (int i = 0; i < list.size(); i++) {
            textList = textList + list.get(i).getCopy().getBook().getTitle()
                    + " de " + list.get(i).getCopy().getBook().getAuthor()
                    + "\n";
        }

        return textList;
    }

    public List<Person> getAllLoansPersonsLate() {
        LOGGER.info("getAllLoansPersons was called");

        List<Loan> allLoans = loanService.getAllLoans();
        List<Person> persons = new ArrayList<Person>();

        Date todayDate = new Date();

        for (int i = 0; i < allLoans.size(); i++) {
            Date loanDate = allLoans.get(i).getDate();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(todayDate);

            calendar.add(Calendar.DATE, 30);

            Date loanEndingDate = calendar.getTime();

            if (todayDate.compareTo(loanDate) > 0) {
                LOGGER.info("Sending an email to user");
                try {
                    Person p = microservicePersonProxy.getPersonPage(Long.valueOf(allLoans.get(i).getIdPerson()));
                    persons.add(p);
                } catch (Exception e) {
                    LOGGER.error(" so There isn't person for this loan...  "+ allLoans.get(i).getIdPerson() );
                    throw new BookNotFoundException("There isn't person for this loan..." + allLoans.get(i).getIdPerson()  + ' ' + e);
                }
            }
        }
        return persons;
    }
}


