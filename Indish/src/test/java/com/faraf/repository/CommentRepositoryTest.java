package com.faraf.repository;

import com.faraf.BaseTestClass;
import com.faraf.entity.Comment;
import com.faraf.entity.FoodPost;
import com.faraf.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class CommentRepositoryTest extends BaseTestClass {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FoodPostRepository foodPostRepository;

    @Autowired
    private UserRepository userRepository;

    private FoodPost sampleVeganFoodPost;
    private FoodPost sampleNonVeganFoodPost;
    private Comment sampleComment;
    private User sampleUser;

    @BeforeEach
    public void setUp() {
        initSampleData();
        saveSampleData();
        sampleVeganFoodPost.setUser(sampleUser);
        sampleNonVeganFoodPost.setUser(sampleUser);
    }

    @Test
    public void saved_comment_should_be_present() {
        assertThat(commentRepository.findById(sampleComment.getId()).get()).isEqualTo(sampleComment);
    }

    @Test
    public void it_should_find_comments_by_postId() {
        List<Comment> allByPost_id = commentRepository.findAllByPost_Id(sampleVeganFoodPost.getId());
        assertThat(allByPost_id).isNotNull();
        assertThat(allByPost_id).isNotEmpty();
    }

    @Test
    public void it_should_find_comments_by_userId() {
        List<Comment> allByUser_id = commentRepository.findAllByUser_Id(sampleUser.getId());
        assertThat(allByUser_id).isNotNull();
        assertThat(allByUser_id).isNotEmpty();
    }

    private void initSampleData() {
        sampleVeganFoodPost = getSampleVeganFoodPost();
        sampleNonVeganFoodPost = getSampleNonVeganFoodPost();
        sampleUser = getCorrectSampleUser();
        sampleComment = getSampleComment(sampleUser, sampleVeganFoodPost);
    }

    private void saveSampleData() {
        sampleVeganFoodPost = foodPostRepository.save(sampleVeganFoodPost);
        sampleNonVeganFoodPost = foodPostRepository.save(sampleNonVeganFoodPost);
        sampleUser = userRepository.save(sampleUser);
        sampleComment = commentRepository.save(sampleComment);
    }
}
