package com.learneasy.user.usermgmtservice;

import com.learneasy.user.infrastructure.dto.StudentDTO;
import com.learneasy.user.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private StudentService studentService;

	@Test
	public void testSaveStudentSuccess() throws Exception {
		// Prepare input
		StudentDTO input = new StudentDTO();
		input.setFirstName("John");
		input.setLastName("Doe");
		// Prepare expected output
		StudentDTO expectedOutput = new StudentDTO();
		expectedOutput.setStudentId("1000000");
		expectedOutput.setFirstName("John");
		expectedOutput.setLastName("Doe");
		// Mock the service layer
		when(studentService.createStudent(input)).thenReturn(expectedOutput);
		// Call the controller
		ResponseEntity<StudentDTO> responseEntity = restTemplate.postForEntity("/student/", input, StudentDTO.class);
		// Assert the response
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody().getStudentId()).isEqualTo(expectedOutput.getStudentId());
		assertThat(responseEntity.getBody().getFirstName()).isEqualTo(expectedOutput.getFirstName());
		assertThat(responseEntity.getBody().getLastName()).isEqualTo(expectedOutput.getLastName());
	}

	@Test
	public void testSaveStudentError() throws Exception {
		// Prepare input
		StudentDTO input = new StudentDTO();
		input.setFirstName("John");
		input.setLastName("Doe");
		// Mock the service layer to throw an exception
		when(studentService.createStudent(input)).thenThrow(new RuntimeException());
		// Call the controller
		ResponseEntity<StudentDTO> responseEntity = restTemplate.postForEntity("/student/", input, StudentDTO.class);
		// Assert the response
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(responseEntity.getBody().getErrorMessage()).isEqualTo("Server Error");
	}
}

