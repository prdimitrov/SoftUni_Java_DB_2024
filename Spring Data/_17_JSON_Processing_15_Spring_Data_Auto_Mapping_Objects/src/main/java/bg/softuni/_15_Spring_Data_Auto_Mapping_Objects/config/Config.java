package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.config;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.repositories.AddressRepository;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services.AddressService;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services.AddressServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class Config {
    //ТОВА ГО НАПРАВИХМЕ ЗА ДА НЕ СЕ СЪЗДАВА ВЪВ ВСЕКИ SERVICE
    //ПОСТОЯННО Mapper
    @Bean
    public ModelMapper createModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Scanner createScanner() {
        return new Scanner(System.in);
    }

    @Bean
    public AddressService createAddressService(AddressRepository repo, ModelMapper mapper) {
        return new AddressServiceImpl(repo, mapper);
    }

    @Bean
    public Gson createGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
