package FabioGilardi.U5W2D5.repositories;

import FabioGilardi.U5W2D5.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Long> {
}
