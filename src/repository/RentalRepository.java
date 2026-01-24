package repository;


import exception.DatabaseOperationException;
import model.Car;
import model.Customer;
import model.Rental;
import utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalRepository {
    private final DatabaseConnection db;

    public RentalRepository(DatabaseConnection db) {
        this.db = db;
    }

    public Rental create(Rental rental) {
        String sql = "INSERT INTO rentals(name, car_id, customer_id, start_date, end_date, total_price) " +
                "VALUES(?,?,?,?,?,?) RETURNING id";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rental.getName());
            ps.setInt(2, rental.getCar().getId());
            ps.setInt(3, rental.getCustomer().getId());
            ps.setDate(4, Date.valueOf(rental.getStartDate()));
            ps.setDate(5, Date.valueOf(rental.getEndDate()));
            ps.setDouble(6, rental.getTotalPrice());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rental.setId(rs.getInt("id"));
            }
            return rental;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create rental", e);
        }
    }

    public List<Rental> getAll() {
        String sql =
                "SELECT r.id, r.name, r.start_date, r.end_date, r.total_price, " +
                        "c.id AS car_id, c.name AS car_name, c.plate_number, c.daily_rate, c.status, " +
                        "cu.id AS customer_id, cu.name AS customer_name, cu.email " +
                        "FROM rentals r " +
                        "JOIN cars c ON c.id = r.car_id " +
                        "JOIN customers cu ON cu.id = r.customer_id " +
                        "ORDER BY r.id";

        List<Rental> list = new ArrayList<>();

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("car_id"),
                        rs.getString("car_name"),
                        rs.getString("plate_number"),
                        rs.getDouble("daily_rate"),
                        rs.getString("status")
                );

                Customer customer = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("email")
                );

                Rental rental = new Rental(
                        rs.getInt("id"),
                        rs.getString("name"),
                        car,
                        customer,
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getDouble("total_price")
                );

                list.add(rental);
            }

            return list;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get all rentals", e);
        }
    }

    public Rental getById(int id) {
        String sql =
                "SELECT r.id, r.name, r.start_date, r.end_date, r.total_price, " +
                        "c.id AS car_id, c.name AS car_name, c.plate_number, c.daily_rate, c.status, " +
                        "cu.id AS customer_id, cu.name AS customer_name, cu.email " +
                        "FROM rentals r " +
                        "JOIN cars c ON c.id = r.car_id " +
                        "JOIN customers cu ON cu.id = r.customer_id " +
                        "WHERE r.id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Car car = new Car(
                        rs.getInt("car_id"),
                        rs.getString("car_name"),
                        rs.getString("plate_number"),
                        rs.getDouble("daily_rate"),
                        rs.getString("status")
                );

                Customer customer = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("email")
                );

                return new Rental(
                        rs.getInt("id"),
                        rs.getString("name"),
                        car,
                        customer,
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getDouble("total_price")
                );
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get rental by id", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM rentals WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete rental", e);
        }
    }
}
