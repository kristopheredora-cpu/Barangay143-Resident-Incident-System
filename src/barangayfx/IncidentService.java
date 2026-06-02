package barangayfx;

import javafx.collections.ObservableList;

import java.sql.*;

public class IncidentService {

    public static void loadAll(ObservableList<Incident> list) throws SQLException {
        list.clear();
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT id, description, location, reported_by, status " +
                 "FROM incidents ORDER BY id DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Incident(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("reported_by"),
                    rs.getString("status") != null ? rs.getString("status") : "Open"
                ));
            }
        }
    }

    public static void insert(String desc, String loc,
                              String by, String status) throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO incidents (description, location, reported_by, status) " +
                 "VALUES (?,?,?,?)")) {

            ps.setString(1, desc);
            ps.setString(2, loc);
            ps.setString(3, by);
            ps.setString(4, status);
            ps.executeUpdate();
        }
    }

    public static void update(int id, String desc, String loc,
                              String by, String status) throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE incidents SET description=?, location=?, " +
                 "reported_by=?, status=? WHERE id=?")) {

            ps.setString(1, desc);
            ps.setString(2, loc);
            ps.setString(3, by);
            ps.setString(4, status);
            ps.setInt(5, id);
            ps.executeUpdate();
        }
    }

    // ── Delete ───────────────────────────────────────────────────────
    public static void delete(int id) throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "DELETE FROM incidents WHERE id=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}