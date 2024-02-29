package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.services;

import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.Address;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.addresses.AddressDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos.addresses.CreateAddressDTO;
import bg.softuni._15_Spring_Data_Auto_Mapping_Objects.repositories.AddressRepository;
import org.modelmapper.ModelMapper;

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
    public AddressDTO create(CreateAddressDTO data) {
//        ModelMapper mapper = new ModelMapper();
        Address address = mapper.map(data, Address.class);
        Address saved = this.addressRepository.save(address);

        return this.mapper.map(saved, AddressDTO.class);
    }
}
