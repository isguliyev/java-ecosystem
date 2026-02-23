import java.time.LocalDate;
import java.time.Period;

public class Person {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private boolean anotherCompanyOwner;
    private boolean pensioner;
    private boolean publicServer;
    private float salary;

    public boolean isMEI() {
        int age = Period.between(this.birthDate, LocalDate.now()).getYears();

        return !(
            this.anotherCompanyOwner
            || this.pensioner
            || this.publicServer
            || age < 18
            || calculateYearlySalary() > 130000.0f
        );
    }

    public float calculateYearlySalary() {
        return this.salary * 12.0f;
    }

    public String fullName() {
        return String.format("%s %s", this.name, this.surname);
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public boolean isAnotherCompanyOwner() {
        return this.anotherCompanyOwner;
    }

    public boolean isPensioner() {
        return this.pensioner;
    }

    public boolean isPublicServer() {
        return this.publicServer;
    }

    public float getSalary() {
        return this.salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAnotherCompanyOwner(boolean anotherCompanyOwner) {
        this.anotherCompanyOwner = anotherCompanyOwner;
    }

    public void setPensioner(boolean pensioner) {
        this.pensioner = pensioner;
    }

    public void setPublicServer(boolean publicServer) {
        this.publicServer = publicServer;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}