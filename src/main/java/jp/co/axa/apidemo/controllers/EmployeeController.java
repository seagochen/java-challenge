package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    @Cacheable("employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

    @PostMapping("/employees")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody Employee employee, BindingResult result){

        // check for errors
        ResponseEntity<?> errors = getResponseEntity(result);
        if (errors != null) return errors;
        
        // save employee
        employeeService.saveEmployee(employee);
        return ResponseEntity.ok("Employee Saved Successfully");
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(name="employeeId")Long employeeId){

        // check if employee exists
        Employee emp = employeeService.getEmployee(employeeId);

        // if employee exists, delete it
        if(emp != null){
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.ok("Employee Deleted Successfully");
        }

        // if employee does not exist, return the message
        return ResponseEntity.badRequest().body("Employee Not Found");
    }


    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody Employee employee,
                                            @PathVariable(name="employeeId")Long employeeId, BindingResult result){
        
        // check for errors
        ResponseEntity<?> errors = getResponseEntity(result);
        if (errors != null) return errors;

        // check if employee exists
        Employee emp = employeeService.getEmployee(employeeId);
        
        // if employee exists, update it
        if(emp != null){
            employeeService.updateEmployee(employee);
            return ResponseEntity.ok("Employee Updated Successfully");
        }

        // if employee does not exist, return the message
        return ResponseEntity.badRequest().body("Employee Not Found");
    }

    private ResponseEntity<?> getResponseEntity(BindingResult result) {

        // if there are errors
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            // return the errors
            return ResponseEntity.badRequest().body(errors);
        }

        // if there are no errors, return null
        return null;
    }
}
