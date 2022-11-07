package com.faraf.repository;

import com.faraf.entity.Role;
import com.faraf.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private Role role;

    @Before
    public void setUp() {
        Role sampleUser = getSampleRole();
        role = roleRepository.save(sampleUser);
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
