package com.faraf.repository;

import com.faraf.BaseTestClass;
import com.faraf.entity.FoodPost;
import com.faraf.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class FoodPostRepositoryTest extends BaseTestClass {

    @Autowired
    private FoodPostRepository foodPostRepository;

    @Autowired
    private UserRepository userRepository;

    private FoodPost sampleVeganFoodPost;
    private FoodPost sampleNonVeganFoodPost;
    private User sampleUser;
    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        initSampleData();
        saveSampleData();
    }

    @Test
    public void saved_post_should_be_present() {
        assertThat(foodPostRepository.findById(sampleVeganFoodPost.getId()).get()).isEqualTo(sampleVeganFoodPost);
    }

    @Test
    public void it_should_find_posts_by_userId() {
        sampleVeganFoodPost.setUser(sampleUser);
        List<FoodPost> allByUserId = foodPostRepository.findAllByUser_Id(sampleUser.getId());
        assertThat(allByUserId).isNotNull();
        assertThat(allByUserId).isNotEmpty();
    }

    @Test
    public void it_should_find_post_page_by_userId() {
        sampleVeganFoodPost.setUser(sampleUser);
        List<FoodPost> allByUserId = foodPostRepository.findAllByUser_Id(sampleUser.getId(), pageable).getContent();
        assertThat(allByUserId).isNotNull();
        assertThat(allByUserId).isNotEmpty();
    }

    @Test
    public void it_should_find_posts_by_username() {
        sampleVeganFoodPost.setUser(sampleUser);
        List<FoodPost> allByUsername = foodPostRepository.findByUser_UserName(sampleUser.getUserName(), pageable).getContent();
        assertThat(allByUsername).isNotNull();
        assertThat(allByUsername).isNotEmpty();
    }

    @Test
    public void it_should_find_posts_by_food_originCountry() {
        sampleVeganFoodPost.setUser(sampleUser);
        List<FoodPost> allByFoodOriginCountry = foodPostRepository.findAllByOriginCountry(sampleVeganFoodPost.getOriginCountry(), pageable).getContent();
        assertThat(allByFoodOriginCountry).isNotNull();
        assertThat(allByFoodOriginCountry).isNotEmpty();
    }

    @Test
    public void it_should_find_all_vegan_foods() {
        sampleVeganFoodPost.setUser(sampleUser);
        sampleNonVeganFoodPost.setUser(sampleUser);
        List<FoodPost> allVeganFoods = foodPostRepository.findAllByVeganFoodTrue(pageable).getContent();
        assertThat(allVeganFoods).isNotNull();
        assertThat(allVeganFoods).isNotEmpty();
        assertThat(allVeganFoods).doesNotContain(sampleNonVeganFoodPost);
    }

    @Test
    public void it_should_find_all_non_vegan_foods() {
        sampleVeganFoodPost.setUser(sampleUser);
        sampleNonVeganFoodPost.setUser(sampleUser);
        List<FoodPost> allNonVeganFoods = foodPostRepository.findAllByVeganFoodFalse(pageable).getContent();
        assertThat(allNonVeganFoods).isNotNull();
        assertThat(allNonVeganFoods).isNotEmpty();
        assertThat(allNonVeganFoods).doesNotContain(sampleVeganFoodPost);
    }

    @Test
    public void it_should_find_foods_with_given_time_required_to_ready() {
        sampleVeganFoodPost.setUser(sampleUser);
        List<FoodPost> allByTimeRequiredEquals = foodPostRepository.findAllByTimeRequiredEquals(sampleVeganFoodPost.getTimeRequired(), pageable).getContent();
        assertThat(allByTimeRequiredEquals).isNotNull();
        assertThat(allByTimeRequiredEquals).isNotEmpty();
    }

    @Test
    public void it_should_find_foods_with_given_startTime_and_endTime_required_to_ready() {
        sampleVeganFoodPost.setUser(sampleUser);
        List<FoodPost> allByTimeRequiredBetween = foodPostRepository.findAllByTimeRequiredBetween(0, sampleVeganFoodPost.getTimeRequired(), pageable).getContent();
        assertThat(allByTimeRequiredBetween).isNotNull();
        assertThat(allByTimeRequiredBetween).isNotEmpty();
    }

    private void saveSampleData() {
        sampleVeganFoodPost = foodPostRepository.save(sampleVeganFoodPost);
        sampleNonVeganFoodPost = foodPostRepository.save(sampleNonVeganFoodPost);
        sampleUser = userRepository.save(sampleUser);
    }

    private void initSampleData() {
        sampleVeganFoodPost = getSampleVeganFoodPost();
        sampleNonVeganFoodPost = getSampleNonVeganFoodPost();
        sampleUser = getCorrectSampleUser();
        pageable = getPageable();
    }
}
