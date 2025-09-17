/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import config.Koneksi;
import model.Mahasiswa;
import model.Prodi;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author THA
 */


public class DbMahasiswa {
    private final Koneksi k = new Koneksi();

    public boolean insertMahasiswa(Mahasiswa mahasiswa) throws SQLException {
        String sql = "INSERT INTO mahasiswa (nim, email, nmdepan, nmbelakang, jk, Idprodi, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        try {
            connection = k.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mahasiswa.getNim());
            stmt.setString(2, mahasiswa.getEmail());
            stmt.setString(3, mahasiswa.getNmdepan());
            stmt.setString(4, mahasiswa.getNmbelakang());
            stmt.setString(5, mahasiswa.getJk());
            stmt.setString(6, mahasiswa.getIdprodi());
            stmt.setString(7, mahasiswa.getPassword());
            return stmt.executeUpdate() > 0;
        } finally {
            if (connection != null) connection.close();
        }
    }

    public Mahasiswa loginMahasiswa(String nim, String password) throws SQLException {
        String sql = "SELECT * FROM mahasiswa WHERE nim = ? AND password = ?";
        Connection connection = null;
        try {
            connection = k.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nim);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Mahasiswa(
                    rs.getString("nim"),
                    rs.getString("email"),
                    rs.getString("nmdepan"),
                    rs.getString("nmbelakang"),
                    rs.getString("jk"),
                    rs.getString("Idprodi"),
                    rs.getString("password")
                );
            }
        } finally {
            if (connection != null) connection.close();
        }
        return null;
    }

    public ArrayList<Prodi> getAllProdi() throws SQLException {
        ArrayList<Prodi> prodiList = new ArrayList<>();
        String sql = "SELECT * FROM prodi";
        Connection connection = null;
        try {
            connection = k.getConnection();
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
}