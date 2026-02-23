// javac -cp "junit-jupiter-api-6.0.0.jar:apiguardian-api-1.1.2.jar:junit-jupiter-params-6.0.0.jar" -d target Person.java PersonTest.java
// java -jar junit-platform-console-standalone-6.0.0.jar execute -cp target --select-class PersonTest

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(Lifecycle.PER_CLASS)
public class PersonTest {
    Person person;

    @BeforeAll
    void setup() {
        person = new Person();
    }

    @ParameterizedTest
    @ValueSource(strings = {"PaulMcCartney2", "NeilArms2"})
    void check_user_valid(String username) {
        assertTrue(person.checkUser(username));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Paul#McCartney", "Neil@Arms"})
    void check_user_not_valid(String username) {
        assertFalse(person.checkUser(username));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123456789", "#$%1234"})
    void does_not_have_letters(String password) {
        assertFalse(person.checkPassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Abcabcdefgh@", "#hbtn@%tc"})
    void does_not_have_numbers(String password) {
        assertFalse(person.checkPassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Abc@123", "12$@hbt"})
    void does_not_have_eight_chars(String password) {
        assertFalse(person.checkPassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abC123456$", "Hbtn@1234", "Betty@1#2", "Hbtn@123"})
    void check_password_valid(String password) {
        assertTrue(person.checkPassword(password));
    }
}