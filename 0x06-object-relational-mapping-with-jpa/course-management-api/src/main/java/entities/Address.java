package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;
    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private Student student;

    public Address() {}

    public Address(String data) {
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

        if (!(object instanceof Address)) {
            return false;
        }

        Address address = (Address) object;

        return this.id != null && this.id.equals(address.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}