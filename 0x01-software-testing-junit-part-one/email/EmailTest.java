// javac -cp "junit-jupiter-api-6.0.0.jar:apiguardian-api-1.1.2.jar" -d target EmailTest.java Person.java
// java -jar junit-platform-console-standalone-6.0.0.jar execute -cp target --select-class EmailTest

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmailTest {
    @Test
    void test_email_with_at_symbol() {
        assertTrue(Person.isEmailValid("email_test@domain.com"));
    }

    @Test
    void test_email_without_at_symbol() {
        assertFalse(Person.isEmailValid("email_testdomain.com"));
    }

    @Test
    void test_email_over_50_character() {
        assertEquals(
            false,
            Person.isEmailValid("email_test_very_long_should_not_be_valid@domain.com")
        );
    }
}