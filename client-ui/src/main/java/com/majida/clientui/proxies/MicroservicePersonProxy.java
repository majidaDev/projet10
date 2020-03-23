package com.majida.clientui.proxies;

import com.majida.clientui.entity.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(contextId = "microservicePersonProxy", name = "zuul-server")
public interface MicroservicePersonProxy {
    /**
     * Get person page
     * @param personId
     * @return Person
     */
    @RequestMapping(value = {"/microservice-person/person/{personId}"}, method = RequestMethod.GET)
    Person getPersonPage(@PathVariable Long personId);

    /**
     * Post sign in
     * @param email
     * @param password
     * @return Person
     */
    @RequestMapping(value = {"/microservice-person/signin"}, method = RequestMethod.POST)
    Person signIn(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    );

    /**
     * Post create new person
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     */
    @RequestMapping(value = {"/microservice-person/newPerson"}, method = RequestMethod.POST)
    void setPerson(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    );
}
