CREATE TABLE IF NOT EXISTS students
(
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    furigana VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    email_address VARCHAR(100) NOT NULL,
    address VARCHAR(150) NOT NULL,
    age int NOT NULL,
    gender VARCHAR(10) NOT NULL,
    remark  VARCHAR(250),
    is_deleted boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS students_courses
(
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    student_id int NOT NULL,
    course_name VARCHAR(50) NOT NULL,
    course_start_day DATE NOT NULL,
    course_completion_day DATE NOT NULL
);


CREATE TABLE IF NOT EXISTS application_status
(
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    student_course_id INT NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL
);
