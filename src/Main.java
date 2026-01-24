import controller.CarRentalController;
import repository.CarRepository;
import repository.CustomerRepository;
import repository.RentalRepository;
import service.CarService;
import service.CustomerService;
import service.RentalService;
import utils.DatabaseConnection;

public class Main {

    public static void main(String[] args) {

        DatabaseConnection db = new DatabaseConnection();


        CarRepository carRepository = new CarRepository(db);
        CustomerRepository customerRepository = new CustomerRepository(db);
        RentalRepository rentalRepository = new RentalRepository(db);


        CarService carService = new CarService(carRepository);
        CustomerService customerService = new CustomerService(customerRepository);
        RentalService rentalService =
                new RentalService(rentalRepository, carService, customerService);


        CarRentalController controller =
                new CarRentalController(carService, customerService, rentalService);


        controller.start();
    }
}
