/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config; // Ini adalah paket baru untuk kelas Koneksi

import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * Kelas ini bertanggung jawab untuk membuat dan mengelola koneksi ke database MySQL.
 * Ini adalah bagian dari lapisan konfigurasi/utilitas aplikasi.
 * @author THA
 */
public class Koneksi {
    private final MysqlDataSource dataSource = new MysqlDataSource();
    
    // Pastikan ini adalah URL dan nama database untuk proyek TeluWell
    private final String DB_URL = "jdbc:mysql://localhost:3306/teluwell?serverTimezone=Asia/Jakarta";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "";

    /**
     * Mendapatkan objek Connection ke database TeluWell.
     * @return Objek Connection yang aktif.
     * @throws SQLException Jika terjadi kesalahan saat mencoba membuat koneksi.
     */
    public Connection getConnection() throws SQLException {
        dataSource.setUrl(DB_URL);
        dataSource.setUser(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        return dataSource.getConnection();
    }
}