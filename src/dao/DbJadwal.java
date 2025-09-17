/*
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/

package dao;

import config.Koneksi;
import model.Jadwal;
import model.JadwalKons;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author THA
 */

public class DbJadwal {
    private final Koneksi k = new Koneksi();

    public ArrayList<Jadwal> getAllJadwal() throws SQLException {
        ArrayList<Jadwal> jadwalList = new ArrayList<>();
        String sql = "SELECT * FROM jadwal";
        Connection connection = null;
        try {
            connection = k.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Jadwal jadwal = new Jadwal(
                    rs.getString("Idjadwal"),
                    rs.getString("harij"),
                    rs.getTime("jammulaij"),
                    rs.getTime("jamselesaij")
                );
                jadwalList.add(jadwal);
            }
        } finally {
            if (connection != null) connection.close();
        }
        return jadwalList;
    }

    public ArrayList<JadwalKons> getJadwalKonselorByKonselor(String idKonselor) throws SQLException {
        ArrayList<JadwalKons> jadwalList = new ArrayList<>();
        String sql = "SELECT * FROM jadwalkons WHERE Idkonselor = ?";
        Connection connection = null;
        try {
            connection = k.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, idKonselor);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                JadwalKons jadwal = new JadwalKons(
                    rs.getString("Idjadwal_kons"),
                    rs.getString("Idjadwal"),
                    rs.getString("Idkonselor")
                );
                jadwalList.add(jadwal);
            }
        } finally {
            if (connection != null) connection.close();
        }
        return jadwalList;
    }

    public boolean insertJadwalKonselor(String idKonselor, String idJadwal) throws SQLException {
        String sql = "INSERT INTO jadwalkons (Idjadwal_kons, Idjadwal, Idkonselor) VALUES (?, ?, ?)";
        Connection connection = null;
        try {
            connection = k.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, generateIdJadwalKons());
            stmt.setString(2, idJadwal);
            stmt.setString(3, idKonselor);
            return stmt.executeUpdate() > 0;
        } finally {
            if (connection != null) connection.close();
        }
    }

    public boolean deleteJadwalKonselor(String idJadwalKons) throws SQLException {
        String sql = "DELETE FROM jadwalkons WHERE Idjadwal_kons = ?";
        Connection connection = null;
        try {
            connection = k.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, idJadwalKons);
            return stmt.executeUpdate() > 0;
        } finally {
            if (connection != null) connection.close();
        }
    }

    private String generateIdJadwalKons() throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(Idjadwal_kons, 3) AS UNSIGNED)) as maxId FROM jadwalkons WHERE Idjadwal_kons LIKE 'JK%'";
        Connection connection = null;
        try {
            connection = k.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                int maxId = rs.getInt("maxId");
                return String.format("JK%03d", maxId + 1);
            } else {
                return "JK001";
            }
        } finally {
            if (connection != null) connection.close();
        }
    }
}