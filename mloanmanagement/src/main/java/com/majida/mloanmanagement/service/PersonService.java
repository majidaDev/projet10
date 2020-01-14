package com.majida.mloanmanagement.service;

import com.majida.mloanmanagement.entity.Person;
import com.majida.mloanmanagement.repository.PersonRepository;
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

    public Optional<Person> getPerson(Long id) {
        return personRepository.findById(id);
    }

    public void addPerson(Person person) {
        personRepository.save(person);
    }

    public void updatePerson(Long id, Person person) {
        personRepository.save(person);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
