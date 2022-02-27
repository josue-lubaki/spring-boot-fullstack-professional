package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
        // when
        underTest.getAllStudents();

        // then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        // given
        Student student = new Student(
                "Josue",
                "josuelubaki@mail.com",
                Gender.MALE
        );

        // when
        underTest.addStudent(student);

        // capture Objet
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        // verify
        verify(studentRepository).save(studentArgumentCaptor.capture());

        // get captor value
        Student studentCaptured = studentArgumentCaptor.getValue();

        // assertThat
        assertThat(studentCaptured).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        Student student = new Student(
                "Josue",
                "josuelubaki@mail.com",
                Gender.MALE
        );
        given(studentRepository.selectExistsEmail(anyString())).willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");

        // never save any student
        verify(studentRepository, never()).save(any());
    }

    @Test
    void canDeleteStudent() {
        // given
        long studentId = 1;
        given(studentRepository.existsById(studentId)).willReturn(true);

        // when
        underTest.deleteStudent(studentId);

        // then
        verify(studentRepository).deleteById(studentId);
    }

    @Test
    void willThrowWhenDeleteStudentNoFound() {
        // given
        long studentId = 1;
        given(studentRepository.existsById(studentId)).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> underTest.deleteStudent(studentId))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + studentId + " does not exists");

        // never delete any student
        verify(studentRepository, never()).deleteById(any());
    }
}