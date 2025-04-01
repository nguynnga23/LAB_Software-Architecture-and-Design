package com.example.clients;

import com.example.dtos.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "student")
public interface StudentClient {

    @GetMapping("/school/{school-id}")
    List<StudentDTO> findAllStudentsBySchool(@PathVariable("school-id") Integer schoolId);
}