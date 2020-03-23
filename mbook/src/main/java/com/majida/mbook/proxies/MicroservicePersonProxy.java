package com.majida.mbook.proxies;

import com.majida.mbook.entity.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "microservicePersonProxy", name = "zuul-server")
public interface MicroservicePersonProxy {
    /**
     * Get person page
     * @param personId
     * @return Person
     */
    @RequestMapping(value = {"/microservice-person/person/{personId}"}, method = RequestMethod.GET)
    Person getPersonPage(@PathVariable Long personId);


}
