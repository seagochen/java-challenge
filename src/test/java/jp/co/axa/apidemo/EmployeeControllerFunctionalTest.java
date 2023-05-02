package jp.co.axa.apidemo;

import jp.co.axa.apidemo.entities.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerFunctionalTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSaveEmployee1() {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setSalary(50000);
        employee.setDepartment("IT");

        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/employees", employee, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Employee Saved Successfully");
    }

    @Test
    public void testSaveEmployee2() {
        Employee employee = new Employee();
        employee.setName("Alice Smith");
        employee.setSalary(50000);
        employee.setDepartment("Sales");

        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/employees", employee, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Employee Saved Successfully");
    }

    @Test
    public void testGetEmployees() {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/v1/employees", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isGreaterThanOrEqualTo(0);
    }


}
