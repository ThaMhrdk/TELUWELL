/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

/**
 * @author THA
 */

public class TeleponMhs {
    private String telpMhs; // Nomor telepon mahasiswa
    private String nim;     // Foreign Key ke Mahasiswa

    public TeleponMhs(String telpMhs, String nim) {
        this.telpMhs = telpMhs;
        this.nim = nim;
    }

    public String getTelpMhs() {
        return telpMhs;
    }

    public String getNim() {
        return nim;
    }
}