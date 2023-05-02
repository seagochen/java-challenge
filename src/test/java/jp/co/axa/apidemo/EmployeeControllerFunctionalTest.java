package jp.co.axa.apidemo;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee createSampleEmployee(String name, int salary, String department) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSalary(salary);
        employee.setDepartment(department);
        return employee;
    }

    @Test
    public void testGeneralApis() {

        // Get all employees
        ResponseEntity<List> response = restTemplate.getForEntity("/api/v1/employees", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isGreaterThanOrEqualTo(0);

        // Insert an employee
        Employee employee = createSampleEmployee("Test Employee", 10000, "Test Department");
        ResponseEntity<String> postResponse = restTemplate.postForEntity("/api/v1/employees", employee, String.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Get the employee
        ResponseEntity<Employee> getResponse = restTemplate.getForEntity("/api/v1/employees/1", Employee.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Update the employee
        employee.setName("Updated Test Employee");
        restTemplate.put("/api/v1/employees/1", employee);

        // Get the updated employee
        ResponseEntity<Employee> getUpdatedResponse = restTemplate.getForEntity("/api/v1/employees/1", Employee.class);
        assertThat(getUpdatedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testInsertAndDeleteManyEmployees() {
        // Insert 10,000 employees
        for (int i = 0; i < 10000; i++) {
            Employee employee = createSampleEmployee("Employee " + i, 50000 + i, "Department " + i % 10);
            restTemplate.postForEntity("/api/v1/employees", employee, String.class);
        }

        // Test if 10,000 employees have been added
        ResponseEntity<List> getResponse = restTemplate.getForEntity("/api/v1/employees", List.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().size()).isGreaterThanOrEqualTo(10000);

        // Delete all employees
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            restTemplate.delete("/api/v1/employees/" + employee.getId());
        }

        // Test if all employees have been deleted
        ResponseEntity<List> getDeletedResponse = restTemplate.getForEntity("/api/v1/employees", List.class);
        assertThat(getDeletedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getDeletedResponse.getBody().size()).isEqualTo(0);
    }
}
