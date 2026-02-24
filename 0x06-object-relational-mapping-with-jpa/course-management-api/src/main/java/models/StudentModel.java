package models;

import entities.Student;
import entities.Course;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.List;

public class StudentModel {
    private final EntityManagerFactory entityManagerFactory;

    public StudentModel(EntityManagerFactory entityManagerFactory) {
        if (entityManagerFactory == null) {
            throw new IllegalArgumentException(
                "Cannot create StudentModel object: entityManagerFactory is null"
            );
        }

        this.entityManagerFactory = entityManagerFactory;
    }

    public void create(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Cannot create Student: student is null");
        }

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(student);
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


    public Student update(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Cannot update Student: student is null");
        }

        if (student.getId() == null || student.getId() < 1) {
            throw new IllegalArgumentException("Cannot update Student: invalid student id");
        }

        Student mergedStudent = null;
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            mergedStudent = entityManager.merge(student);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            throw exception;
        } finally {
            entityManager.close();
        }

        return mergedStudent;
    }

    public Optional<Student> findById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Cannot find Student: invalid student id");
        }

        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            return Optional.ofNullable(entityManager.find(Student.class, id));
        }
    }

    public List<Student> findAll() {
        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(
                "SELECT s FROM Student s",
                Student.class
            ).getResultList();
        }
    }

    public void deleteById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Cannot delete Student: invalid student id");
        }

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            Student student = entityManager.find(Student.class, id);

            if (student == null) {
                throw new IllegalArgumentException(
                    String.format("Cannot delete Student: no student found with id=%d", id)
                );
            }

            entityManager.remove(student);
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
}