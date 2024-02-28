package bg.softuni._15_Spring_Data_Auto_Mapping_Objects;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Address;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Employee;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.AddressDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.CreateEmployeeDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.EmployeeNameAndSalaryDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services.AddressService;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

//@Component
public class AppMain implements CommandLineRunner {

    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final Scanner scanner;

    @Autowired
    public AppMain(AddressService addressService, EmployeeService employeeService, Scanner scanner) {
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) throws Exception {
//        Scanner scanner = new Scanner(System.in); //Задаваме го в конструктора, а го създаваме в Config!!!!!

//        createAddress(sc);
//        createEmployee(sc);
//        printAllEmployees();
//        printEmployeeNames();
        printEmployeeNameAndSalary();
    }

    private void printEmployeeNameAndSalary() {
        EmployeeNameAndSalaryDTO result = this.employeeService.findNameAndSalaryById(1L);
        System.out.println(result);
    }

    private void printEmployeeNames() {
        System.out.println(this.employeeService.findNamesById(1L));
    }

    private void printAllEmployees() {
        this.employeeService.findAll()
                .forEach(System.out::println);
    }

    private void createEmployee(Scanner scanner) {
        String firstName = scanner.nextLine();
        BigDecimal salary = new BigDecimal(scanner.nextLine());
        LocalDate birthday = LocalDate.parse(scanner.nextLine());

//        long addressId = Long.parseLong(sc.nextLine());

        String country = scanner.nextLine();
        String city = scanner.nextLine();

        AddressDTO address = new AddressDTO(country, city);

        CreateEmployeeDTO employeeDTO =
                new CreateEmployeeDTO(firstName, null, salary, birthday, address);

        Employee employee = this.employeeService.create(employeeDTO);
        System.out.println(employee);
    }

    private void createAddress(Scanner scanner) {

        String country = scanner.nextLine();
        String city = scanner.nextLine();

        AddressDTO data = new AddressDTO(country, city);

        Address address = addressService.create(data);

        System.out.println(address);
    }
}
