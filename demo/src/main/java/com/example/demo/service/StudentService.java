package com.example.demo.service;

import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service

public class StudentService {
    private final StudentDao studentDao;
    StudentService (StudentDao studentDao){
        this.studentDao = studentDao;
    }
    public List<Student> students (){
        return studentDao.getStudents();
    }

    public Student addStudent(String name, String email){
        return studentDao.addStudent(name, email);
    }

    public Flux<Student> studentFlux (){
        return studentDao.getStudentFlux();
    }

    public Mono<Student> addStudentMono(String name, String email){
        return studentDao.addStudentMono(name, email);
    }

    public Mono<Boolean> updateStudentMono(Student student){
        return studentDao.updateStudent(student);
    }
    public Mono<Boolean> deleteStudentId(UUID uuid){
        if (uuid.equals(null)){
            return Mono.just(false);
        }
        else{
            return studentDao.deleteStudent(uuid);
        }
    }
}
