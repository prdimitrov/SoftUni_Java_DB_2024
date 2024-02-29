package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.repositories;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Employee;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.EmployeeNameAndSalaryDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.EmployeeNamesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT new bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.EmployeeNamesDTO(e.firstName, e.lastName) " +
            "FROM Employee AS e WHERE e.id = :id") //1 начин за проекция!
    EmployeeNamesDTO findNamesById(long id);

    EmployeeNameAndSalaryDTO findFirstNameAndSalaryById(long id);
}
