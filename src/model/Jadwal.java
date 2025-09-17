/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import java.sql.Time;

/**
 * @author THA
 */

public class Jadwal {
    private String idjadwal, harij;
    private Time jammulaij, jamselesaij;

    public Jadwal(String idjadwal, String harij, Time jammulaij, Time jamselesaij) {
        this.idjadwal = idjadwal;
        this.harij = harij;
        this.jammulaij = jammulaij;
        this.jamselesaij = jamselesaij;
    }

    public String getIdjadwal() {
        return idjadwal;
    }

    public String getHarij() {
        return harij;
    }

    public Time getJammulaij() {
        return jammulaij;
    }

    public Time getJamselesaij() {
        return jamselesaij;
    }
}
