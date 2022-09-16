package com.faraf.repository;

import com.faraf.entity.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findConfirmationTokenByConfirmationToken(String token);

    Optional<ConfirmationToken> findConfirmationTokenByUser_Email(String email);

}
