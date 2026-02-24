package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;

import lombok.Setter;
import lombok.Getter;

import java.util.Set;
import java.util.HashSet;

@Getter
@Setter
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;
    @OneToMany(
        mappedBy = "teacher",
        fetch = FetchType.LAZY,
        cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    private Set<Course> courses;

    public Teacher() {
        this.courses = new HashSet<Course>();
    }

    public Teacher(String data) {
        this.data = data;
        this.courses = new HashSet<Course>();
    }

    public Teacher(String data, Set<Course> courses) {
        this.data = data;
        this.setCourses(courses);
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

        if (!(object instanceof Teacher)) {
            return false;
        }

        Teacher teacher = (Teacher) object;

        return this.id != null && this.id.equals(teacher.id);
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
            course.setTeacher(this);
            this.courses.add(course);
        }
    }
}