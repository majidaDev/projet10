package com.majida.mbook.service;


import com.majida.mbook.entity.*;
import com.majida.mbook.proxies.MicroservicePersonProxy;
import com.majida.mbook.repository.ReservationRepository;
import org.apache.commons.lang.time.DateUtils;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    ReservationRepository reservationRepository;

    @Autowired
    BookService bookService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MicroservicePersonProxy microservicePersonProxy;


    @Scheduled(cron = "0 * * * * ?")
    public void execute() {
        List<Person> persons = getAllLoansPersonsLate();
        persons.stream().forEach(p -> {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(p.getEmail());
            simpleMailMessage.setFrom("biliotheque230@gmail.com");
            simpleMailMessage.setSubject("Retard dans vos emprunts");
            LOGGER.info("now Sending an email to user");
            simpleMailMessage.setText(
                    "Bonjour " + p.getFirstname() + " " + p.getLastname() + ", \n\n"
                            + "Vous avez des retards d'emprunt : \n"
                            + this.getListBook(loanService.getLoansByPersonId(p.getId()))
                            + "\nMerci de respecter les dates et de retournen les ouvrages. \n"
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
                    LOGGER.error(" so There isn't person for this loan...  " + allLoans.get(i).getIdPerson());
                    throw new BookNotFoundException("There isn't person for this loan..." + allLoans.get(i).getIdPerson() + ' ' + e);
                }
            }
        }
        return persons;
    }


    public List<Person> getAllReservationsPersons() {
        LOGGER.info("getAllReservationsPersons was called");

        List<Book> allBooks = bookService.getAllBooks();
        List<Person> persons = new ArrayList<Person>();


        for (int i = 0; i < allBooks.size(); i++) {
            List<Reservation> reservations = reservationService.getAllReservationsBookIdOrOrderByDate(allBooks.get(i).getId());


            LOGGER.info("sending mail");
            try {
                Person p = microservicePersonProxy.getPersonPage(Long.valueOf(reservations.get(i).getIdPerson()));
                persons.add(p);
            } catch (Exception e) {
                LOGGER.error(" so There isn't person for this reservation...  " + reservations.get(i).getIdPerson());
                throw new BookNotFoundException("There isn't person for this loan..." + reservations.get(i).getIdPerson() + ' ' + e);
            }
        }
        return persons;
    }

    @Scheduled(cron = "*/60 * * * * *")
    public void sendReservationMail () {

        List<Reservation> reservationList = reservationRepository.findReservationBySendMailAndStatus(true, Status.Waiting);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        LocalDate localDate = LocalDate.now();

        if(reservationList.size() > 0) {

            for (Reservation reservation : reservationList) {

                Date dateDeadline = DateUtils.addDays(reservation.getDateMail(), 2);

                Date today = new Date();

                if(today.after(dateDeadline)) {

                    reservationService.deleteReservationAfterMail(reservation.getId());

                    List<Reservation> reservations = reservationRepository.findReservationByBookAndStatusOrderByDateCreate(reservation.getBook(), Status.Waiting);

                    if ( reservations.size() > 0 ) {

                        Reservation reservationMail = reservations.get(0);

                        reservationMail.setDateMail(java.sql.Date.valueOf(localDate));

                        Book book = reservationMail.getBook();

                        Date deadlineReservation = DateUtils.addDays(reservationMail.getDateMail(), 2);

                        Person person = microservicePersonProxy.getPersonPage(Long.valueOf(reservationMail.getIdPerson()));
                        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                        simpleMailMessage.setTo(person.getEmail());
                        simpleMailMessage.setFrom("biliotheque230@gmail.com");
                        simpleMailMessage.setSubject("Réservation disponible");
                        LOGGER.info("now Sending an email to user");
                        simpleMailMessage.setText( "Bonjour " + person.getFirstname() + " " + person.getLastname() + "," +
                                "\n\nNous vous informons que la réservation du Livre ci-dessous est disponible : " +
                                "\n\n" + book.getTitle() +
                                "\n\nVous avez jusqu'au " + dateFormat.format(deadlineReservation) + " pour venir récupérer votre livre." +
                                "\n\nPassée cette date, le document sera remis en disponibilité." +
                                "\n\nCordialement," +
                                "\n\nL'équipe de la Bibliothèque");


                        javaMailSender.send(simpleMailMessage);
                        reservationMail.setSendMail(true);

                        reservationRepository.save(reservationMail);
                    }
                }
            }
        }
    }

}



