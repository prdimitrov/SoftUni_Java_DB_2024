package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos;

import java.math.BigDecimal;

public class EmployeeDTO {
    private String firstName;

    private BigDecimal salary;

    private String city;

    public EmployeeDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "firstName='" + firstName + '\'' +
                ", salary=" + salary +
                ", city='" + city + '\'' +
                '}';
    }
}
