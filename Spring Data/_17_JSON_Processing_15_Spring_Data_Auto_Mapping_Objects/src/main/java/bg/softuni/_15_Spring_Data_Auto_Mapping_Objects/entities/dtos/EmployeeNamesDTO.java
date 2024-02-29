package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos;

public class EmployeeNamesDTO {
    private String firstName;
    private String lastName;

    public EmployeeNamesDTO() {

    }

    public EmployeeNamesDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

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

    @Override
    public String toString() {
        return "EmployeeNamesDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
