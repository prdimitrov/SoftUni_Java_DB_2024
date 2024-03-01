package bg.softuni._18_EX_JSON_Processing.productshop.services;

import bg.softuni._18_EX_JSON_Processing.productshop.entities.users.User;
import bg.softuni._18_EX_JSON_Processing.productshop.entities.users.UserWithSoldProductsDTO;
import bg.softuni._18_EX_JSON_Processing.productshop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository/*, ModelMapper modelMapper*/) {
        this.userRepository = userRepository;

//        this.modelMapper = modelMapper;
        this.modelMapper = new ModelMapper();
    }


    @Override
    @Transactional
    public List<UserWithSoldProductsDTO> getUsersWithSoldProducts() {
        List<User> allWithSoldProducts = this.userRepository.findAllWithSoldProducts();

        return allWithSoldProducts
                .stream()
                .map(user -> this.modelMapper.map(user, UserWithSoldProductsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<User> getUsersWithSoldProductsOrderByCount() {
        return this.userRepository.findAllWithSoldProductsOrderByCount();
    }
}
