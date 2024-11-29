//Class that interacts with the Appointment database

import java.sql.*;

public class AppointmentDB {
    public void saveAppointment(Appointment appointment) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO appointments (id, date, details) VALUES (?, ?, ?)")) {
            stmt.setString(1, appointment.getId());
            stmt.setDate(2, Date.valueOf(appointment.getDate()));
            stmt.setString(3, appointment.getDetails());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Appointment fetchAppointment(String appointmentID) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM appointments WHERE id = ?")) {
            stmt.setString(1, appointmentID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Appointment(rs.getString("id"), rs.getDate("date").toLocalDate(), rs.getString("details"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateAppointment(Appointment appointment) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE appointments SET date = ?, details = ? WHERE id = ?")) {
            stmt.setDate(1, Date.valueOf(appointment.getDate()));
            stmt.setString(2, appointment.getDetails());
            stmt.setString(3, appointment.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAppointment(String appointmentID) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM appointments WHERE id = ?")) {
            stmt.setString(1, appointmentID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
