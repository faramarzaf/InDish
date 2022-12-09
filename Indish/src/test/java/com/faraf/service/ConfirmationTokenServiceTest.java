package com.faraf.service;

import com.faraf.BaseTestClass;
import com.faraf.entity.ConfirmationToken;
import com.faraf.entity.User;
import com.faraf.exception.AuthException;
import com.faraf.repository.ConfirmationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ConfirmationTokenServiceTest extends BaseTestClass {

    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    private User sampleUser;
    private ConfirmationToken confirmationToken;


    @BeforeEach
    public void setup() {
        confirmationTokenService = new ConfirmationTokenService(confirmationTokenRepository);
        sampleUser = getCorrectSampleUser();
        confirmationToken = getSampleConfirmationToken(sampleUser);
    }

    @Test
    public void it_should_save_confirmationToken() {
        when(confirmationTokenRepository.save(confirmationToken)).thenReturn(confirmationToken);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        verify(confirmationTokenRepository, times(1)).save(confirmationToken);
    }

    @Test
    public void it_should_delete_confirmationToken_by_id() {
        doNothing().when(confirmationTokenRepository).deleteById(anyLong());
        confirmationTokenService.deleteConfirmationToken(anyLong());
        verify(confirmationTokenRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void it_should_find_confirmationToken_by_token() {
        when(confirmationTokenRepository.findConfirmationTokenByConfirmationToken(anyString())).thenReturn(Optional.of(confirmationToken));
        ConfirmationToken confirmationTokenByToken = confirmationTokenService.findConfirmationTokenByToken(anyString());
        assertNotNull(confirmationTokenByToken);
        verify(confirmationTokenRepository, times(1)).findConfirmationTokenByConfirmationToken(anyString());
    }


    @Test
    public void it_should_throw_exception_find_confirmationToken_by_token() {
        when(confirmationTokenRepository.findConfirmationTokenByConfirmationToken(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> confirmationTokenService.findConfirmationTokenByToken(anyString()))
                .isInstanceOf(AuthException.class)
                .hasMessage("Wrong credentials for email verification!");
    }


}
