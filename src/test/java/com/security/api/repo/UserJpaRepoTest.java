package com.security.api.repo;

import com.security.api.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserJpaRepoTest {

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenFindByUid_thenReturnUser() {
//        String uid = "test@test.com";
//        String name = "test9999999";
        String uid = "test1";
        String name = "test1";
        // given
        userJpaRepo.save(User.builder()
                .uid(uid)
                .password(passwordEncoder.encode("test"))
                .name("test9999")
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        //wher
        Optional<User> user = userJpaRepo.findByUid(uid);

        assertNotNull(user);    // user객체가 null이 아닌지 체크
        assertTrue(user.isPresent());   // user객체가 존재여부 true/false 체크
        assertEquals(user.get().getName(), name);   // user객체의 name과 name변수 값이 같은지
//        assertThat(user.get().getName(),is(name));  // user객체의 name과 name변수 값이 값이 같은지

    }
}