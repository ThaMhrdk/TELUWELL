/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

/**
 * @author THA
 */

public class Mahasiswa {
    private String nim, email, nmdepan, nmbelakang, jk, idprodi, password;

    public Mahasiswa(String nim, String email, String nmdepan, String nmbelakang, String jk, String idprodi, String password) {
        this.nim = nim;
        this.email = email;
        this.nmdepan = nmdepan;
        this.nmbelakang = nmbelakang;
        this.jk = jk;
        this.idprodi = idprodi;
        this.password = password;
    }

    public String getNim() {
        return nim;
    }

    public String getEmail() {
        return email;
    }

    public String getNmdepan() {
        return nmdepan;
    }

    public String getNmbelakang() {
        return nmbelakang;
    }

    public String getJk() {
        return jk;
    }

    public String getIdprodi() {
        return idprodi;
    }

    public String getPassword() {
        return password;
    }
}