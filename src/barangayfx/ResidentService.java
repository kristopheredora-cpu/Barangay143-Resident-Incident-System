package barangayfx;

import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;

public class ResidentService {

    public static void loadAll(ObservableList<Resident> list) throws SQLException {
        list.clear();
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT id, full_name, address, birthdate, contact_no, status, " +
                 "gender, civil_status, occupation, email, emergency_contact, " +
                 "COALESCE(date_registered,'') as date_registered " +
                 "FROM residents ORDER BY id DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Resident r = new Resident(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("address"),
                    rs.getString("birthdate")        != null ? rs.getString("birthdate")        : "",
                    rs.getString("contact_no")       != null ? rs.getString("contact_no")       : "",
                    rs.getString("status")           != null ? rs.getString("status")           : "Active",
                    rs.getString("gender")           != null ? rs.getString("gender")           : "Male",
                    rs.getString("civil_status")     != null ? rs.getString("civil_status")     : "Single",
                    rs.getString("occupation")       != null ? rs.getString("occupation")       : "",
                    rs.getString("email")            != null ? rs.getString("email")            : "",
                    rs.getString("emergency_contact")!= null ? rs.getString("emergency_contact"): ""
                );
                r.setDateRegistered(rs.getString("date_registered"));
                list.add(r);
            }
        }
    }

    public static void loadRecent(ObservableList<Resident> list) throws SQLException {
        list.clear();
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT id, full_name, address, birthdate, contact_no, status, " +
                 "gender, civil_status, occupation, email, emergency_contact, " +
                 "COALESCE(date_registered,'') as date_registered " +
                 "FROM residents ORDER BY id DESC LIMIT 10");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Resident r = new Resident(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("address"),
                    rs.getString("birthdate")        != null ? rs.getString("birthdate")        : "",
                    rs.getString("contact_no")       != null ? rs.getString("contact_no")       : "",
                    rs.getString("status")           != null ? rs.getString("status")           : "Active",
                    rs.getString("gender")           != null ? rs.getString("gender")           : "Male",
                    rs.getString("civil_status")     != null ? rs.getString("civil_status")     : "Single",
                    rs.getString("occupation")       != null ? rs.getString("occupation")       : "",
                    rs.getString("email")            != null ? rs.getString("email")            : "",
                    rs.getString("emergency_contact")!= null ? rs.getString("emergency_contact"): ""
                );
                r.setDateRegistered(rs.getString("date_registered"));
                list.add(r);
            }
        }
    }

    public static void insert(String fullName, String address, String birthdate,
                               String contactNo, String status, String gender,
                               String civilStatus, String occupation,
                               String email, String emergencyContact) throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO residents (full_name, address, birthdate, contact_no, status, " +
                 "gender, civil_status, occupation, email, emergency_contact, date_registered) " +
                 "VALUES (?,?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1,  fullName);
            ps.setString(2,  address);
            ps.setString(3,  birthdate);
            ps.setString(4,  contactNo);
            ps.setString(5,  status);
            ps.setString(6,  gender);
            ps.setString(7,  civilStatus);
            ps.setString(8,  occupation);
            ps.setString(9,  email);
            ps.setString(10, emergencyContact);
            ps.setString(11, LocalDate.now().toString());
            ps.executeUpdate();
        }
    }

    public static void update(int id, String fullName, String address, String birthdate,
                               String contactNo, String status, String gender,
                               String civilStatus, String occupation,
                               String email, String emergencyContact) throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE residents SET full_name=?, address=?, birthdate=?, contact_no=?, " +
                 "status=?, gender=?, civil_status=?, occupation=?, email=?, " +
                 "emergency_contact=? WHERE id=?")) {
            ps.setString(1,  fullName);
            ps.setString(2,  address);
            ps.setString(3,  birthdate);
            ps.setString(4,  contactNo);
            ps.setString(5,  status);
            ps.setString(6,  gender);
            ps.setString(7,  civilStatus);
            ps.setString(8,  occupation);
            ps.setString(9,  email);
            ps.setString(10, emergencyContact);
            ps.setInt   (11, id);
            ps.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "DELETE FROM residents WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static int countByStatus(String status) throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT COUNT(*) FROM residents WHERE status=?")) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public static int countThisMonth() throws SQLException {
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT COUNT(*) FROM residents WHERE MONTH(date_registered)=MONTH(CURRENT_DATE) " +
                 "AND YEAR(date_registered)=YEAR(CURRENT_DATE)");
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}