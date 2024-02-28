package bg.softuni._15_Spring_Data_Auto_Mapping_Objects;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.AddressDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.CreateEmployeeDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class JsonTestMain implements CommandLineRunner {
    private final Scanner scanner;

    public JsonTestMain(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) throws Exception {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                //Ако го използваме това, няма да показва полетата без анотация @Expose
                //Иначе, ако няма да използваме .excludeFieldsWithoutExposeAnnotation(), няма смисъл и да използваме @Expose
//                .setPrettyPrinting()
                .setDateFormat("YYYY-MM-DD")
                .create();
        AddressDTO address1 = new AddressDTO("Bulgaria", "Sofia");
        AddressDTO address2 = new AddressDTO("Romaina", "Bucuresti");
        AddressDTO address3 = new AddressDTO("Bulgaria", "Plovdiv");

        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO("Edin", "Gospodin",
                BigDecimal.ONE, LocalDate.now(), address1);

        String json = gson.toJson(createEmployeeDTO);

        System.out.println(json);

        List<AddressDTO> addressList = List.of(address1, address2, address3);

        System.out.println(gson.toJson(addressList));


        String input = scanner.nextLine(); //махаме .setPrettyPrinting(), за да ни подаде json'а на един ред,
        //иначе няма да сработи скенера.

//        List list = gson.fromJson(input, List.class);
//        AddressDTO[] addressDTOS = gson.fromJson(input, AddressDTO[].class);
        CreateEmployeeDTO parsedDTO = gson.fromJson(input, CreateEmployeeDTO.class);
        System.out.println(); //breakpoint

//        CreateEmployeeDTO parsedDTO = gson.fromJson(input, CreateEmployeeDTO.class);
//        System.out.println(parsedDTO);

    }
}
