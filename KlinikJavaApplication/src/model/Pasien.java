package model;

import java.util.Date;

public class Pasien {
    private int id;
    private String idPasien;
    private String nama;
    private String alamat;
    private String jenisKelamin;
    private Date tanggalLahir;
    private String noTelepon;
    private String keluhan;
    private int idPoli;
    private String idDokter;
    
    // Constructors
    public Pasien() {}
    
    public Pasien(String idPasien, String nama, String alamat, String jenisKelamin, 
                  Date tanggalLahir, String noTelepon, String keluhan, int idPoli, String idDokter) {
        this.idPasien = idPasien;
        this.nama = nama;
        this.alamat = alamat;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
        this.noTelepon = noTelepon;
        this.keluhan = keluhan;
        this.idPoli = idPoli;
        this.idDokter = idDokter;
    }
    
    public Pasien(int id, String idPasien, String nama, String alamat, String jenisKelamin, 
                  Date tanggalLahir, String noTelepon, String keluhan, int idPoli, String idDokter) {
        this.id = id;
        this.idPasien = idPasien;
        this.nama = nama;
        this.alamat = alamat;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
        this.noTelepon = noTelepon;
        this.keluhan = keluhan;
        this.idPoli = idPoli;
        this.idDokter = idDokter;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getIdPasien() { return idPasien; }
    public void setIdPasien(String idPasien) { this.idPasien = idPasien; }
    
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    
    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    
    public Date getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(Date tanggalLahir) { this.tanggalLahir = tanggalLahir; }
    
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
    
    public String getKeluhan() { return keluhan; }
    public void setKeluhan(String keluhan) { this.keluhan = keluhan; }
    
    public int getIdPoli() { return idPoli; }
    public void setIdPoli(int idPoli) { this.idPoli = idPoli; }
    
    public String getIdDokter() { return idDokter; }
    public void setIdDokter(String idDokter) { this.idDokter = idDokter; }
}