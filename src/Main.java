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


        CarRepository carRepo = new CarRepository(db);
        CustomerRepository customerRepo = new CustomerRepository(db);
        RentalRepository rentalRepo = new RentalRepository(db);


        CarService carService = new CarService(carRepo);
        CustomerService customerService = new CustomerService(customerRepo);
        RentalService rentalService = new RentalService(rentalRepo, carService, customerService);


        CarRentalController controller = new CarRentalController(carService, customerService, rentalService);
        controller.start();
    }
}
