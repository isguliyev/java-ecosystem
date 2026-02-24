package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@Entity
@Table(name = "course_material")
public class CourseMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;
    @OneToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    private Course course;

    public CourseMaterial() {}

    public CourseMaterial(String data) {
        this.data = data;
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

        if (!(object instanceof CourseMaterial)) {
            return false;
        }

        CourseMaterial courseMaterial = (CourseMaterial) object;

        return this.id != null && this.id.equals(courseMaterial.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}