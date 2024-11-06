package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.*

@Controller
class EmployeesResolver(private val employeeRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> {
        return employeeRepository.findAll()
    }

    @MutationMapping
    fun newEmployee(@Argument("createEmployeeInput") input: CreateEmployeeInput): Employee {
        if(input.name != null && input.dateOfBirth != null && input.city != null && input.salary != null) {
            val employee = Employee(
                name = input.name,
                dateOfBirth = input.dateOfBirth,
                city = input.city,
                salary = input.salary,
                gender = input.gender,
                email = input.email
            )
            employee.id = UUID.randomUUID().toString()
            return employeeRepository.save(employee)
        } 
        else {
            throw Exception("Missing required fields")
        }        
    }
}