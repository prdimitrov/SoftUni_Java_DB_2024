package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
//@Table(name = "cars")
public class Car extends PassengerVehicle {
    private static final String CAR_TYPE = "CAR";

    public Car() {
        super(CAR_TYPE);
    }

    public Car(String model, String fuelType, int seats) {
        this(); //Това сочи към горния конструктор!
        this.model = model;
        this.fuelType = fuelType;
        this.seats = seats;
    }
}
