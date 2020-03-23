package com.majida.batch.tasklet;

import com.majida.batch.entity.Loan;
import com.majida.batch.entity.Person;
import com.majida.batch.proxies.MicroserviceLoanBatchProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanTasklet implements Tasklet {

    private static final Logger LOGGER = LogManager.getLogger(LoanTasklet.class);

    @Autowired
    MicroserviceLoanBatchProxy microserviceLoanBatchProxy;

    @Autowired
    private JavaMailSender javaMailSender;

    public LoanTasklet(MicroserviceLoanBatchProxy microserviceLoanBatchProxy, JavaMailSender javaMailSender) {
        this.microserviceLoanBatchProxy = microserviceLoanBatchProxy;
        this.javaMailSender = javaMailSender;
    }


    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
    {
        List<Person> persons = microserviceLoanBatchProxy.getAllLoansPersonsLate();
        persons.stream().forEach(p -> {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(p.getEmail());

            simpleMailMessage.setSubject("Retard dans vos emprunts");
            simpleMailMessage.setText(
                    "Bonjour "+p.getFirstname()+" "+p.getLastname()+", \n\n"
                            +"Vous avez des retards d'emprunt : \n"
                            + this.getListBook(microserviceLoanBatchProxy.getLoansById(p.getId()))
                            +"\nMerci de faire le nécessaire pour être en règle. \n"
                            +"\nCordialement, \n\n"
                            +"La direction de la médiathèque au Mille et un Livres."
            );
            javaMailSender.send(simpleMailMessage);
        });

        return RepeatStatus.FINISHED;
    }

    private String getListBook(List<Loan> list){

        String textList = "";

        for(int i=0; i < list.size(); i++){
            textList = textList + list.get(i).getCopy().getBook().getTitle()
                    + " de " + list.get(i).getCopy().getBook().getAuthor()
                    + "\n";
        }

        return textList;
    }
}
