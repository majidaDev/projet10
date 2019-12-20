package com.majida.clientui.controller;

import com.majida.clientui.entity.Book;
import com.majida.clientui.proxies.MicroserviceBookProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchingController {

    private static final Logger LOGGER = LogManager.getLogger(SearchingController.class);

    @Autowired
    private MicroserviceBookProxy microserviceBookProxy;
    /**
     * SEARCHING PART
     */

    /**
     * searchByCategory
     *
     * @param model
     * @param categoryId
     * @return
     */
    @RequestMapping(value = {"/searchByCategory"}, method = RequestMethod.POST)
    public ModelAndView searchByCategory(
            Model model,
            @RequestParam("categoryId") Long categoryId
    ) {
        List<Book> books = microserviceBookProxy.getBooksByCategoryId(categoryId);
        model.addAttribute("results", books);
        return new ModelAndView("results");
    }

    /**
     * searchByAuthor
     *
     * @param model
     * @param author
     * @return
     */
    @RequestMapping(value = {"/searchByAuthor"}, method = RequestMethod.POST)
    public ModelAndView searchByAuthor(
            Model model,
            @RequestParam("author") String author
    ) {
        List<Book> books = microserviceBookProxy.getBooksByAuthor(author);
        model.addAttribute("results", books);
        return new ModelAndView("results");
    }

    /**
     * searchByKeyword
     *
     * @param model
     * @param keyword
     * @return
     */
    @RequestMapping(value = {"/searchByKeyword"}, method = RequestMethod.POST)
    public ModelAndView searchByKeyword(
            Model model,
            @RequestParam("keyword") String keyword
    ) {
        List<Book> books = microserviceBookProxy.getBooksByKeyword(keyword);
        model.addAttribute("results", books);
        return new ModelAndView("results");
    }
}