package com.faraf.controller;


import com.faraf.dto.request.FoodPostRequestDto;
import com.faraf.dto.response.FoodPostResponseDto;
import com.faraf.dto.request.FoodPostUpdateRequestDto;
import com.faraf.service.FoodPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/food")
public class FoodPostController {


    private final FoodPostService foodPostService;

    @GetMapping("/by-id")
    public FoodPostResponseDto findById(@RequestParam Long id) {
        return foodPostService.findById(id);
    }

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
    public FoodPostResponseDto postFood(@RequestBody FoodPostRequestDto requestDto) {
        return foodPostService.addFoodToUser(requestDto);
    }

    @PutMapping("/update")
    public void updateFood(@RequestBody FoodPostUpdateRequestDto requestDto) {
        foodPostService.updateFoodPost(requestDto);
    }
}
