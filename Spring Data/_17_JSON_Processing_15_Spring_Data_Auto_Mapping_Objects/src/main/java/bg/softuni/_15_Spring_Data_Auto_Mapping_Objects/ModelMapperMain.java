package bg.softuni._15_Spring_Data_Auto_Mapping_Objects;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Address;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Employee;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.EmployeeDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;

//@Component TODO: Спряно е за момента, да не се стартира.
public class ModelMapperMain implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        _ex_01();
    }
    private void _ex_01() {
        ModelMapper modelMapper = new ModelMapper();
//        PropertyMap<Employee, EmployeeDTO> propertyMap = new PropertyMap<>() {
//            @Override
//            protected void configure() {
//                map().setCity(source.getAddress().getCity());
//            }
//        };
//        modelMapper.addMappings(propertyMap);
        TypeMap<Employee, EmployeeDTO> typeMap = modelMapper.createTypeMap(Employee.class, EmployeeDTO.class);
//        typeMap.validate();
        typeMap.addMappings(mapping -> mapping.map(
                source -> source.getAddress().getCity(), EmployeeDTO::setAddressCity));
        typeMap.validate();
        Address address = new Address("Bulgaria", "Sofia");
        Employee employee = new Employee("First", BigDecimal.TEN, address);

        EmployeeDTO employeeDTO = typeMap.map(employee);

        System.out.println(employeeDTO);
    }
}
