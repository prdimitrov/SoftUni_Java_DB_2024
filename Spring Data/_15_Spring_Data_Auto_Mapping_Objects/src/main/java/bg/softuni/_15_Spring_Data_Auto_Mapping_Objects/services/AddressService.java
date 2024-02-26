package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Address;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.CreateAddressDTO;

public interface AddressService {

    Address create(CreateAddressDTO data);
}
