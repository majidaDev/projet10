package com.majida.zuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LogFilter extends ZuulFilter {

    // créer un Logger
    Logger log = LoggerFactory.getLogger(this.getClass());
/*
Ce filtre sera executé quand la requete va arriver de client vers Zuul
return "post" : dans le sens contraire
 */
    @Override
    public String filterType() {
        return "pre";
    }
/*
permet de définir l'ordre d'exécutiond ece filtre
 */
    @Override
    public int filterOrder() {
        return 1;
    }
/*
pour dire si ce filtre doit s'exécuter ou nn
 */
    @Override
    public boolean shouldFilter() {
        return true;
    }
/*
La logique de filtre est à mettre dans run
 */
    @Override
    public Object run() throws ZuulException {

        HttpServletRequest req = RequestContext.getCurrentContext().getRequest();

        log.info("**** Requête interceptée ! L'URL est : {} " , req.getRequestURL());

        return null;
    }
}
