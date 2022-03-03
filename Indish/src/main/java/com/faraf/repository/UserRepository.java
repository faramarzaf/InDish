package com.faraf.repository;


import com.faraf.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    boolean existsUserByEmail(String email);

    boolean existsUserByUserName(String username);

    Optional<User> findUserById(Long id);

    Optional<User> findByUserName(String username);

    Optional<User> findByEmail(String email);

    void deleteUserById(Long id);

    void deleteUserByEmail(String email);
}
