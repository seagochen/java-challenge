# Employee Management API Documentation

This Employee Management API allows users to create, retrieve, update, and delete employee records. The API is designed using the RESTful principles and returns responses.

## API Endpoints

### 1. Add a new employee

**URL**: /api/v1/employees

**Method**: POST

**Payload**:

* name (String): Employee's name
* salary (Integer): Employee's salary
* department (String): Employee's department

**Example request payload:**

```json
{
  "name": "John Doe",
  "salary": 50000,
  "department": "IT"
}
```

**Example response:**
```json
{
  "message": "Employee Saved Successfully"
}
```

### 2. Get all employees

**URL**: /api/v1/employees

**Method**: GET

**Example response**:

```json
[
  {
    "id": 1,
    "name": "John Doe",
    "salary": 50000,
    "department": "IT"
  },
  {
    "id": 2,
    "name": "Alice Smith",
    "salary": 60000,
    "department": "Sales"
  }
]

```

### 3. Get employee by ID

**URL**: /api/v1/employees/{employeeId}

**Method**: GET

**Path parameter**:

* employeeId (Long): Employee's ID

**Example response**:

```json
{
  "id": 1,
  "name": "John Doe",
  "salary": 50000,
  "department": "IT"
}
```

### 4. Update employee

**URL**: /api/v1/employees/{employeeId}

**Method**: PUT

**Path parameter**:

* employeeId (Long): Employee's ID

**Payload**:

* name (String): Employee's name
* salary (Integer): Employee's salary
* department (String): Employee's department

**Example request payload**:

```json
{
  "name": "John Doe",
  "salary": 55000,
  "department": "IT"
}
```

**Example response**:

```json
{
  "message": "Employee Updated Successfully"
}
```

### 5. Delete employee

**URL**: /api/v1/employees/{employeeId}

**Method**: DELETE

**Path parameter**:

* employeeId (Long): Employee's ID
* Example request:

**Example response**:

```json
{
  "message": "Employee Deleted Successfully"
}
```