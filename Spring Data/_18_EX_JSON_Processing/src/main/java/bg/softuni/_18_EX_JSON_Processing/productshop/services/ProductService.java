package bg.softuni._18_EX_JSON_Processing.productshop.services;

import bg.softuni._18_EX_JSON_Processing.productshop.entities.categories.CategoryStats;
import bg.softuni._18_EX_JSON_Processing.productshop.entities.products.ProductWithoutBuyerDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductWithoutBuyerDTO> getProductsInPriceRangeForSell(
            float from, float to);

    List<CategoryStats> getCategoryStatistics();

}
