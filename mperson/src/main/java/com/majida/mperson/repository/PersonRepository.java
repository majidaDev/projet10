package com.majida.mperson.repository;

import com.majida.mperson.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query(value = "SELECT * FROM Person p WHERE p.email = ?", nativeQuery = true)
    Optional<Person> authentificateUser(String email);
}
