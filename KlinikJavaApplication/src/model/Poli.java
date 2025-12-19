package model;

public class Poli {
    private int id;
    private String namaPoli;
    private String keterangan;
    
    // Constructors
    public Poli() {}
    
    public Poli(String namaPoli, String keterangan) {
        this.namaPoli = namaPoli;
        this.keterangan = keterangan;
    }
    
    public Poli(int id, String namaPoli, String keterangan) {
        this.id = id;
        this.namaPoli = namaPoli;
        this.keterangan = keterangan;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNamaPoli() { return namaPoli; }
    public void setNamaPoli(String namaPoli) { this.namaPoli = namaPoli; }
    
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
    
    @Override
    public String toString() {
        return namaPoli;
    }
}