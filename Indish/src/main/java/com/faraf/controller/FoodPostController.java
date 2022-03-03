package com.faraf.controller;



import com.faraf.entity.User;
import com.faraf.service.FoodPostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
public class FoodPostController {


    private final FoodPostServiceImpl service;

    //localhost:8080/api/v1/post?name=kebab
    @RequestMapping(params = "name", method = RequestMethod.GET)
    public List<User> findAllByCity(@RequestParam("name") String name) {
        return service.getAllByName(name);
    }

    //localhost:8080/api/v1/post?country=russia
    @RequestMapping(params = "country", method = RequestMethod.GET)
    public List<User> findAllByCountry(@RequestParam("country") String country) {
        return service.getAllByCountry(country);
    }

    //localhost:8080/api/v1/post?timeRequired=50
    @RequestMapping(params = "timeRequired", method = RequestMethod.GET)
    public List<User> findAllByTimeRequired(@RequestParam("timeRequired") int timeRequired) {
        return service.getAllByTimeRequired(timeRequired);
    }

}
