package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.addresses.AddressDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.addresses.CreateAddressDTO;

public interface AddressService {

    //    Address create(AddressDTO data);
    AddressDTO create(CreateAddressDTO data);
}
