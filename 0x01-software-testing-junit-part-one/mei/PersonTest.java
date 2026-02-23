// javac -cp "junit-jupiter-api-6.0.0.jar:apiguardian-api-1.1.2.jar" -d target PersonTest.java Person.java
// java -jar junit-platform-console-standalone-6.0.0.jar execute -cp target --select-class PersonTest

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class PersonTest {
    Person person;

    @BeforeEach
    public void setup() {
        person = new Person();
        person.setName("Paul");
        person.setSurname("McCartney");
        person.setBirthDate(LocalDate.of(2000, 1, 1));
        person.setAnotherCompanyOwner(false);
        person.setPensioner(false);
        person.setPublicServer(false);
    }

    @Test
    void show_full_name() {
        assertEquals("Paul McCartney", person.fullName());
    }

    @Test
    void test_calculateYearlySalary() {
        person.setSalary(1200.0f);
        assertEquals(14400.0f, person.calculateYearlySalary());
    }

    @Test
    void person_is_MEI() {
        person.setSalary(1200.0f);
        assertTrue(person.isMEI());
    }

    @Test
    void person_is_not_MEI() {
        person.setSalary(11000.0f);
        assertFalse(person.isMEI());
    }
}