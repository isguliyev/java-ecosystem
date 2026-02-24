package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
    private Boolean status;

    public Product() {}

    public Product(
        String name,
        Integer quantity,
        Double price,
        Boolean status
    ) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
            "%s [id=%d, name=%s, quantity=%d, price=%f, status=%b]",
            this.getClass().getSimpleName(),
            this.id,
            this.name,
            this.quantity,
            this.price,
            this.status
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Product)) {
            return false;
        }

        Product product = (Product) object;

        return this.id != null && this.id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}