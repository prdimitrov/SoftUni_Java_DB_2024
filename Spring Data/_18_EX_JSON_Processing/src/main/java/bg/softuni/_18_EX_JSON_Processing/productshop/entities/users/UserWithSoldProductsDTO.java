package bg.softuni._18_EX_JSON_Processing.productshop.entities.users;

import bg.softuni._18_EX_JSON_Processing.productshop.entities.products.SoldProductDTO;

import java.util.List;

public class UserWithSoldProductsDTO {
    private String firstName;
    private String lastName;
    private List<SoldProductDTO> itemsBought;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<SoldProductDTO> getItemsBought() {
        return itemsBought;
    }

    public void setItemsBought(List<SoldProductDTO> itemsBought) {
        this.itemsBought = itemsBought;
    }
}
