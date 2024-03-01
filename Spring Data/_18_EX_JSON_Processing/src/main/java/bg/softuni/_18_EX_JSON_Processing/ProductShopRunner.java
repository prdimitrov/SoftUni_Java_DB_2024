package bg.softuni._18_EX_JSON_Processing;

import bg.softuni._18_EX_JSON_Processing.productshop.entities.categories.CategoryStats;
import bg.softuni._18_EX_JSON_Processing.productshop.entities.products.ProductWithoutBuyerDTO;
import bg.softuni._18_EX_JSON_Processing.productshop.entities.users.UserWithSoldProductsDTO;
import bg.softuni._18_EX_JSON_Processing.productshop.services.ProductService;
import bg.softuni._18_EX_JSON_Processing.productshop.services.SeedService;
import bg.softuni._18_EX_JSON_Processing.productshop.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductShopRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;

    private final Gson gson;
    private UserService userService;

    @Autowired
    public ProductShopRunner(SeedService seedService, ProductService productService, UserService userService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedService.seedUsers();
//        this.seedService.seedCategories();
//        this.seedService.seedProducts();
//        this.seedService.seedAll();
//        query_1_Products_in_Range();
//        query_2_Successfully_Sold_Products();
//        query_3_Categories_by_Products_Count();

        //TODO: NOT WORKING!
        query_4_Users_and_Products();
    }

    private void query_1_Products_in_Range() {
        List<ProductWithoutBuyerDTO> productsForSale = this.productService.getProductsInPriceRangeForSell(500, 1000);
        String json = this.gson.toJson(productsForSale);

        System.out.println(json);
    }

    private void query_2_Successfully_Sold_Products() {
        List<UserWithSoldProductsDTO> usersWithSoldProducts = this.userService.getUsersWithSoldProducts();

        String json = this.gson.toJson(usersWithSoldProducts);

        System.out.println(json);
    }

    private void query_3_Categories_by_Products_Count() {
        List<CategoryStats> categoryStatistics = this.productService.getCategoryStatistics();

        String json = this.gson.toJson(categoryStatistics);

        System.out.println(json);
    }

    //TODO: Must be fixed!
    private void query_4_Users_and_Products() {
        this.userService.getUsersWithSoldProductsOrderByCount();
    }
}
