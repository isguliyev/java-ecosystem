package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.HashSet;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;
    @ManyToMany(
        mappedBy = "students",
        fetch = FetchType.LAZY,
        cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    private Set<Course> courses;
    @OneToMany(
        mappedBy = "student",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private Set<Address> addresses;
    @OneToMany(
        mappedBy = "student",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private Set<Phone> phones;

    public Student() {
        this.courses = new HashSet<Course>();
        this.addresses = new HashSet<Address>();
        this.phones = new HashSet<Phone>();
    }

    public Student(String data, Set<Address> addresses, Set<Phone> phones) {
        this.data = data;
        this.setAddresses(addresses);
        this.setPhones(phones);
        this.courses = new HashSet<Course>();
    }

    public Student(String data, Set<Course> courses, Set<Address> addresses, Set<Phone> phones) {
        this.data = data;
        this.setCourses(courses);
        this.setAddresses(addresses);
        this.setPhones(phones);
    }

    @Override
    public String toString() {
        return String.format(
            "%s [id=%d, data=%s]",
            this.getClass().getSimpleName(),
            this.id,
            this.data
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Student)) {
            return false;
        }

        Student student = (Student) object;

        return this.id != null && this.id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    public void setCourses(Set<Course> courses) {
        if (this.courses == null) {
            this.courses = new HashSet<Course>();
        } else {
            this.courses.clear();
        }

        if (courses == null) {
            return;
        }

        for (Course course : courses) {
            course.getStudents().add(this);
            this.courses.add(course);
        }
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses == null) {
            this.addresses = new HashSet<Address>();
        } else {
            this.addresses.clear();
        }

        if (addresses == null) {
            return;
        }

        for (Address address : addresses) {
            address.setStudent(this);
            this.addresses.add(address);
        }
     }

     public void setPhones(Set<Phone> phones) {
        if (this.phones == null) {
            this.phones = new HashSet<Phone>();
        } else {
            this.phones.clear();
        }

        if (phones == null) {
            return;
        }

        for (Phone phone : phones) {
            phone.setStudent(this);
            this.phones.add(phone);
        }
     }
}