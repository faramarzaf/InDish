package com.faraf.controller;


import com.faraf.dto.request.DeleteFoodPostRequestDto;
import com.faraf.dto.request.FoodPostRequestDto;
import com.faraf.dto.request.FoodPostUpdateRequestDto;
import com.faraf.dto.response.FoodPostResponseDto;
import com.faraf.service.FoodPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("by-user-id")
    public List<FoodPostResponseDto> findAllByUserId(@RequestParam Long userId,
                                                     @RequestParam int pageNo,
                                                     @RequestParam int pageSize,
                                                     @RequestParam String sort) {
        return foodPostService.findAllByUserId(userId, pageNo, pageSize, sort);
    }

    @GetMapping("by-username")
    public List<FoodPostResponseDto> findAllByUsername(@RequestParam String username,
                                                       @RequestParam int pageNo,
                                                       @RequestParam int pageSize,
                                                       @RequestParam String sort) {
        return foodPostService.findAllByUsername(username, pageNo, pageSize, sort);
    }

    @GetMapping("by-country")
    public List<FoodPostResponseDto> findAllByOriginCountry(@RequestParam String country,
                                                            @RequestParam int pageNo,
                                                            @RequestParam int pageSize,
                                                            @RequestParam String sort) {
        return foodPostService.findAllByOriginCountry(country, pageNo, pageSize, sort);
    }


    @GetMapping("vegans")
    public List<FoodPostResponseDto> findAllByVeganFoodTrue(@RequestParam int pageNo,
                                                            @RequestParam int pageSize,
                                                            @RequestParam String sort) {
        return foodPostService.findAllByVeganFoodTrue(pageNo, pageSize, sort);
    }


    @GetMapping("non-vegans")
    public List<FoodPostResponseDto> findAllByVeganFoodFalse(@RequestParam int pageNo,
                                                             @RequestParam int pageSize,
                                                             @RequestParam String sort) {
        return foodPostService.findAllByVeganFoodFalse(pageNo, pageSize, sort);
    }


    @GetMapping("by-time-required")
    public List<FoodPostResponseDto> findAllByTimeRequiredEquals(@RequestParam int hour,
                                                                 @RequestParam int pageNo,
                                                                 @RequestParam int pageSize,
                                                                 @RequestParam String sort) {
        return foodPostService.findAllByTimeRequiredEquals(hour, pageNo, pageSize, sort);
    }


    @GetMapping("by-time-required-between")
    public List<FoodPostResponseDto> findAllByTimeRequiredBetween(@RequestParam int startHour,
                                                                  @RequestParam int endHour,
                                                                  @RequestParam int pageNo,
                                                                  @RequestParam int pageSize,
                                                                  @RequestParam String sort) {
        return foodPostService.findAllByTimeRequiredBetween(startHour, endHour, pageNo, pageSize, sort);
    }


    @PostMapping("/save")
    @PreAuthorize("#requestDto.userId == authentication.principal.id")
    public FoodPostResponseDto postFood(@RequestBody FoodPostRequestDto requestDto) {
        return foodPostService.addFoodToUser(requestDto);
    }

    @PutMapping("/update")
    @PreAuthorize("#requestDto.userId == authentication.principal.id")
    public void updateFood(@RequestBody FoodPostUpdateRequestDto requestDto) {
        foodPostService.updateFoodPost(requestDto);
    }

    @DeleteMapping("/delete-by-food-id")
    @PreAuthorize("#requestDto.userId == authentication.principal.id")
    public void deleteFood(@RequestBody DeleteFoodPostRequestDto requestDto) {
        foodPostService.deletePostByFoodId(requestDto);
    }

}
