/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import config.Koneksi;
import model.Konselor;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author THA
 */

public class DbKonselor {
    private final Koneksi k = new Koneksi();

    public ArrayList<Konselor> getAllKonselor() throws SQLException {
        ArrayList<Konselor> konselorList = new ArrayList<>();
        String sql = "SELECT * FROM konselor";
        Connection connection = null;
        try {
            connection = k.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Konselor konselor = new Konselor(
                    rs.getString("Idkonselor"),
                    rs.getString("jdwlkonf"),
                    rs.getString("emailkons"),
                    rs.getString("nmkonselor"),
                    rs.getString("spesialisasi"),
                    rs.getString("password")
                );
                konselorList.add(konselor);
            }
        } finally {
            if (connection != null) connection.close();
        }
        return konselorList;
    }

    public Konselor loginKonselor(String email, String password) throws SQLException {
        String sql = "SELECT * FROM konselor WHERE emailkons = ? AND password = ?";
        Connection connection = null;
        try {
            connection = k.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Konselor(
                    rs.getString("Idkonselor"),
                    rs.getString("jdwlkonf"),
                    rs.getString("emailkons"),
                    rs.getString("nmkonselor"),
                    rs.getString("spesialisasi"),
                    rs.getString("password")
                );
            }
        } finally {
            if (connection != null) connection.close();
        }
        return null;
    }
}