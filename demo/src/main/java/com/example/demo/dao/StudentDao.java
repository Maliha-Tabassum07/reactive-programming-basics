package com.example.demo.dao;

import com.example.demo.model.Student;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class StudentDao {
    private List<Student> students = new ArrayList<>();
    private Flux<Student> studentFlux = Flux.empty();


    public List<Student> getStudents(){
        return students;
    }

    public Flux<Student> getStudentFlux(){
        return studentFlux;
    }

    public Student addStudent(String name, String email){
        UUID id = UUID.randomUUID();
        Student student = new Student(id, name, email);
        students.add(student);
        return student;
    }

    public Mono<Student> addStudentMono(String name, String email){
        UUID id = UUID.randomUUID();
        Student student = new Student(id, name, email);
        studentFlux = studentFlux.concatWithValues(student);
        return Mono.just(student);
    }
//    public Flux<?> deleteStudent(UUID studentIdToDelete) {
//        studentFlux = studentFlux.filter(student -> !student.getId().equals(studentIdToDelete));
//        return true;
//    }

    public Mono<Boolean> deleteStudent(UUID studentIdToDelete) {
        return studentFlux
                .any(student -> student.getId().equals(studentIdToDelete))
                .flatMap(found -> {
                    if (found) {
                        studentFlux = studentFlux.filter(student -> !student.getId().equals(studentIdToDelete));
                    }
                    return Mono.just(found);
                });
    }


    public Mono<Boolean> updateStudent(Student updatedStudent) {
        UUID updatedStudentId = updatedStudent.getId();
        return studentFlux
                .any(student -> student.getId().equals(updatedStudentId))
                .flatMap(found -> {
                    if (found) {
                        studentFlux = studentFlux.map(student -> {
                            if (student.getId().equals(updatedStudentId)) {
                                return updatedStudent;
                            }
                            return student;
                        });
                    return Mono.just(true);
                } else {
            return Mono.just(false);
        }
    });
    }
}


