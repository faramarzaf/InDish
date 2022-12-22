package com.faraf.service;

import com.faraf.entity.ConfirmationToken;
import com.faraf.exception.AuthException;
import com.faraf.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Transactional
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Transactional
    public void deleteConfirmationToken(Long id) {
        confirmationTokenRepository.deleteById(id);
    }


    public ConfirmationToken findConfirmationTokenByToken(String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
        if (optionalConfirmationToken.isPresent())
            return optionalConfirmationToken.get();

        else throw new AuthException("Wrong credentials for email verification!");
    }

    public Optional<ConfirmationToken> findConfirmationTokenByUserEmail(String email) {
        return confirmationTokenRepository.findConfirmationTokenByUser_Email(email);
    }


}
