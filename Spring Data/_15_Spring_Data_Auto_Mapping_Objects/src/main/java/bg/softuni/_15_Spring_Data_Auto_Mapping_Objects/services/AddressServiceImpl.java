package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Address;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.AddressDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service - Вече не го използваме, защото създаваме сървиза от Config!
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper mapper;
//    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper mapper) {
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }
    @Override
    public Address create(AddressDTO data) {
        ModelMapper mapper = new ModelMapper();

        Address address = mapper.map(data, Address.class);

        return this.addressRepository.save(address);
    }
}
