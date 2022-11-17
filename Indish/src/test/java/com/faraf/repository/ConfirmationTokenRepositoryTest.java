package com.faraf.repository;

import com.faraf.BaseTestClass;
import com.faraf.entity.ConfirmationToken;
import com.faraf.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConfirmationTokenRepositoryTest extends BaseTestClass {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    private ConfirmationToken sampleConfirmationToken;
    private User sampleUser;

    @Before
    public void setUp() {
        sampleUser = getCorrectSampleUser();
        sampleConfirmationToken = getSampleConfirmationToken(sampleUser);
        sampleUser = userRepository.save(sampleUser);
        sampleConfirmationToken = confirmationTokenRepository.save(sampleConfirmationToken);
    }

    @Test
    public void saved_confirmationToken_should_be_present() {
        assertThat(confirmationTokenRepository.findById(sampleConfirmationToken.getId()).get()).isEqualTo(sampleConfirmationToken);
    }

    @Test
    public void it_should_find_confirmationToken_by_UserEmail() {
        Optional<ConfirmationToken> confirmationTokenByUserEmail = confirmationTokenRepository.findConfirmationTokenByUser_Email(sampleUser.getEmail());
        assertThat(confirmationTokenByUserEmail).isNotNull();
        assertThat(confirmationTokenByUserEmail).isNotEmpty();
    }

    @Test
    public void it_should_find_confirmationToken_by_confirmationToken() {
        Optional<ConfirmationToken> confirmationTokenByConfirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(sampleConfirmationToken.getConfirmationToken());
        assertThat(confirmationTokenByConfirmationToken).isNotNull();
        assertThat(confirmationTokenByConfirmationToken).isNotEmpty();
    }
}
