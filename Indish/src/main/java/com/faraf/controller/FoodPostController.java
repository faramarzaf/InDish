package com.faraf.controller;


import com.faraf.dto.FoodPostRequestDto;
import com.faraf.dto.FoodPostResponseDto;
import com.faraf.service.FoodPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/food")
public class FoodPostController {


    private final FoodPostService foodPostService;

    @GetMapping("by-username")
    public List<FoodPostResponseDto> findAllByUsername(@RequestParam String username) {
        return foodPostService.findAllByUsername(username);
    }

    @GetMapping("by-country")
    public List<FoodPostResponseDto> findAllByOriginCountry(@RequestParam String country) {
        return foodPostService.findAllByOriginCountry(country);
    }


    @GetMapping("vegans")
    public List<FoodPostResponseDto> findAllByVeganFoodTrue() {
        return foodPostService.findAllByVeganFoodTrue();
    }


    @GetMapping("non-vegans")
    public List<FoodPostResponseDto> findAllByVeganFoodFalse() {
        return foodPostService.findAllByVeganFoodFalse();
    }


    @GetMapping("by-time-required")
    public List<FoodPostResponseDto> findAllByTimeRequiredEquals(@RequestParam int hour) {
        return foodPostService.findAllByTimeRequiredEquals(hour);
    }


    @GetMapping("by-time-required-between")
    public List<FoodPostResponseDto> findAllByTimeRequiredBetween(@RequestParam int startHour, @RequestParam int endHour) {
        return foodPostService.findAllByTimeRequiredBetween(startHour, endHour);
    }


    @PostMapping("/save")
    public void postFood(@RequestBody FoodPostRequestDto requestDto) {
        foodPostService.addFoodPost(requestDto);
    }

}
