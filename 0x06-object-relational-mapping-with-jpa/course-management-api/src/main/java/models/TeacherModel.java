package models;

import entities.Teacher;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.List;

public class TeacherModel {
    private final EntityManagerFactory entityManagerFactory;

    public TeacherModel(EntityManagerFactory entityManagerFactory) {
        if (entityManagerFactory == null) {
            throw new IllegalArgumentException(
                "Cannot create TeacherModel object: entityManagerFactory is null"
            );
        }

        this.entityManagerFactory = entityManagerFactory;
    }

    public void create(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Cannot create Teacher: teacher is null");
        }

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(teacher);
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

    public Teacher update(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Cannot update Teacher: teacher is null");
        }

        if (teacher.getId() == null || teacher.getId() < 1) {
            throw new IllegalArgumentException("Cannot update Teacher: invalid teacher id");
        }

        Teacher mergedTeacher = null;
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            mergedTeacher = entityManager.merge(teacher);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            throw exception;
        } finally {
            entityManager.close();
        }

        return mergedTeacher;
    }


    public Optional<Teacher> findById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Cannot find Teacher: invalid teacher id");
        }

        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            return Optional.ofNullable(entityManager.find(Teacher.class, id));
        }
    }

    public List<Teacher> findAll() {
        try (EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(
                "SELECT s FROM Teacher s",
                Teacher.class
            ).getResultList();
        }
    }

    public void deleteById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Cannot delete Teacher: invalid teacher id");
        }

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            Teacher teacher = entityManager.find(Teacher.class, id);

            if (teacher == null) {
                throw new IllegalArgumentException(
                    String.format("Cannot delete Teacher: no teacher found with id=%d", id)
                );
            }

            entityManager.remove(teacher);
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