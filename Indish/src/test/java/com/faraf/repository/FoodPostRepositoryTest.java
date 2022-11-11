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

    private FoodPost sampleVeganFoodPost;
    private FoodPost sampleNonVeganFoodPost;
    private User user;

    @Before
    public void setUp() {
        sampleVeganFoodPost = getSampleVeganFoodPost();
        sampleNonVeganFoodPost = getSampleNonVeganFoodPost();
        User sampleUser = getCorrectSampleUser();

        sampleVeganFoodPost = foodPostRepository.save(sampleVeganFoodPost);
        sampleNonVeganFoodPost = foodPostRepository.save(sampleNonVeganFoodPost);
        user = userRepository.save(sampleUser);
    }

    @Test
    public void saved_post_should_be_present() {
        assertThat(foodPostRepository.findById(sampleVeganFoodPost.getId()).get()).isEqualTo(sampleVeganFoodPost);
    }

    @Test
    public void it_should_find_posts_by_userId() {
        sampleVeganFoodPost.setUser(user);
        List<FoodPost> allByUserId = foodPostRepository.findAllByUser_Id(user.getId());
        assertThat(allByUserId).isNotNull();
    }

    @Test
    public void it_should_find_posts_by_username() {
        sampleVeganFoodPost.setUser(user);
        List<FoodPost> allByUsername = foodPostRepository.findByUser_UserName(user.getUserName());
        assertThat(allByUsername).isNotNull();
    }

    @Test
    public void it_should_find_posts_by_food_originCountry() {
        sampleVeganFoodPost.setUser(user);
        List<FoodPost> allByFoodOriginCountry = foodPostRepository.findAllByOriginCountry(sampleVeganFoodPost.getOriginCountry());
        assertThat(allByFoodOriginCountry).isNotNull();
    }

    @Test
    public void it_should_find_all_vegan_foods() {
        sampleVeganFoodPost.setUser(user);
        sampleNonVeganFoodPost.setUser(user);
        List<FoodPost> allVeganFoods = foodPostRepository.findAllByVeganFoodTrue();
        assertThat(allVeganFoods).isNotNull();
        assertThat(allVeganFoods).doesNotContain(sampleNonVeganFoodPost);
    }

    @Test
    public void it_should_find_all_non_vegan_foods() {
        sampleVeganFoodPost.setUser(user);
        sampleNonVeganFoodPost.setUser(user);
        List<FoodPost> allNonVeganFoods = foodPostRepository.findAllByVeganFoodFalse();
        assertThat(allNonVeganFoods).isNotNull();
        assertThat(allNonVeganFoods).doesNotContain(sampleVeganFoodPost);
    }

    @Test
    public void it_should_find_foods_with_given_time_required_to_ready() {
        sampleVeganFoodPost.setUser(user);
        List<FoodPost> allByTimeRequiredEquals = foodPostRepository.findAllByTimeRequiredEquals(sampleVeganFoodPost.getTimeRequired());
        assertThat(allByTimeRequiredEquals).isNotNull();
    }

    @Test
    public void it_should_find_foods_with_given_startTime_and_endTime_required_to_ready() {
        sampleVeganFoodPost.setUser(user);
        List<FoodPost> allByTimeRequiredBetween = foodPostRepository.findAllByTimeRequiredBetween(0, sampleVeganFoodPost.getTimeRequired());
        assertThat(allByTimeRequiredBetween).isNotNull();
    }


}
