package com.faraf.repository;

import com.faraf.BaseTestClass;
import com.faraf.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
public class UserRepositoryTest extends BaseTestClass {

    @Autowired
    private UserRepository userRepository;

    private User sampleUser;

    @BeforeEach
    public void setUp() {
        sampleUser = getCorrectSampleUser();
        sampleUser = userRepository.save(sampleUser);
    }

    @Test
    public void saved_user_should_be_present() {
        assertThat(userRepository.findById(sampleUser.getId()).get()).isEqualTo(sampleUser);
    }

    @Test
    public void user_should_exists_by_email() {
        assertThat(userRepository.existsUserByEmail(sampleUser.getEmail())).isEqualTo(true);
    }


    @Test
    public void user_should_exists_by_username() {
        assertThat(userRepository.existsUserByUserName(sampleUser.getUserName())).isEqualTo(true);
    }

    @Test
    public void it_should_find_user_by_id() {
        Optional<User> userById = userRepository.findUserById(sampleUser.getId());
        assertThat(userById).isNotNull();
        assertThat(userById).isNotEmpty();
    }

    @Test
    public void it_should_find_user_by_username() {
        Optional<User> byUserName = userRepository.findByUserName(sampleUser.getUserName());
        assertThat(byUserName).isNotNull();
        assertThat(byUserName).isNotEmpty();
    }

    @Test
    public void it_should_find_user_by_email() {
        Optional<User> byEmail = userRepository.findByEmail(sampleUser.getEmail());
        assertThat(byEmail).isNotNull();
        assertThat(byEmail).isNotEmpty();
    }

    @Test
    public void it_should_delete_user_by_id() {
        assertThat(userRepository.findUserById(sampleUser.getId())).isNotEmpty();
        userRepository.deleteUserById(sampleUser.getId());
        assertThat(userRepository.findUserById(sampleUser.getId())).isEmpty();
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
        assertThrows(ConstraintViolationException.class, () -> userRepository.save(user));
    }

    @Test
    public void it_should_not_save_user_with_invalid_email_address() {
        User user = new User();
        user.setUserName("user1");
        user.setEmail("samplegmail.10");
        assertThrows(ConstraintViolationException.class, () -> userRepository.save(user));
    }

    @Test
    public void it_should_not_save_user_with_empty_password() {
        User user = new User();
        user.setUserName("user1");
        user.setEmail("sample@gmail.com");
        user.setBio("sample bio");
        assertThrows(ConstraintViolationException.class, () -> userRepository.save(user));
    }


}
