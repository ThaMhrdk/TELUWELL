/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

/**
 * @author THA
 */

public class Prodi {
    private String idprodi;
    private String nmprodi;
    private String jenjang;
    private String kdprodi;
    private String idfakultas;
    
    public Prodi() {
    }

    public Prodi(String idprodi, String nmprodi) {
        this.idprodi = idprodi;
        this.nmprodi = nmprodi;
    }
    
    public Prodi(String idprodi, String nmprodi, String jenjang, String kdprodi, String idfakultas) {
        this.idprodi = idprodi;
        this.nmprodi = nmprodi;
        this.jenjang = jenjang;
        this.kdprodi = kdprodi;
        this.idfakultas = idfakultas;
    }
    
    // Getter dan Setter
    public String getIdprodi() {
        return idprodi;
    }
    
    public void setIdprodi(String idprodi) {
        this.idprodi = idprodi;
    }
    
    public String getNmprodi() {
        return nmprodi;
    }
    
    public void setNmprodi(String nmprodi) {
        this.nmprodi = nmprodi;
    }
    
    public String getJenjang() {
        return jenjang;
    }
    
    public void setJenjang(String jenjang) {
        this.jenjang = jenjang;
    }
    
    public String getKdprodi() {
        return kdprodi;
    }
    
    public void setKdprodi(String kdprodi) {
        this.kdprodi = kdprodi;
    }
    
    public String getIdfakultas() {
        return idfakultas;
    }
    
    public void setIdfakultas(String idfakultas) {
        this.idfakultas = idfakultas;
    }
    
    @Override
    public String toString() {
        return this.nmprodi;
    }
}