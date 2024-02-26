package bg.softuni._15_Spring_Data_Auto_Mapping_Objects;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Address;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.CreateAddressDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class AppMain implements CommandLineRunner {

    private AddressService addressService;

    @Autowired
    public AppMain(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
//        createAddress(sc);
     createEmployee(sc);
    }

    private void createEmployee(Scanner sc) {
        String firstName = sc.nextLine();
        BigDecimal salary = new BigDecimal(sc.nextLine());
        LocalDate birthday = LocalDate.parse(sc.nextLine());


    }

    private void createAddress(Scanner sc) {

        String country = sc.nextLine();
        String city = sc.nextLine();

        CreateAddressDTO data = new CreateAddressDTO(country, city);

        Address address = addressService.create(data);

        System.out.println(address);
    }
}
