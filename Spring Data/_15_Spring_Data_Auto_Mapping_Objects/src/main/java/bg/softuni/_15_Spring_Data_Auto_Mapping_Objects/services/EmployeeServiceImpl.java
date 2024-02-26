package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Address;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Employee;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.CreateEmployeeDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.EmployeeDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.EmployeeNameAndSalaryDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.EmployeeNamesDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.repositories.AddressRepository;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    public EmployeeServiceImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional //иначе ще гръмне.
    public Employee create(CreateEmployeeDTO employeeDTO) {
//        ModelMapper mapper = new ModelMapper(); //има го в конструктора.

        Employee employee = mapper.map(employeeDTO, Employee.class);

        //Дали ще проверяваме от employeeDTO или employee e едно и също.
        //Няма значение.
        Optional<Address> address = this.addressRepository.findByCountryAndCity(
                employee.getAddress().getCountry(),
                employeeDTO.getAddress().getCity());

//        if (address.isPresent()) {
//            employee.setAddress(address.get());
//        }
        address.ifPresent(employee::setAddress);

        return this.employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDTO> findAll() {
//        ModelMapper mapper = new ModelMapper(); //има го в конструктора.
        return this.employeeRepository.findAll()
                .stream()
                .map(e -> mapper.map(e, EmployeeDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public EmployeeNamesDTO findNamesById(long id) {
        return this.employeeRepository.findNamesById(id);
    }
    @Override
    public EmployeeNameAndSalaryDTO findNameAndSalaryById(long id) {
        return this.employeeRepository.findFirstNameAndSalaryById(id);
    }
}
