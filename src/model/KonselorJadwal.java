/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import java.sql.Time;

/**
 * @author THA
 */

public class KonselorJadwal {
    private String idKonselor;
    private String namaKonselor;
    private String spesialisasi;
    private String hariJadwal;
    private Time jamMulaiJadwal;
    private Time jamSelesaiJadwal;
    private String idJadwal;

    public KonselorJadwal(String idKonselor, String namaKonselor, String spesialisasi, String hariJadwal, Time jamMulaiJadwal, Time jamSelesaiJadwal, String idJadwal) {
        this.idKonselor = idKonselor;
        this.namaKonselor = namaKonselor;
        this.spesialisasi = spesialisasi;
        this.hariJadwal = hariJadwal;
        this.jamMulaiJadwal = jamMulaiJadwal;
        this.jamSelesaiJadwal = jamSelesaiJadwal;
        this.idJadwal = idJadwal;
    }

    public String getIdKonselor() {
        return idKonselor;
    }

    public String getNamaKonselor() {
        return namaKonselor;
    }

    public String getSpesialisasi() {
        return spesialisasi;
    }

    public String getHariJadwal() {
        return hariJadwal;
    }

    public Time getJamMulaiJadwal() {
        return jamMulaiJadwal;
    }

    public Time getJamSelesaiJadwal() {
        return jamSelesaiJadwal;
    }
    
    public String getIdJadwal() {
        return idJadwal;
    }
    
    public String getJamTersediaString() {
        if (jamMulaiJadwal == null || jamSelesaiJadwal == null) {
            return "N/A";
        }
        return jamMulaiJadwal.toString().substring(0, 5) + " - " + jamSelesaiJadwal.toString().substring(0, 5);
    }
}