package controller;

import model.BaseEntity;
import model.Car;
import model.Customer;
import model.Rental;
import service.CarService;
import service.CustomerService;
import service.RentalService;
import utils.ApiResponse;
import utils.ExceptionMapper;
import utils.InputHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CarRentalController {
    private final CarService carService;
    private final CustomerService customerService;
    private final RentalService rentalService;

    public CarRentalController(CarService carService, CustomerService customerService, RentalService rentalService) {
        this.carService = carService;
        this.customerService = customerService;
        this.rentalService = rentalService;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        InputHelper in = new InputHelper(sc);

        System.out.println("=== Car Rental CLI API ===");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1) Create Car");
            System.out.println("2) List Cars");
            System.out.println("3) Update Car");
            System.out.println("4) Delete Car");
            System.out.println("5) Create Customer");
            System.out.println("6) List Customers");
            System.out.println("7) Create Rental");
            System.out.println("8) List Rentals");
            System.out.println("9) Delete Rental");
            System.out.println("10) Demo: Polymorphism + Interfaces");
            System.out.println("0) Exit");

            try {
                int choice = in.readInt("Choose: ");

                if (choice == 0) break;

                switch (choice) {
                    case 1 -> createCar(in);
                    case 2 -> listCars();
                    case 3 -> updateCar(in);
                    case 4 -> deleteCar(in);
                    case 5 -> createCustomer(in);
                    case 6 -> listCustomers();
                    case 7 -> createRental(in);
                    case 8 -> listRentals();
                    case 9 -> deleteRental(in);
                    case 10 -> demoPolymorphismAndInterfaces();
                    default -> System.out.println(new ApiResponse(false, "Unknown option", null).toJsonLikeString());
                }

            } catch (Exception ex) {
                System.out.println(ExceptionMapper.toResponse(ex).toJsonLikeString());
            }
        }

        System.out.println("Bye!");
    }

    private void createCar(InputHelper in) {
        String name = in.readString("Car name: ");
        String plate = in.readString("Plate number: ");
        double rate = in.readDouble("Daily rate: ");
        String status = in.readString("Status (AVAILABLE/RENTED): ");

        Car car = new Car(0, name, plate, rate, status);
        Car saved = carService.create(car);

        System.out.println(new ApiResponse(true, "Car created", saved.shortInfo()).toJsonLikeString());
    }

    private void listCars() {
        List<Car> cars = carService.getAll();
        for (Car c : cars) {
            System.out.println(new ApiResponse(true, "OK", c.shortInfo()).toJsonLikeString());
        }
        if (cars.isEmpty()) {
            System.out.println(new ApiResponse(true, "No cars", null).toJsonLikeString());
        }
    }

    private void updateCar(InputHelper in) {
        int id = in.readInt("Car id to update: ");
        String name = in.readString("New car name: ");
        String plate = in.readString("New plate number: ");
        double rate = in.readDouble("New daily rate: ");
        String status = in.readString("New status (AVAILABLE/RENTED): ");

        Car car = new Car(0, name, plate, rate, status);
        Car updated = carService.update(id, car);

        System.out.println(new ApiResponse(true, "Car updated", updated.shortInfo()).toJsonLikeString());
    }

    private void deleteCar(InputHelper in) {
        int id = in.readInt("Car id to delete: ");
        carService.delete(id);
        System.out.println(new ApiResponse(true, "Car deleted", "id=" + id).toJsonLikeString());
    }

    private void createCustomer(InputHelper in) {
        String name = in.readString("Customer name: ");
        String email = in.readString("Email (optional): ");
        if (email != null && email.trim().isEmpty()) email = null;

        Customer customer = new Customer(0, name, email);
        Customer saved = customerService.create(customer);

        System.out.println(new ApiResponse(true, "Customer created", saved.shortInfo()).toJsonLikeString());
    }

    private void listCustomers() {
        List<Customer> customers = customerService.getAll();
        for (Customer c : customers) {
            System.out.println(new ApiResponse(true, "OK", c.shortInfo()).toJsonLikeString());
        }
        if (customers.isEmpty()) {
            System.out.println(new ApiResponse(true, "No customers", null).toJsonLikeString());
        }
    }

    private void createRental(InputHelper in) {
        String name = in.readString("Rental contract name: ");
        int carId = in.readInt("Car id: ");
        int customerId = in.readInt("Customer id: ");
        LocalDate start = in.readDate("Start date");
        LocalDate end = in.readDate("End date");

        Rental rental = rentalService.create(name, carId, customerId, start, end);
        System.out.println(new ApiResponse(true, "Rental created", rental.shortInfo()).toJsonLikeString());
    }

    private void listRentals() {
        List<Rental> rentals = rentalService.getAll();
        for (Rental r : rentals) {
            System.out.println(new ApiResponse(true, "OK", r.shortInfo()).toJsonLikeString());
        }
        if (rentals.isEmpty()) {
            System.out.println(new ApiResponse(true, "No rentals", null).toJsonLikeString());
        }
    }

    private void deleteRental(InputHelper in) {
        int id = in.readInt("Rental id to delete: ");
        rentalService.delete(id);
        System.out.println(new ApiResponse(true, "Rental deleted", "id=" + id).toJsonLikeString());
    }

    private void demoPolymorphismAndInterfaces() {
        Car car = new Car(1, "Demo Car", "D000DD", 10000, "AVAILABLE");
        Customer customer = new Customer(1, "Demo Customer", "demo@mail.com");

        BaseEntity e1 = car;
        BaseEntity e2 = customer;

        System.out.println(new ApiResponse(true, "Polymorphism", e1.getEntityType() + " -> " + e1.shortInfo()).toJsonLikeString());
        System.out.println(new ApiResponse(true, "Polymorphism", e2.getEntityType() + " -> " + e2.shortInfo()).toJsonLikeString());


        car.validate();
        customer.validate();

        double price = car.calculatePrice(3);
        System.out.println(new ApiResponse(true, "Interfaces", "Price for 3 days = " + price).toJsonLikeString());
    }
}
