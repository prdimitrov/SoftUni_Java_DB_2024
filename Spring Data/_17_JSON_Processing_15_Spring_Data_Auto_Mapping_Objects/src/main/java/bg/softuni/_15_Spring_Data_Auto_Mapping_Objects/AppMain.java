package bg.softuni._15_Spring_Data_Auto_Mapping_Objects;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Employee;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.addresses.AddressDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.addresses.CreateAddressDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.CreateEmployeeDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.EmployeeNameAndSalaryDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services.AddressService;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services.EmployeeService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class AppMain implements CommandLineRunner {

    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final Scanner scanner;

    private final Gson gson;

    @Autowired
    public AppMain(AddressService addressService, EmployeeService employeeService, Scanner scanner, Gson gson) {
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.scanner = scanner;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
//        Scanner scanner = new Scanner(System.in); //Задаваме го в конструктора, а го създаваме в Config!!!!!

        createAddress();
        createEmployee();
//        printAllEmployees();
//        printEmployeeNames();
//        printEmployeeNameAndSalary();

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

    private void createEmployee() {
        String firstName = scanner.nextLine();
        BigDecimal salary = new BigDecimal(scanner.nextLine());
        LocalDate birthday = LocalDate.parse(scanner.nextLine());

//        long addressId = Long.parseLong(sc.nextLine());

        String country = scanner.nextLine();
        String city = scanner.nextLine();

        CreateAddressDTO address = new CreateAddressDTO(country, city);

        CreateEmployeeDTO employeeDTO =
                new CreateEmployeeDTO(firstName, null, salary, birthday, address);

        Employee employee = this.employeeService.create(employeeDTO);
        System.out.println(employee);
    }

    private void createAddress() {
//        String country = scanner.nextLine();
//        String city = scanner.nextLine();
//        AddressDTO data = new AddressDTO(country, city);
        String input = scanner.nextLine();
        CreateAddressDTO data = this.gson.fromJson(input, CreateAddressDTO.class);

//        Address address = addressService.create(data);
        AddressDTO created = addressService.create(data);
        System.out.println(this.gson.toJson(created));
    }
}
