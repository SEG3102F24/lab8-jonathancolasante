package seg3x02.employeeGql.resolvers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.*

@Controller
class EmployeesResolver(@Autowired private val employeeRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> {
        return employeeRepository.findAll()
    }

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? {
        return employeeRepository.findById(id).orElse(null)
    }

    @MutationMapping
    fun newEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        val name = createEmployeeInput.name ?: throw IllegalArgumentException("Name is required")
        val dateOfBirth = createEmployeeInput.dateOfBirth ?: throw IllegalArgumentException("Date of Birth is required")
        val city = createEmployeeInput.city ?: throw IllegalArgumentException("City is required")
        val salary = createEmployeeInput.salary ?: throw IllegalArgumentException("Salary is required")
        val gender = createEmployeeInput.gender
        val email = createEmployeeInput.email
    
        val employee = Employee(
            name = name,
            dateOfBirth = dateOfBirth,
            city = city,
            salary = salary,
            gender = gender,
            email = email
        )
        employee.id = UUID.randomUUID().toString()
        
        return employeeRepository.save(employee)
    }
    
    @MutationMapping
    fun deleteEmployee(@Argument id: String): Boolean {
        return try {
            employeeRepository.deleteById(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}
