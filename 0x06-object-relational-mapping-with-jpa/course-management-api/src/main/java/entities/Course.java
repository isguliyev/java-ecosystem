package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.HashSet;

@Getter
@Setter
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "course_student",
        joinColumns = @JoinColumn(name = "courseId"),
        inverseJoinColumns = @JoinColumn(name = "studentId")
    )
    private Set<Student> students;
    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    private CourseMaterial courseMaterial;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "teacherId", referencedColumnName = "id")
    private Teacher teacher;

    public Course() {
        this.students = new HashSet<Student>();
    }

    public Course(String data, CourseMaterial courseMaterial, Teacher teacher) {
        this.data = data;
        this.setCourseMaterial(courseMaterial);
        this.setTeacher(teacher);
        this.students = new HashSet<Student>();
    }

    public Course(String data, Set<Student> students, CourseMaterial courseMaterial) {
        this.data = data;
        this.setStudents(students);
        this.setCourseMaterial(courseMaterial);
    }

    public Course(
        String data,
        Set<Student> students,
        CourseMaterial courseMaterial,
        Teacher teacher
    ) {
        this.data = data;
        this.setStudents(students);
        this.setCourseMaterial(courseMaterial);
        this.setTeacher(teacher);
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

        if (!(object instanceof Course)) {
            return false;
        }

        Course course = (Course) object;

        return this.id != null && this.id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    public void setStudents(Set<Student> students) {
        if (this.students == null) {
            this.students = new HashSet<Student>();
        } else {
            this.students.clear();
        }

        if (students == null) {
            return;
        }

        for (Student student : students) {
            student.getCourses().add(this);
            this.students.add(student);
        }
    }

    public void setCourseMaterial(CourseMaterial courseMaterial) {
        if (courseMaterial == null) {
            return;
        }

        courseMaterial.setCourse(this);
        this.courseMaterial = courseMaterial;
    }

    public void setTeacher(Teacher teacher) {
        if (teacher == null) {
            return;
        }

        teacher.getCourses().add(this);
        this.teacher = teacher;
    }
}