package models;

import entities.Course;
import entities.Student;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.List;

public class CourseModel {
    private final EntityManagerFactory entityManagerFactory;

    public CourseModel(EntityManagerFactory entityManagerFactory) {
        if (entityManagerFactory == null) {
            throw new IllegalArgumentException(
                "Cannot create CourseModel object: entityManagerFactory is null"
            );
        }

        this.entityManagerFactory = entityManagerFactory;
    }

    public void create(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Cannot create Course: course is null");
        }

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(course);
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

    public Course update(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Cannot update Course: course is null");
        }

        if (course.getId() == null || course.getId() < 1) {
            throw new IllegalArgumentException("Cannot update Course: invalid course id");
        }

        Course mergedCourse = null;
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            mergedCourse = entityManager.merge(course);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            throw exception;
        } finally {
            entityManager.close();
        }

        return mergedCourse;
    }


    public Optional<Course> findById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Cannot find Course: invalid course id");
        }

        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            return Optional.ofNullable(entityManager.find(Course.class, id));
        }
    }

    public List<Course> findAll() {
        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(
                "SELECT s FROM Course s",
                Course.class
            ).getResultList();
        }
    }

    public void deleteById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Cannot delete Course: invalid course id");
        }

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            Course course = entityManager.find(Course.class, id);

            if (course == null) {
                throw new IllegalArgumentException(
                    String.format("Cannot delete Course: no course found with id=%d", id)
                );
            }

            entityManager.remove(course);
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