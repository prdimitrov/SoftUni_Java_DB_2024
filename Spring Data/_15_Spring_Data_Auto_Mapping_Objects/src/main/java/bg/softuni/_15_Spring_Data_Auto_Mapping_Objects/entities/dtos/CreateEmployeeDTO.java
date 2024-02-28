package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateEmployeeDTO {
    @Expose
    private final String firstName;
    private final String lastName;
    @Expose
    private final BigDecimal salary;
    @Expose
    private final LocalDate birthday;
    @Expose
    private final AddressDTO address;

    public CreateEmployeeDTO(String firstName, String lastName, BigDecimal salary, LocalDate birthday, AddressDTO address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public AddressDTO getAddress() {
        return address;
    }
}
