package bg.softuni._15_Spring_Data_Auto_Mapping_Objects;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.addresses.CreateAddressDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.CompanyDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.CreateEmployeeDTO;
import com.google.gson.*;
import org.springframework.boot.CommandLineRunner;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

//@Component
public class JsonTestMain implements CommandLineRunner {
    private final Scanner scanner;
    private final Gson gson;

    static class LocalDateAdapter implements JsonSerializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }

    public JsonTestMain(Scanner scanner, Gson gson) {
        this.scanner = scanner;
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("YYYY-MM-DD")
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    @Override
    public void run(String... args) throws Exception {
//        test_01();
        CreateAddressDTO address1 = new CreateAddressDTO("Bulgaria", "Sofia");
        CreateEmployeeDTO employee1 = new CreateEmployeeDTO("Edin", "Gospodin",
                BigDecimal.ONE, LocalDate.now(), address1);
        CreateAddressDTO address2 = new CreateAddressDTO("Romaina", "Bucuresti");
        CreateEmployeeDTO employee2 = new CreateEmployeeDTO("Vtori", "Gospodin",
                BigDecimal.ONE, LocalDate.now(), address2);
        CreateAddressDTO address3 = new CreateAddressDTO("Bulgaria", "Plovdiv");
        CreateEmployeeDTO employee3 = new CreateEmployeeDTO("Treti", "Gospodin",
                BigDecimal.ONE, LocalDate.now(), address3);

        CompanyDTO mega = new CompanyDTO("Mega", Set.of(employee1, employee2, employee3));

        System.out.println(this.gson.toJson(mega));

        String input = scanner.nextLine();
        CompanyDTO parsed = this.gson.fromJson(input, CompanyDTO.class);

        System.out.println();
    }

    private void test_01() {
//        Gson gson = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                //Ако го използваме това, няма да показва полетата без анотация @Expose
//                //Иначе, ако няма да използваме .excludeFieldsWithoutExposeAnnotation(), няма смисъл и да използваме @Expose
////                .setPrettyPrinting()
//                .setDateFormat("YYYY-MM-DD")
//                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
//                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> null)
//                .create();
        CreateAddressDTO address1 = new CreateAddressDTO("Bulgaria", "Sofia");
        CreateAddressDTO address2 = new CreateAddressDTO("Romaina", "Bucuresti");
        CreateAddressDTO address3 = new CreateAddressDTO("Bulgaria", "Plovdiv");

        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO("Edin", "Gospodin",
                BigDecimal.ONE, LocalDate.now(), address1);

        String json = gson.toJson(createEmployeeDTO);

        System.out.println(json);

        List<CreateAddressDTO> addressList = List.of(address1, address2, address3);

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
