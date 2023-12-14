package hu.bankblaze.bankblaze.repo;

import hu.bankblaze.bankblaze.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(nativeQuery = true, value="SELECT * FROM employee WHERE role='USER'")
    List<Employee> getAllActiveClerks();

    @Query(nativeQuery = true, value="SELECT * FROM employee WHERE role='USER' OR role='inactive'")
    List<Employee> getAllClerks();

    @Query(nativeQuery = true, value="SELECT * FROM employee WHERE role='ADMIN'")
    List<Employee> getAllAdmins();

    Optional<Employee> findByName(String name);
    Employee getAdminByName(String userName);
    void deleteById(Long id);

}
