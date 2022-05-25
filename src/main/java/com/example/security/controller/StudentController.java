package com.example.security.controller;

import com.example.security.entity.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/student")
public class StudentController {

    private static final List<Student> students = Arrays.asList(
            new Student(1, "TAHSIN"),
            new Student(2, "AMIT"),
            new Student(3, "MAHFUZ")
    );

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Student> getStudents() {
        return students;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('student:write', 'student:read')")
    public void createStudent(@RequestBody Student student) {
        System.out.printf("Create Student %s", student);
    }

    @PutMapping("{student-id}")
    public void updateStudent(@PathVariable("student-id") Integer studentId, @RequestBody Student student) {
        System.out.printf("Update Student %s", student);
    }

    @DeleteMapping("{student-id}")
    public void deleteStuent(@PathVariable("student-id") Integer studentId) {
        System.out.printf("Delete Student %s", studentId);
    }

    @GetMapping("{student-id}")
    public Student get(@Validated @PathVariable("student-id") Integer studentId) {
        return students.stream().filter(s -> s.getId().equals(studentId)).findFirst()
                .orElseThrow(() -> new IllegalStateException("STUDENT NOT FOUND"));
    }
}
