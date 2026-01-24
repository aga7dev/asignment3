package model;

import java.time.LocalDate;

public class Rental {
    private int id;
    private String name;
    private Car car;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;

    public Rental() {
    }

    public Rental(int id, String name, Car car, Customer customer,
                  LocalDate startDate, LocalDate endDate, double totalPrice) {
        this.id = id;
        this.name = name;
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public String shortInfo() {
        int carId = (car == null) ? 0 : car.getId();
        int customerId = (customer == null) ? 0 : customer.getId();
        return "Rental{id=" + id + ", name=" + name + ", carId=" + carId +
                ", customerId=" + customerId + ", start=" + startDate + ", end=" + endDate +
                ", total=" + totalPrice + "}";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
