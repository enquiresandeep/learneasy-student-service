package com.learneasy.user.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learneasy.user.domain.Address;
import com.learneasy.user.domain.Student;
import com.learneasy.user.infrastructure.dto.AddressDTO;
import com.learneasy.user.infrastructure.dto.StudentDTO;

import java.util.List;

public interface IStudentService {
    public Student createStudent(StudentDTO student) throws Exception;

    public Student findStudentByStudentId(String studentId);

    public Student updateStudent(StudentDTO updatedStudent) throws JsonMappingException ;
    public List<Student> findAll();

    public Address createAddress(AddressDTO address) throws JsonMappingException;

    public List<Address> findAddressesByStudentId(String studentId) ;

    public Address updateAddress( AddressDTO updatedAddress) throws JsonMappingException ;
}
