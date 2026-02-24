package models;

import entities.Product;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.List;

public class ProductModel {
    private final EntityManagerFactory entityManagerFactory;

    public ProductModel(EntityManagerFactory entityManagerFactory) {
        if (entityManagerFactory == null) {
            throw new IllegalArgumentException(
                "Cannot create ProductModel object: entityManagerFactory is null."
            );
        }

        this.entityManagerFactory = entityManagerFactory;
    }

    public void create(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot create Product: product is null.");
        }

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(product);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            throw exception;
        } finally {
            entityManager.close();
        }
    }

    public Product update(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot update Product: product is null.");
        }

        if (product.getId() == null || product.getId() < 1) {
            throw new IllegalArgumentException("Cannot update Product: invalid product id.");
        }

        Product mergedProduct = null;
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            mergedProduct = entityManager.merge(product);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            throw exception;
        } finally {
            entityManager.close();
        }

        return mergedProduct;
    }

    public void deleteById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Cannot delete Product: invalid product id.");
        }

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            Product product = entityManager.find(Product.class, id);

            if (product == null) {
                throw new IllegalArgumentException(
                    String.format("Cannot delete Product: no product found with id=%d.", id)
                );
            }

            entityManager.remove(product);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            throw exception;
        } finally {
            entityManager.close();
        }
    }

    public Optional<Product> findById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Cannot find Product: invalid product id.");
        }

        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            return Optional.ofNullable(entityManager.find(Product.class, id));
        }
    }

    public List<Product> findAll() {
        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(
                "SELECT p FROM Product p",
                Product.class
            ).getResultList();
        }
    }
}