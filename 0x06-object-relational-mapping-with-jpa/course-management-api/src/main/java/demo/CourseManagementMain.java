package demo;

import entities.Student;
import entities.Address;
import entities.Phone;
import entities.Teacher;
import entities.Course;
import entities.CourseMaterial;

import models.CourseModel;
import models.StudentModel;
import models.TeacherModel;

import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class CourseManagementMain {
	private static final String PERSISTENCE_UNIT = "course-management-jpa";

	public static void main(String[] args) {
		try (
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
				PERSISTENCE_UNIT
			)
		) {
			CourseModel courseModel = new CourseModel(entityManagerFactory);
			StudentModel studentModel = new StudentModel(entityManagerFactory);
			TeacherModel teacherModel = new TeacherModel(entityManagerFactory);


			Course course = new Course(
				"course-data",
				Set.of(
					new Student(
						"student-data",
						Set.of(
							new Address("address-data"),
							new Address("address-data")
						),
						Set.of(
							new Phone("phone-data"),
							new Phone("phone-data")
						)
					),
					new Student(
						"student-data",
						Set.of(
							new Address("address-data"),
							new Address("address-data")
						),
						Set.of(
							new Phone("phone-data"),
							new Phone("phone-data")
						)
					)
				),
				new CourseMaterial("courseMaterial-data"),
				new Teacher("teacher-data")
			);

			Student student = new Student(
				"student-data",
				Set.of(
					new Course(
						"course-data",
						new CourseMaterial("courseMaterial-data"),
						new Teacher("teacher-data")
					),
					new Course(
						"course-data",
						new CourseMaterial("courseMaterial-data"),
						new Teacher("teacher-data")
					)
				),
				Set.of(new Address("address-data"), new Address("address-data")),
				Set.of(new Phone("phone-data"), new Phone("phone-data"))
			);

			Teacher teacher = new Teacher(
				"teacher-data",
				Set.of(
					new Course(
						"course-data",
						Set.of(
							new Student(
								"student-data",
								Set.of(
									new Address("address-data"),
									new Address("address-data")
								),
								Set.of(
									new Phone("phone-data"),
									new Phone("phone-data")
								)
							),
							new Student(
								"student-data",
								Set.of(
									new Address("address-data"),
									new Address("address-data")
								),
								Set.of(
									new Phone("phone-data"),
									new Phone("phone-data")
								)
							)
						),
						new CourseMaterial("courseMaterial-data")
					)
				)
			);

			courseModel.create(course);
			studentModel.create(student);
			teacherModel.create(teacher);

			course = courseModel.findById(course.getId()).orElseThrow(
				() -> new RuntimeException("course not found")
			);
			student = studentModel.findById(student.getId()).orElseThrow(
				() -> new RuntimeException("student not found")
			);
			teacher = teacherModel.findById(teacher.getId()).orElseThrow(
				() -> new RuntimeException("teacher not found")
			);

			System.out.println();
			System.out.println(course);
			System.out.println(student);
			System.out.println(teacher);

			System.out.println();
			System.out.println(courseModel.findAll());
			System.out.println(studentModel.findAll());
			System.out.println(teacherModel.findAll());

			course.setData("new-course-data");
			student.setData("new-student-data");
			teacher.setData("new-teacher-data");

			course = courseModel.update(course);
			student = studentModel.update(student);
			teacher = teacherModel.update(teacher);

			System.out.println();
			System.out.println(course);
			System.out.println(student);
			System.out.println(teacher);

			courseModel.deleteById(course.getId());
			studentModel.deleteById(student.getId());
			teacherModel.deleteById(teacher.getId());

			System.out.println();
			System.out.println(courseModel.findAll());
			System.out.println(studentModel.findAll());
			System.out.println(teacherModel.findAll());
		}
	}
}