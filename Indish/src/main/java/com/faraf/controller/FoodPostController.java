package com.faraf.controller;


import com.faraf.dto.FoodPostRequestDto;
import com.faraf.entity.User;
import com.faraf.service.FoodPostService;
import com.faraf.service.FoodPostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/food")
public class FoodPostController {


    private final FoodPostService foodPostService;

/*    //localhost:8080/api/v1/post?name=kebab
    @RequestMapping(params = "name", method = RequestMethod.GET)
    public List<User> findAllByCity(@RequestParam("name") String name) {
        return foodPostService.getAllByName(name);
    }

    //localhost:8080/api/v1/post?country=russia
    @RequestMapping(params = "country", method = RequestMethod.GET)
    public List<User> findAllByCountry(@RequestParam("country") String country) {
        return foodPostService.getAllByCountry(country);
    }

    //localhost:8080/api/v1/post?timeRequired=50
    @RequestMapping(params = "timeRequired", method = RequestMethod.GET)
    public List<User> findAllByTimeRequired(@RequestParam("timeRequired") int timeRequired) {
        return foodPostService.getAllByTimeRequired(timeRequired);
    }*/


    @PostMapping("/save")
    public void postFood(@RequestParam("userId") Long id, @RequestBody  FoodPostRequestDto requestDto) {
        foodPostService.addFoodPost(id, requestDto);
    }

}
