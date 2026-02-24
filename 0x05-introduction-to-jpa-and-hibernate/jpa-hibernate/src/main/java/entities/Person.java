package entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Short age;
    private String cpf;
    private LocalDate birthDate;

    public Person() {}

    public Person(
        String name,
        String email,
        String cpf,
        LocalDate birthDate
    ) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.setBirthDate(birthDate);
    }

    @Override
    public String toString() {
        return String.format(
            "%s [id=%d, name=%s, email=%s, age=%d, cpf=%s, birthDate=%s]",
            this.getClass().getSimpleName(),
            this.id,
            this.name,
            this.email,
            this.age,
            this.cpf,
            this.birthDate
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Person)) {
            return false;
        }

        Person person = (Person) object;

        return this.id != null && this.id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        this.age = (short) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }
}