/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

/**
 * @author THA
 */

public class JadwalKons {
    private String idjadwalKons, idjadwal, idkonselor;

    public JadwalKons(String idjadwalKons, String idjadwal, String idkonselor) {
        this.idjadwalKons = idjadwalKons;
        this.idjadwal = idjadwal;
        this.idkonselor = idkonselor;
    }

    public String getIdjadwalKons() {
        return idjadwalKons;
    }

    public String getIdjadwal() {
        return idjadwal;
    }

    public String getIdkonselor() {
        return idkonselor;
    }
}