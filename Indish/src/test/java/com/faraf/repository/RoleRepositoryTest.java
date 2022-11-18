package com.faraf.repository;

import com.faraf.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    public void setUp() {
        role = getSampleRole();
        role = roleRepository.save(role);
    }

    @Test
    public void saved_role_should_be_present() {
        assertThat(roleRepository.findById(role.getId()).get()).isEqualTo(role);
    }

    private Role getSampleRole() {
        Role role = new Role();
        role.setName("ADMIN");
        return role;
    }


}
