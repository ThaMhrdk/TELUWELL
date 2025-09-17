/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.TeleponMhs;
import config.Koneksi;

/**
 * @author THA
 */
public class DbTeleponMhs {
    private Connection conn;
    private final Koneksi k = new Koneksi();

    public boolean isNomorTeleponExists(String telpMhs, String nim) throws SQLException {
        conn = k.getConnection();
        String kueri = "SELECT COUNT(*) AS count FROM teleponmhs WHERE telpmhs = ? AND nim = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            ps = conn.prepareStatement(kueri);
            ps.setString(1, telpMhs);
            ps.setString(2, nim);
            rs = ps.executeQuery();

            if (rs.next()) {
                exists = rs.getInt("count") > 0;
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
        return exists;
    }

    public boolean insertTeleponMhs(TeleponMhs tm) throws SQLException {
        conn = k.getConnection();
        String kueri = "INSERT INTO teleponmhs(telpmhs, nim) VALUES(?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(kueri);
            ps.setString(1, tm.getTelpMhs());
            ps.setString(2, tm.getNim());

            int rowAffected = ps.executeUpdate();
            return rowAffected == 1;
        } finally {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
    }
}