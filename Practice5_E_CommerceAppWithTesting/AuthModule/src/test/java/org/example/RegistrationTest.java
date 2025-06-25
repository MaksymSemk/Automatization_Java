package org.example;


import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        AuthModuleTest.class
})
@IncludeTags("Registration")
public class RegistrationTest {
}