package bg.softuni._15_Spring_Data_Auto_Mapping_Objects.entities.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Set;

public class CompanyDTO {
    @Expose
    private String name;
    @Expose
    private Set<CreateEmployeeDTO> employees;

    public CompanyDTO(String name, Set<CreateEmployeeDTO> employees) {
        this.name = name;
        this.employees = employees;
    }
}
