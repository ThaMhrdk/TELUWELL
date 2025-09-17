/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

/**
 * @author THA
 */

public class Konselor {
    private String idkonselor, jdwlkonf, emailkons, nmkonselor, spesialisasi, password;

    public Konselor(String idkonselor, String jdwlkonf, String emailkons, String nmkonselor, String spesialisasi, String password) {
        this.idkonselor = idkonselor;
        this.jdwlkonf = jdwlkonf;
        this.emailkons = emailkons;
        this.nmkonselor = nmkonselor;
        this.spesialisasi = spesialisasi;
        this.password = password;
    }

    public String getIdkonselor() {
        return idkonselor;
    }

    public String getJdwlkonf() {
        return jdwlkonf;
    }

    public String getEmailkons() {
        return emailkons;
    }

    public String getNmkonselor() {
        return nmkonselor;
    }

    public String getSpesialisasi() {
        return spesialisasi;
    }

    public String getPassword() {
        return password;
    }
}