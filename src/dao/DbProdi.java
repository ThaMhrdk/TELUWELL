/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import config.Koneksi;
import model.Prodi;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author THA
 */

public class DbProdi {
    private final Koneksi k = new Koneksi(); // ✅ SAMA SEPERTI DbMahasiswa

    public ArrayList<Prodi> getAllProdi() throws SQLException {
        ArrayList<Prodi> prodiList = new ArrayList<>();
        String sql = "SELECT * FROM prodi ORDER BY nmprodi";
        Connection connection = null;
        try {
            connection = k.getConnection(); // ✅ SAMA SEPERTI DbMahasiswa
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Prodi prodi = new Prodi(
                    rs.getString("Idprodi"),
                    rs.getString("nmprodi"),
                    rs.getString("jenjang"),
                    rs.getString("kdprodi"),
                    rs.getString("Idfakultas")
                );
                prodiList.add(prodi);
            }
        } finally {
            if (connection != null) connection.close();
        }
        return prodiList;
    }
    
    public Prodi getProdiById(String idProdi) throws SQLException {
        String sql = "SELECT * FROM prodi WHERE Idprodi = ?";
        Connection connection = null;
        try {
            connection = k.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, idProdi);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Prodi(
                    rs.getString("Idprodi"),
                    rs.getString("nmprodi"),
                    rs.getString("jenjang"),
                    rs.getString("kdprodi"),
                    rs.getString("Idfakultas")
                );
            }
        } finally {
            if (connection != null) connection.close();
        }
        return null;
    }
    
    public String getProdiNameById(String idProdi) throws SQLException {
        String sql = "SELECT nmprodi FROM prodi WHERE Idprodi = ?";
        Connection connection = null;
        try {
            connection = k.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, idProdi);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("nmprodi");
            }
        } finally {
            if (connection != null) connection.close();
        }
        return "Unknown Prodi";
    }
}