package bg.softuni._18_EX_JSON_Processing.productshop.services;

import bg.softuni._18_EX_JSON_Processing.productshop.entities.products.Product;
import bg.softuni._18_EX_JSON_Processing.productshop.entities.categories.Category;
import bg.softuni._18_EX_JSON_Processing.productshop.entities.categories.CategoryImportDTO;
import bg.softuni._18_EX_JSON_Processing.productshop.entities.products.ProductImportDTO;
import bg.softuni._18_EX_JSON_Processing.productshop.entities.users.User;
import bg.softuni._18_EX_JSON_Processing.productshop.entities.users.UserImportDTO;
import bg.softuni._18_EX_JSON_Processing.productshop.repositories.CategoryRepository;
import bg.softuni._18_EX_JSON_Processing.productshop.repositories.ProductRepository;
import bg.softuni._18_EX_JSON_Processing.productshop.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {

    private static final String USERS_JSON_PATH =
            "src/main/resources/productshop/users.json";
    private static final Path CATEGORIES_JSON_PATH =
            Path.of("src", "main", "resources", "productshop", "categories.json");
    private static final Path PRODUCTS_JSON_PATH =
            Path.of("src", "main", "resources", "productshop", "products.json");


    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ProductRepository productRepository;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository,
                           CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void seedCategories() throws FileNotFoundException {
        FileReader fileReader = new FileReader(CATEGORIES_JSON_PATH.toAbsolutePath().toString());
        CategoryImportDTO[] categoryImportDTOS = this.gson.fromJson(fileReader, CategoryImportDTO[].class);

        List<Category> categories = Arrays.stream(categoryImportDTOS)
                .map(importDTO -> this.modelMapper.map(importDTO, Category.class))
                .collect(Collectors.toList());


   this.categoryRepository.saveAll(categories);
    }

    @Override
    public void seedUsers() throws FileNotFoundException {
        FileReader fileReader = new FileReader(USERS_JSON_PATH);
        UserImportDTO[] userImportDTOS = this.gson.fromJson(fileReader, UserImportDTO[].class);

        List<User> users = Arrays.stream(userImportDTOS)
                .map(importDTO -> this.modelMapper.map(importDTO, User.class))
                .collect(Collectors.toList());


        this.userRepository.saveAll(users);
    }

    @Override
    public void seedProducts() throws FileNotFoundException {
        FileReader fileReader = new FileReader(PRODUCTS_JSON_PATH.toAbsolutePath().toString());
        ProductImportDTO[] productImportDTOS = this.gson.fromJson(fileReader, ProductImportDTO[].class);

        List<Product> products = Arrays.stream(productImportDTOS)
                .map(importDTO -> this.modelMapper.map(importDTO, Product.class))
                .map(this::setRandomSeller)
                .map(this::setRandomBuyer)
                .map(this::sendRandomCategories)
                .collect(Collectors.toList());


        this.productRepository.saveAll(products);
    }

    private Product sendRandomCategories(Product product) {
        Random random = new Random();
        long categoriesDbCount = this.categoryRepository.count();

        int count = random.nextInt((int) categoriesDbCount);

        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < count; i++) {
            long randomId = random.nextInt((int) categoriesDbCount) + 1;

            Optional<Category> randomCategory = this.categoryRepository.findById(randomId);

            // Check if the Optional contains a value before calling .get()
            randomCategory.ifPresent(categories::add);
        }

        product.setCategories(categories);

        return product;
    }

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(944)) > 0) {
            return product;
        }

        User defaultUser = new User(); // Provide a default User if Optional is empty
        Optional<User> buyer = getRandomUser();

        // Save the buyer user first if it's not already persisted
        if (buyer.isPresent()) {
            userRepository.save(buyer.get());
        } else {
            // Save the default user
            userRepository.save(defaultUser);
            buyer = Optional.of(defaultUser);
        }

        product.setBuyer(buyer.get());

        return product;
    }

    private Product setRandomSeller(Product product) {
        Optional<User> seller = getRandomUser();

        // Use .ifPresent() to safely set the seller
        seller.ifPresent(product::setSeller);

        return product;
    }
    private Optional<User> getRandomUser() {
        long usersCount = this.userRepository.count(); //1..5
        //random - 0..4
        long randomUserId = new Random().nextInt((int) usersCount + 1);

        /*
        Optional<User> seller = this.userRepository.findById(randomUserId);
        return seller;
         */
        return this.userRepository.findById(randomUserId);
    }
}
