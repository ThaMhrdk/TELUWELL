/*
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/

package dao;

import config.Koneksi;
import model.Booking;
import java.sql.*;
import java.util.ArrayList;
/**
@author THA
*/

public class DbBooking {
    private final Koneksi k = new Koneksi();

    public ArrayList<Object[]> getBookingByMahasiswa(String nim) throws SQLException {
        ArrayList<Object[]> result = new ArrayList<>();
        String sql = "SELECT b.Idbooking, k.nmkonselor, b.tglbooking, b.jammulaib, b.jamselesaib, b.metode, b.status " +
                     "FROM booking b " +
                     "JOIN jadwal j ON b.Idjadwal = j.Idjadwal " +
                     "JOIN jadwalkons jk ON j.Idjadwal = jk.Idjadwal " +
                     "JOIN konselor k ON jk.Idkonselor = k.Idkonselor " +
                     "WHERE b.nim = ?";
        Connection connection = null;
        try {
            connection = k.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nim);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getString("Idbooking");
                row[1] = rs.getString("nmkonselor");
                row[2] = rs.getDate("tglbooking");
                row[3] = rs.getTime("jammulaib");
                row[4] = rs.getTime("jamselesaib");
                row[5] = rs.getString("metode");
                row[6] = rs.getString("status");
                result.add(row);
            }
        } finally {
            if (connection != null) connection.close();
        }
        return result;
    }

    public boolean insertBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO booking (Idbooking, tglbooking, jammulaib, jamselesaib, metode, nim, Idjadwal, Kodekslg, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        try {
            connection = k.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, booking.getIdbooking());
            stmt.setDate(2, booking.getTglbooking());
            stmt.setTime(3, booking.getJammulaib());
            stmt.setTime(4, booking.getJamselesaib());
            stmt.setString(5, booking.getMetode());
            stmt.setString(6, booking.getNim());
            stmt.setString(7, booking.getIdjadwal());
            stmt.setString(8, booking.getKodekslg());
            stmt.setString(9, booking.getStatus());
            return stmt.executeUpdate() > 0;
        } finally {
            if (connection != null) connection.close();
        }
    }

    public String generateBookingId() throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(Idbooking, 2) AS UNSIGNED)) as maxId FROM booking WHERE Idbooking LIKE 'B%'";
        Connection connection = null;
        try {
            connection = k.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                int maxId = rs.getInt("maxId");
                return String.format("B%04d", maxId + 1);
            } else {
                return "B0001";
            }
        } finally {
            if (connection != null) connection.close();
        }
    }

    public String generateKonselingId() throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(Kodekslg, 2) AS UNSIGNED)) as maxId FROM konseling WHERE Kodekslg LIKE 'K%'";
        Connection connection = null;
        try {
            connection = k.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                int maxId = rs.getInt("maxId");
                return String.format("K%04d", maxId + 1);
            } else {
                return "K0001";
            }
        } finally {
            if (connection != null) connection.close();
        }
    }

    public boolean insertKonseling(String kodekslg, String ruangan, Date tglkslg, Time jammulai, Time jamselesai) throws SQLException {
        String sql = "INSERT INTO konseling (Kodekslg, ruangan, tglkslg, jammulai, jamselesai) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        try {
            connection = k.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, kodekslg);
            stmt.setString(2, ruangan);
            stmt.setDate(3, tglkslg);
            stmt.setTime(4, jammulai);
            stmt.setTime(5, jamselesai);
            return stmt.executeUpdate() > 0;
        } finally {
            if (connection != null) connection.close();
        }
    }
    
    public boolean completeBooking(String idBooking) throws SQLException {
        String sql = "UPDATE booking SET status = 'COMPLETED' WHERE Idbooking = ?";
        Connection connection = null;
        try {
            connection = k.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, idBooking);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}