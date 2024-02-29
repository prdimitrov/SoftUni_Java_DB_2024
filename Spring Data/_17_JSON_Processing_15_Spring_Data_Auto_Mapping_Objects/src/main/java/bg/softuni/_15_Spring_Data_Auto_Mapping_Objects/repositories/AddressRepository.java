package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.repositories;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    //Optional - може и да имаме адрес, може и да нямаме адрес. :D
    Optional<Address> findByCountryAndCity(String country, String city);
}
