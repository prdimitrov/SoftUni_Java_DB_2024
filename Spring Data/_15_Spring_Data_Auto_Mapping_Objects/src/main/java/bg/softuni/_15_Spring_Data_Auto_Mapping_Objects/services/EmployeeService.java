package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Employee;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.CreateEmployeeDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.EmployeeDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.EmployeeNameAndSalaryDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.EmployeeNamesDTO;

import java.util.List;

public interface EmployeeService {
    Employee create(CreateEmployeeDTO employeeDTO);

    List<EmployeeDTO> findAll();

    EmployeeNamesDTO findNamesById(long id);

    EmployeeNameAndSalaryDTO findNameAndSalaryById(long id);
}
