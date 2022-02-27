package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckWhenStudentEmailExists() {
        String email = "josuelubaki@gmail.com";
        // given
        Student student = new Student(
                "Josue",
                email,
                Gender.MALE
        );
        underTest.save(student);

        // when
        boolean excepted = underTest.selectExistsEmail(student.getEmail());

        // then
        assertThat(excepted).isTrue();
    }

    @Test
    void itShouldCheckWhenStudentEmailDoesNotExists() {
        // given
        String email = "josuelubaki@gmail.com";

        // when
        boolean excepted = underTest.selectExistsEmail(email);

        // then
        assertThat(excepted).isFalse();
    }
}