package models;

import entities.Person;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.List;

public class PersonModel {
    private final EntityManagerFactory entityManagerFactory;

    public PersonModel(EntityManagerFactory entityManagerFactory) {
        if (entityManagerFactory == null) {
            throw new IllegalArgumentException(
                "Cannot create PersonModel object: entityManagerFactory is null."
            );
        }

        this.entityManagerFactory = entityManagerFactory;
    }

    public void create(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Cannot create Person: person is null.");
        }

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(person);
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

    public Person update(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Cannot update Person: person is null.");
        }

        if (person.getId() == null || person.getId() < 1) {
            throw new IllegalArgumentException("Cannot update Person: invalid person id.");
        }

        Person mergedPerson = null;
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            mergedPerson = entityManager.merge(person);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            throw exception;
        } finally {
            entityManager.close();
        }

        return mergedPerson;
    }

    public void deleteById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Cannot delete Person: invalid person id.");
        }

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            Person person = entityManager.find(Person.class, id);

            if (person == null) {
                throw new IllegalArgumentException(
                    String.format("Cannot delete Person: no person found with id=%d.", id)
                );
            }

            entityManager.remove(person);
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

    public Optional<Person> findById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Cannot find Person: invalid person id.");
        }

        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            return Optional.ofNullable(entityManager.find(Person.class, id));
        }
    }

    public List<Person> findAll() {
        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(
                "SELECT p FROM Person p",
                Person.class
            ).getResultList();
        }
    }
}