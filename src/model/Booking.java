/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author THA
 */

public class Booking {
    private String idbooking, metode, nim, idjadwal, kodekslg, status;
    private Date tglbooking;
    private Time jammulaib, jamselesaib;
    private Timestamp cancelledAt, completedAt;

    public Booking(String idbooking, Date tglbooking, Time jammulaib, Time jamselesaib, String metode, String nim, String idjadwal, String kodekslg, String status, Timestamp cancelledAt, Timestamp completedAt) {
        this.idbooking = idbooking;
        this.tglbooking = tglbooking;
        this.jammulaib = jammulaib;
        this.jamselesaib = jamselesaib;
        this.metode = metode;
        this.nim = nim;
        this.idjadwal = idjadwal;
        this.kodekslg = kodekslg;
        this.status = status;
        this.cancelledAt = cancelledAt;
        this.completedAt = completedAt;
    }

    public String getIdbooking() {
        return idbooking;
    }

    public Date getTglbooking() {
        return tglbooking;
    }

    public Time getJammulaib() {
        return jammulaib;
    }

    public Time getJamselesaib() {
        return jamselesaib;
    }

    public String getMetode() {
        return metode;
    }

    public String getNim() {
        return nim;
    }

    public String getIdjadwal() {
        return idjadwal;
    }

    public String getKodekslg() {
        return kodekslg;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCancelledAt() {
        return cancelledAt;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }
}