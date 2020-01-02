package com.majida.mperson.controller;

import com.majida.mperson.entity.Person;
import com.majida.mperson.exception.PersonNotFoundException;
import com.majida.mperson.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    private static final Logger LOGGER = LogManager.getLogger(PersonController.class);

    @Autowired
    PersonService personService;

    /**
     * Get person page
     * @param personId
     * @return Person
     */
    @RequestMapping(value = {"/person/{personId}"}, method = RequestMethod.GET)
    public Person getPersonPage(@PathVariable Long personId) {
        Person person = null;
        try{
            person = personService.getPerson(personId).orElseThrow( () ->
                    new PersonNotFoundException("There is not Person with this id "+personId)
            );
        } catch (Exception e) {
            LOGGER.error("There is not Person with this id "+personId+" "+e);
        }
        return person;
    }

    /**
     * Post sign in
     * @param email
     * @param password
     * @return Person
     */
    @RequestMapping(value = {"/signin"}, method = RequestMethod.POST)
    public Person signIn(
            @RequestParam("email") String email,
            @RequestParam("password") String password

    ) {
        Person person = null;
        if(personService.passwordOk(email, password)) {
            try {
                person = personService.authentificateUser(email).orElseThrow(() ->
                            new PersonNotFoundException("This person's mail doesn't exist "+email)
                        );
            } catch(Exception e) {
                LOGGER.error("This person's mail doesn't exist "+email+" "+e);
                LOGGER.debug(
                        "message", "L'username ou le mot de passe est mauvais");
            }
        } else {
            LOGGER.debug(
                    "message", "L'username ou le mot de passe est mauvais");
        }
        return person;
    }

    /**
     * Post create new person
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     * @return Person
     */
    @RequestMapping(value = {"/newPerson"}, method = RequestMethod.POST)
    public void setPerson(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        Person person = new Person();
        person.setFirstname(firstname);
        person.setLastname(lastname);
        person.setPassword(password);
        person.setEmail(email);
        person.setIsAdmin(0);
        personService.addPerson(person);
        LOGGER.info("messageSuccess", "Inscription réussite, bravo =)");
    }
}
