package com.faraf.repository;

import com.faraf.BaseTestClass;
import com.faraf.entity.FoodPost;
import com.faraf.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FoodPostRepositoryTest extends BaseTestClass {

    @Autowired
    private FoodPostRepository foodPostRepository;

    @Autowired
    private UserRepository userRepository;

    private FoodPost foodPost;

    private User user;

    @Before
    public void setUp() {
        FoodPost sampleVeganFoodPost = getSampleVeganFoodPost();
        FoodPost sampleNonVeganFoodPost = getSampleNonVeganFoodPost();
        User sampleUser = getCorrectSampleUser();

        foodPost = foodPostRepository.save(sampleVeganFoodPost);
        foodPost = foodPostRepository.save(sampleNonVeganFoodPost);
        user = userRepository.save(sampleUser);
    }

    @Test
    public void saved_post_should_be_present() {
        assertThat(foodPostRepository.findById(foodPost.getId()).get()).isEqualTo(foodPost);
    }


    @Test
    public void it_should_find_posts_by_userId() {
        foodPost.setUser(user);
        List<FoodPost> allByUserId = foodPostRepository.findAllByUser_Id(user.getId());
        assertThat(allByUserId).isNotNull();
    }

    @Test
    public void it_should_find_posts_by_username() {
        foodPost.setUser(user);
        List<FoodPost> allByUsername = foodPostRepository.findByUser_UserName(user.getUserName());
        assertThat(allByUsername).isNotNull();
    }

    @Test
    public void it_should_find_posts_by_food_originCountry() {
        foodPost.setUser(user);
        List<FoodPost> allByFoodOriginCountry = foodPostRepository.findAllByOriginCountry(foodPost.getOriginCountry());
        assertThat(allByFoodOriginCountry).isNotNull();
    }

    @Test
    public void it_should_find_all_vegan_foods() {
        foodPost.setUser(user);
        List<FoodPost> allVeganFoods = foodPostRepository.findAllByVeganFoodTrue();
        assertThat(allVeganFoods).isNotNull();
    }

    @Test
    public void it_should_find_all_non_vegan_foods() {
        foodPost.setUser(user);
        List<FoodPost> allNonVeganFoods = foodPostRepository.findAllByVeganFoodFalse();
        assertThat(allNonVeganFoods).isNotNull();
    }


    @Test
    public void it_should_find_foods_with_given_time_required_to_ready() {

    }

    @Test
    public void it_should_find_foods_with_given_startTime_and_endTime_required_to_ready() {

    }


}
