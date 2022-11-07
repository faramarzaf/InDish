package com.faraf.repository;

import com.faraf.BaseTestClass;
import com.faraf.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest extends BaseTestClass {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() {
        User sampleUser = getCorrectSampleUser();
        user = userRepository.save(sampleUser);
    }

    @Test
    public void saved_user_should_be_present() {
        assertThat(userRepository.findById(user.getId()).get()).isEqualTo(user);
    }

    @Test
    public void user_should_exists_by_email() {
        assertThat(userRepository.existsUserByEmail(user.getEmail())).isEqualTo(true);
    }


    @Test
    public void user_should_exists_by_username() {
        assertThat(userRepository.existsUserByUserName(user.getUserName())).isEqualTo(true);
    }

    @Test
    public void it_should_find_user_by_id() {
        Optional<User> userById = userRepository.findUserById(user.getId());
        assertThat(userById).isNotNull();
    }

    @Test
    public void it_should_find_user_by_username() {
        Optional<User> byUserName = userRepository.findByUserName(user.getUserName());
        assertThat(byUserName).isNotNull();
    }

    @Test
    public void it_should_find_user_by_email() {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        assertThat(byEmail).isNotNull();
    }

    @Test
    public void it_should_delete_user_by_id() {
        assertThat(userRepository.findUserById(user.getId())).isNotEmpty();
        userRepository.deleteUserById(user.getId());
        assertThat(userRepository.findUserById(user.getId())).isEmpty();
    }

    @Test
    public void it_should_not_save_user_with_empty_username() {
        User user = new User();
        user.setEmail("sample@gmail.com");
        assertThatThrownBy(() -> userRepository.save(user))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void it_should_not_save_user_with_empty_email() {
        User user = new User();
        user.setUserName("user1");
        assertThatThrownBy(() -> userRepository.save(user))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void it_should_not_save_user_with_invalid_email_address() {
        User user = new User();
        user.setUserName("user1");
        user.setEmail("samplegmail.10");
        assertThatThrownBy(() -> userRepository.save(user))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void it_should_not_save_user_with_empty_password() {
        User user = new User();
        user.setUserName("user1");
        user.setEmail("sample@gmail.com");
        user.setBio("sample bio");
        assertThatThrownBy(() -> userRepository.save(user))
                .isInstanceOf(ConstraintViolationException.class);
    }


}
