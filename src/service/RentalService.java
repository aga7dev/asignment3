package service;

import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Car;
import model.Customer;
import model.Rental;
import repository.RentalRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentalService {
    private final RentalRepository rentalRepo;
    private final CarService carService;
    private final CustomerService customerService;

    public RentalService(RentalRepository rentalRepo, CarService carService, CustomerService customerService) {
        this.rentalRepo = rentalRepo;
        this.carService = carService;
        this.customerService = customerService;
    }

    public Rental create(String name, int carId, int customerId, LocalDate start, LocalDate end) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Rental name must not be empty");
        }
        if (start == null || end == null) {
            throw new InvalidInputException("Start date and end date must not be null");
        }
        if (end.isBefore(start)) {
            throw new InvalidInputException("End date must be after or equal start date");
        }


        Car car = carService.getById(carId);
        Customer customer = customerService.getById(customerId);


        if (!"AVAILABLE".equalsIgnoreCase(car.getStatus())) {
            throw new InvalidInputException("Car is not available for rental");
        }

        int days = (int) ChronoUnit.DAYS.between(start, end) + 1; // считаем включительно
        double total = car.calculatePrice(days); // интерфейс PricedItem используется

        Rental rental = new Rental();
        rental.setName(name);
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setStartDate(start);
        rental.setEndDate(end);
        rental.setTotalPrice(total);

        Rental created = rentalRepo.create(rental);


        carService.markRented(carId);

        return created;
    }

    public List<Rental> getAll() {
        return rentalRepo.getAll();
    }

    public Rental getById(int id) {
        if (id <= 0) throw new InvalidInputException("Rental id must be > 0");
        Rental r = rentalRepo.getById(id);
        if (r == null) throw new ResourceNotFoundException("Rental not found, id=" + id);
        return r;
    }

    public void delete(int id) {
        Rental rental = getById(id);


        int carId = rental.getCar().getId();

        boolean ok = rentalRepo.delete(id);
        if (!ok) throw new ResourceNotFoundException("Rental not deleted, id=" + id);

        carService.markAvailable(carId);
    }
}
