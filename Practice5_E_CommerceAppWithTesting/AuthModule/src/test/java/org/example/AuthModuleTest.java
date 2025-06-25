package org.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class AuthModuleTest {

    @Tag("Registration")
    @Test
    void testRegistration(){
        assertTrue(AuthModule.registerUser("user1", "1"));
    }

    @Tag("Registration")
    @Test
    void failToRegisterTheSameUser(){
        AuthModule.registerUser("user2", "1");
        assertFalse(AuthModule.registerUser("user2", "2"));
    }

    @Tag("Registration")
    @ParameterizedTest
    @CsvSource({
            "alice, pass1",
            "bob, pass2"
    })
    void testMultipleRegistrations(String username, String password) {
        assertTrue(AuthModule.registerUser(username, password));
    }

    @Tag("Login")
    @Test
    void testRegisteredUserLogin(){
        boolean registered = AuthModule.registerUser("user3", "1");

        assumeTrue(registered, "User was not registered, skipping login test");

       assertNotNull(AuthModule.login("user3", "1"));
    }

    @Tag("Login")
    @Test
    void testUnRegisteredRegisteredUserLogin(){
        assertNull(AuthModule.login("unregisteredUser", "2"));
    }
}