package com.majida.mbook.service;

import com.majida.mperson.entity.Person;
import com.majida.mperson.repository.PersonRepository;
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
import java.util.Set;


/**
 * Test the Person Service Implementation: test the service logic
 *
 * @author majida
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    private static final Long PERSON_ID = 6L;


    @Mock
    private PersonRepository repoMock;

    @InjectMocks
    private PersonService personService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Person person;

    @Before
   public void setUp() {

        person = new Person();
        person.setId(PERSON_ID);
        person.setFirstname("jack");
        person.setLastname("henri");
        person.setEmail("jackhenri@gmail.com");
        person.setPassword("mypassword");
    }

    @Test
    public void getAllPersons() {
        // Data preparation
        List<Person> persons = Arrays.asList(person, person, person);
        Mockito.when(repoMock.findAll()).thenReturn(persons);

        // Method call
        Set<Person> personList = personService.getAllPersons();

        // Verification
        Assert.assertThat(personList, Matchers.hasSize(1));
        Mockito.verify(repoMock, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(repoMock);
    }

    @Test
    public void authentificateUserAndUserExists() {
        // Data preparation
        Mockito.when(repoMock.authentificateUser("jackhenri@gmail.com")).thenReturn(Optional.of(person));

        // Method call
        Optional<Person> person = personService.authentificateUser("jackhenri@gmail.com");

        // Verification
        Assert.assertNotNull(person);
        Mockito.verify(repoMock, Mockito.times(1)).authentificateUser(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(repoMock);

    }

    @Test
    public void passwordOk() {
    }

    @Test
    public void getPersonAndPersonExists() {
        // Data preparation
        Mockito.when(repoMock.findById(PERSON_ID)).thenReturn(Optional.of(person));

        // Method call
        Optional<Person> person = personService.getPerson(PERSON_ID);

        // Verification
        Assert.assertNotNull(person);
        Mockito.verify(repoMock, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(repoMock);
    }

    @Test
    public void addPerson() {
    }

    @Test
    public void updatePersonAndPersonNotExists() {
        // Method call
        personService.updatePerson(PERSON_ID, person);
        repoMock.save(person);

        // Verification
        Mockito.verify(repoMock, Mockito.times(2)).save(ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(repoMock);
    }

    @Test
    public void deletePersonAndPersonNotExists() {

        // Method call
        personService.deletePerson(PERSON_ID);


        // Verification
        Mockito.verify(repoMock, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(repoMock);
    }
}