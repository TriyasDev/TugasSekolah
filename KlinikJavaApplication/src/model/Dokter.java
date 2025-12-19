package model;

public class Dokter {
    private int id;
    private String idDokter;
    private String namaDokter;
    private String spesialis;
    private String alamat;
    private String noTelepon;
    
    // Constructor - PERBAIKAN: Tambahkan () 
    public Dokter() {}
    
    public Dokter(String idDokter, String namaDokter, String spesialis, String alamat, String noTelepon) {
        this.idDokter = idDokter;
        this.namaDokter = namaDokter;
        this.spesialis = spesialis;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
    }
    
    public Dokter(int id, String idDokter, String namaDokter, String spesialis, String alamat, String noTelepon) {
        this.id = id;
        this.idDokter = idDokter;
        this.namaDokter = namaDokter;
        this.spesialis = spesialis;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getIdDokter() { return idDokter; }
    public void setIdDokter(String idDokter) { this.idDokter = idDokter; }
    
    public String getNamaDokter() { return namaDokter; }
    public void setNamaDokter(String namaDokter) { this.namaDokter = namaDokter; }
    
    public String getSpesialis() { return spesialis; }
    public void setSpesialis(String spesialis) { this.spesialis = spesialis; }
    
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
}