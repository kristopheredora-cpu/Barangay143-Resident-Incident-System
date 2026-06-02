package barangayfx;

import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class InventoryService {

    // ── Load all ──────────────────────────────────────────────────────────
    public static void loadAll(ObservableList<InventoryItem> list) throws SQLException {
        list.clear();
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT id, item_name, quantity, stock_status, " +
                 "       location, last_updated, notes " +
                 "FROM inventory ORDER BY id DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new InventoryItem(
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getInt("quantity"),
                    rs.getString("stock_status") != null
                        ? rs.getString("stock_status") : "In Stock",
                    rs.getString("location")    != null
                        ? rs.getString("location")    : "",
                    rs.getString("last_updated") != null
                        ? rs.getString("last_updated") : "",
                    rs.getString("notes")       != null
                        ? rs.getString("notes")       : ""
                ));
            }
        }
    }

    // ── Insert ────────────────────────────────────────────────────────────
    public static void insert(String itemName, int quantity,
                              String stockStatus, String location,
                              String notes) throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO inventory " +
                 "(item_name, quantity, stock_status, location, last_updated, notes) " +
                 "VALUES (?, ?, ?, ?, ?, ?)")) {

            ps.setString(1, itemName);
            ps.setInt   (2, quantity);
            ps.setString(3, stockStatus);
            ps.setString(4, location);
            ps.setString(5, LocalDate.now().toString());
            ps.setString(6, notes);
            ps.executeUpdate();
        }
    }

    // ── Update ────────────────────────────────────────────────────────────
    public static void update(int id, String itemName, int quantity,
                              String stockStatus, String location,
                              String notes) throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE inventory SET item_name=?, quantity=?, stock_status=?, " +
                 "location=?, last_updated=?, notes=? WHERE id=?")) {

            ps.setString(1, itemName);
            ps.setInt   (2, quantity);
            ps.setString(3, stockStatus);
            ps.setString(4, location);
            ps.setString(5, LocalDate.now().toString());
            ps.setString(6, notes);
            ps.setInt   (7, id);
            ps.executeUpdate();
        }
    }

    // ── Delete ────────────────────────────────────────────────────────────
    public static void delete(int id) throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "DELETE FROM inventory WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // ── Summary counts ────────────────────────────────────────────────────
    public static int countByStatus(String status) throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT COUNT(*) FROM inventory WHERE stock_status=?")) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public static int totalQuantity() throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT COALESCE(SUM(quantity),0) FROM inventory");
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}


