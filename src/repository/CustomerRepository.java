package repository;

import utils.DatabaseConnection;
import exception.DatabaseOperationException;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private final DatabaseConnection db;

    public CustomerRepository(DatabaseConnection db) {
        this.db = db;
    }

    public Customer create(Customer customer) {
        String sql = "INSERT INTO customers(name, email) VALUES(?,?) RETURNING id";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer.setId(rs.getInt("id"));
            }
            return customer;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create customer", e);
        }
    }

    public List<Customer> getAll() {
        String sql = "SELECT id, name, email FROM customers ORDER BY id";
        List<Customer> list = new ArrayList<>();

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                list.add(c);
            }
            return list;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get all customers", e);
        }
    }

    public Customer getById(int id) {
        String sql = "SELECT id, name, email FROM customers WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get customer by id", e);
        }
    }

    public Customer getByEmail(String email) {
        String sql = "SELECT id, name, email FROM customers WHERE email = ?";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get customer by email", e);
        }
    }

    public boolean update(int id, Customer customer) {
        String sql = "UPDATE customers SET name=?, email=? WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update customer", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM customers WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete customer", e);
        }
    }
}
