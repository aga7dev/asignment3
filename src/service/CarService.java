package service;

import exception.DuplicateResourceException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Car;
import repository.CarRepository;

import java.util.List;

public class CarService {
    private final CarRepository repo;

    public CarService(CarRepository repo) {
        this.repo = repo;
    }

    public Car create(Car car) {
        car.validate();

        Car existing = repo.getByPlate(car.getPlateNumber());
        if (existing != null) {
            throw new DuplicateResourceException("Car with plate '" + car.getPlateNumber() + "' already exists");
        }


        if (car.getStatus() == null || car.getStatus().trim().isEmpty()) {
            car.setStatus("AVAILABLE");
        }

        return repo.create(car);
    }

    public List<Car> getAll() {
        return repo.getAll();
    }

    public Car getById(int id) {
        if (id <= 0) throw new InvalidInputException("Car id must be > 0");
        Car car = repo.getById(id);
        if (car == null) throw new ResourceNotFoundException("Car not found, id=" + id);
        return car;
    }

    public Car update(int id, Car car) {
        if (id <= 0) throw new InvalidInputException("Car id must be > 0");
        car.validate();

        Car current = repo.getById(id);
        if (current == null) throw new ResourceNotFoundException("Car not found, id=" + id);

        Car byPlate = repo.getByPlate(car.getPlateNumber());
        if (byPlate != null && byPlate.getId() != id) {
            throw new DuplicateResourceException("Plate number already used by another car");
        }

        boolean ok = repo.update(id, car);
        if (!ok) throw new ResourceNotFoundException("Car not updated, id=" + id);
        return repo.getById(id);
    }

    public void delete(int id) {
        if (id <= 0) throw new InvalidInputException("Car id must be > 0");

        Car car = repo.getById(id);
        if (car == null) throw new ResourceNotFoundException("Car not found, id=" + id);


        if ("RENTED".equalsIgnoreCase(car.getStatus())) {
            throw new InvalidInputException("Cannot delete car because status is RENTED");
        }

        boolean ok = repo.delete(id);
        if (!ok) throw new ResourceNotFoundException("Car not deleted, id=" + id);
    }

    public void markRented(int carId) {
        Car car = getById(carId);
        car.setStatus("RENTED");
        repo.update(carId, car);
    }

    public void markAvailable(int carId) {
        Car car = getById(carId);
        car.setStatus("AVAILABLE");
        repo.update(carId, car);
    }
}
