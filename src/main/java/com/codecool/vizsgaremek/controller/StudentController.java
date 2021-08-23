package com.codecool.vizsgaremek.controller;

import com.codecool.vizsgaremek.modell.Student;
import com.codecool.vizsgaremek.service.StudentService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/student")
@RestController
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("getAll")
    public List<Student> getAllStudent() {
        return studentService.getAll();
    }

    @PostMapping("add")
    public Student save(@Valid @RequestBody Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult
                    .getAllErrors()
                    .forEach(errorObj -> System.out.println(errorObj.getDefaultMessage()));
        }
        return studentService.save(student);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/class/{id}")
    public List<Student> getStudentsByClass(@PathVariable Long id) {
        return studentService.getStudentsByClassId(id);
    }

    @GetMapping("/gender/{gender}")
    public List<Student> getStudentsByGender(@PathVariable String gender) {
        return studentService.getStudentsByGender(gender);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id,@Valid @RequestBody Student student,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult
                    .getAllErrors()
                    .forEach(errorObj -> System.out.println(errorObj.getDefaultMessage()));
        }
        studentService.updateStudent(id, student);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }
}
