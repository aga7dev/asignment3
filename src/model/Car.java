package model;



import interfaces.PricedItem;
import interfaces.Validatable;


public class Car extends BaseEntity implements Validatable, PricedItem {
    private String plateNumber;
    private double dailyRate;
    private String status;
    private Engine engine;


    public Car() {
    }

    public Car(int id, String name, String plateNumber, double dailyRate, String status) {
        super(id, name);
        this.plateNumber = plateNumber;
        this.dailyRate = dailyRate;
        this.status = status;
    }

    @Override
    public String getEntityType() {
        return "Car";
    }

    @Override
    public String shortInfo() {
        return "Car{id=" + getId() + ", name=" + getName() + ", plate=" + plateNumber + ", rate=" + dailyRate + ", status=" + status + "}";
    }

    @Override
    public void validate() {
        if (getName() == null || getName().trim().isEmpty()) {
            throw new exception.InvalidInputException("Car name must not be empty");
        }
        if (plateNumber == null || plateNumber.trim().isEmpty()) {
            throw new exception.InvalidInputException("Plate number must not be empty");
        }
        if (dailyRate <= 0) {
            throw new exception.InvalidInputException("Daily rate must be > 0");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new exception.InvalidInputException("Car status must not be empty");
        }
    }

    @Override
    public double calculatePrice(int days) {
        if (days <= 0) return 0;
        return dailyRate * days;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }


    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

