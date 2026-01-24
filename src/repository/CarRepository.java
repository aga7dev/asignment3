
package repository;

import exception.DatabaseOperationException;
import model.Car;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {
    private final DatabaseConnection db;

    public CarRepository(DatabaseConnection db) {
        this.db = db;
    }

    public Car create(Car car) {
        String sql = "INSERT INTO cars(name, plate_number, daily_rate, status) VALUES(?,?,?,?) RETURNING id";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, car.getName());
            ps.setString(2, car.getPlateNumber());
            ps.setDouble(3, car.getDailyRate());
            ps.setString(4, car.getStatus());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                car.setId(rs.getInt("id"));
            }
            return car;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create car", e);
        }
    }

    public List<Car> getAll() {
        String sql = "SELECT id, name, plate_number, daily_rate, status FROM cars ORDER BY id";
        List<Car> list = new ArrayList<>();

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Car c = new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("plate_number"),
                        rs.getDouble("daily_rate"),
                        rs.getString("status")
                );
                list.add(c);
            }
            return list;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get all cars", e);
        }
    }

    public Car getById(int id) {
        String sql = "SELECT id, name, plate_number, daily_rate, status FROM cars WHERE id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("plate_number"),
                        rs.getDouble("daily_rate"),
                        rs.getString("status")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get car by id", e);
        }
    }

    public Car getByPlate(String plate) {
        String sql = "SELECT id, name, plate_number, daily_rate, status FROM cars WHERE plate_number = ?";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, plate);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("plate_number"),
                        rs.getDouble("daily_rate"),
                        rs.getString("status")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get car by plate", e);
        }
    }

    public boolean update(int id, Car car) {
        String sql = "UPDATE cars SET name=?, plate_number=?, daily_rate=?, status=? WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, car.getName());
            ps.setString(2, car.getPlateNumber());
            ps.setDouble(3, car.getDailyRate());
            ps.setString(4, car.getStatus());
            ps.setInt(5, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update car", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM cars WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete car", e);
        }
    }
}
