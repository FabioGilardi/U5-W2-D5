package FabioGilardi.U5W2D5.services;

import FabioGilardi.U5W2D5.entities.Employee;
import FabioGilardi.U5W2D5.exceptions.BadRequestException;
import FabioGilardi.U5W2D5.exceptions.NotFoundException;
import FabioGilardi.U5W2D5.payloads.EmployeeDTO;
import FabioGilardi.U5W2D5.repositories.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    public Page<Employee> findAll(int number, int size, String sortBY) {
        Pageable pageable = PageRequest.of(number, size, Sort.by(sortBY));
        return employeeDAO.findAll(pageable);
    }

    public Employee save(EmployeeDTO payload) {
        if (!employeeDAO.existsByEmail(payload.email()) && !employeeDAO.existsByUsername(payload.username())) {
            Employee newEmployee = new Employee(payload.username(), payload.name(), payload.surname(), payload.email());
            return employeeDAO.save(newEmployee);
        } else throw new BadRequestException("email/username has been already taken");
    }

    public Employee findById(long id) {
        return employeeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Employee findByIdAndUpdate(long id, EmployeeDTO payload) {
        if (!employeeDAO.existsByEmail(payload.email()) && !employeeDAO.existsByUsername(payload.username())) {
            Employee found = this.findById(id);
            found.setUsername(payload.username());
            found.setName(payload.name());
            found.setSurname(payload.surname());
            found.setEmail(payload.email());
            found.setDeafaultAvatar();
            employeeDAO.save(found);
            return found;
        } else {
            throw new BadRequestException("email/username has been already taken");
        }
    }

    public void findByIdAndDelete(long id) {
        Employee found = this.findById(id);
        employeeDAO.delete(found);
    }
}
