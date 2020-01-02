package com.majida.mperson.service;


import com.majida.mperson.entity.Person;
import com.majida.mperson.repository.PersonRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Set<Person> getAllPersons() {
        Set<Person> persons = new HashSet<Person>();
        personRepository.findAll()
                .forEach(persons::add);
        return persons;
    }

    public Optional<Person> authentificateUser(String email) {
        Optional<Person> personByMail = personRepository.authentificateUser(email);
        return personByMail;
    }

    public boolean passwordOk(String email, String password) {
        Optional<Person> personByMail = this.authentificateUser(email);
        boolean rawPassword = false;
        if(!personByMail.isEmpty()) {
            rawPassword = BCrypt.checkpw(password, personByMail.get().getPassword());
        }
        return rawPassword;
    }

    public Optional<Person> getPerson(Long id) {
        return personRepository.findById(id);
    }

    public void addPerson(Person person) {
        String rawPassword = person.getPassword();
        person.setPassword(BCrypt.hashpw(rawPassword, BCrypt.gensalt(12)));
        personRepository.save(person);
    }

    public void updatePerson(Long id, Person person) {
        personRepository.save(person);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
