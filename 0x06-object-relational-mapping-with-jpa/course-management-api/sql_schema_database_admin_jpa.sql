CREATE TABLE address (
    id integer,
    data varchar(255),
    studentId bigint,
    primary key (id)
);

CREATE TABLE course (
    id integer,
    data varchar(255),
    teacherId bigint,
    primary key (id)
);

CREATE TABLE course_material (
    id integer,
    data varchar(255),
    courseId bigint unique,
    primary key (id)
);

CREATE TABLE course_student (
    courseId bigint not null,
    studentId bigint not null,
    primary key (courseId, studentId)
);

CREATE TABLE phone (
    id integer,
    data varchar(255),
    studentId bigint,
    primary key (id)
);

CREATE TABLE student (
    id integer,
    data varchar(255),
    primary key (id)
);

CREATE TABLE teacher (
    id integer,
    data varchar(255),
    primary key (id)
);