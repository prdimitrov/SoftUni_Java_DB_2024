package entities;

import jakarta.persistence.Basic;
import jakarta.persistence.MappedSuperclass;

//@Entity
//Междинен клас, който обединява типове, да обединява логика, да не се повтаря код
@MappedSuperclass //полетата които са дефинирани тук, да отидат към крайните наследници
//Ако използваме MappedSuperclass, не е нужно да използваме празен конструктор!
public class PassengerVehicle extends Vehicle {
    @Basic
    protected int seats;
//    public PassengerVehicle() {
//
//    }
    public PassengerVehicle(String type) {
        super(type);
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
