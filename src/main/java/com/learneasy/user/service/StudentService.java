package com.learneasy.user.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learneasy.user.domain.ADDRESS_TYPE;
import com.learneasy.user.domain.Address;
import com.learneasy.user.domain.Student;
import com.learneasy.user.infrastructure.AddressRepository;
import com.learneasy.user.infrastructure.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
     private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        log.info("StudentService saveStudent "+student.getFirstName());
        return studentRepository.save(student);
    }

    public Student findStudentByStudentId(String studentId){
        log.info("StudentService findStudentBuId "+studentId);
        return studentRepository.findById(studentId).get();
    }

    public Student updateStudent(Student updatedStudent) throws JsonMappingException {
        Student student = studentRepository.findById(updatedStudent.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id " + updatedStudent.getStudentId()));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        student = objectMapper.updateValue(student, updatedStudent);

        student = studentRepository.save(student);

        return student;
    }

    public List<Student> findAll(){
        return studentRepository.findAll();
    }

    public Address createAddress(Address address) {
        log.info("StudentService createAddress "+address.getStreet());
        String studentId = address.getStudentId();
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + studentId));
        address.setStudentId(address.getStudentId());

        address = addressRepository.save(address);
        return address;
    }

    public List<Address> findAddressesByStudentId(String studentId) {
        return addressRepository.findByStudentId(studentId);
    }

    public Address updateAddress( Address updatedAddress) throws JsonMappingException {
        Address address = addressRepository.findById(updatedAddress.getId())
                .orElseThrow(() -> new RuntimeException("Student not found with id " + updatedAddress.getId()));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        address = objectMapper.updateValue(address, updatedAddress);

        address = addressRepository.save(address);

        return address;
    }


}
