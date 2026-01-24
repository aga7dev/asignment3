package service;

import exception.DuplicateResourceException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Customer;
import repository.CustomerRepository;

import java.util.List;

public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public Customer create(Customer customer) {
        customer.validate();

        if (customer.getEmail() != null && !customer.getEmail().trim().isEmpty()) {
            Customer existing = repo.getByEmail(customer.getEmail());
            if (existing != null) {
                throw new DuplicateResourceException("Customer with email '" + customer.getEmail() + "' already exists");
            }
        }

        return repo.create(customer);
    }

    public List<Customer> getAll() {
        return repo.getAll();
    }

    public Customer getById(int id) {
        if (id <= 0) throw new InvalidInputException("Customer id must be > 0");
        Customer c = repo.getById(id);
        if (c == null) throw new ResourceNotFoundException("Customer not found, id=" + id);
        return c;
    }

    public Customer update(int id, Customer customer) {
        if (id <= 0) throw new InvalidInputException("Customer id must be > 0");
        customer.validate();

        Customer current = repo.getById(id);
        if (current == null) throw new ResourceNotFoundException("Customer not found, id=" + id);

        if (customer.getEmail() != null && !customer.getEmail().trim().isEmpty()) {
            Customer byEmail = repo.getByEmail(customer.getEmail());
            if (byEmail != null && byEmail.getId() != id) {
                throw new DuplicateResourceException("Email already used by another customer");
            }
        }

        boolean ok = repo.update(id, customer);
        if (!ok) throw new ResourceNotFoundException("Customer not updated, id=" + id);
        return repo.getById(id);
    }

    public void delete(int id) {
        if (id <= 0) throw new InvalidInputException("Customer id must be > 0");

        Customer current = repo.getById(id);
        if (current == null) throw new ResourceNotFoundException("Customer not found, id=" + id);

        boolean ok = repo.delete(id);
        if (!ok) throw new ResourceNotFoundException("Customer not deleted, id=" + id);
    }
}
