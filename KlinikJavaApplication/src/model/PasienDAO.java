package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasienDAO {
    private static final Logger logger = Logger.getLogger(PasienDAO.class.getName());
    
    // Generate ID Pasien otomatis (PS001, PS002, dst)
    public String generateIdPasien() {
        String newId = "PS001";
        String query = "SELECT id_pasien FROM pasien ORDER BY id_pasien DESC LIMIT 1";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            logger.severe("Database connection is null - cannot generate ID");
            return newId;
        }
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                String lastId = rs.getString("id_pasien");
                int number = Integer.parseInt(lastId.substring(2)) + 1;
                newId = String.format("PS%03d", number);
                logger.info("Generated new ID: " + newId);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error generating ID Pasien", ex);
        } catch (NumberFormatException ex) {
            logger.log(Level.SEVERE, "Error parsing ID Pasien", ex);
        }
        return newId;
    }
    
    // READ - Get all pasien dengan join ke poli dan dokter
    public List<Pasien> getAllPasien() {
        List<Pasien> listPasien = new ArrayList<>();
        String query = """
            SELECT p.*, poli.nama_poli, dokter.nama_dokter 
            FROM pasien p 
            LEFT JOIN poli ON p.id_poli = poli.id 
            LEFT JOIN dokter ON p.id_dokter = dokter.id_dokter 
            ORDER BY p.id_pasien
            """;
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            logger.severe("Cannot get database connection");
            return listPasien;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Pasien pasien = new Pasien(
                    rs.getInt("id"),
                    rs.getString("id_pasien"),
                    rs.getString("nama"),
                    rs.getString("alamat"),
                    rs.getString("jenis_kelamin"),
                    rs.getDate("tanggal_lahir"),
                    rs.getString("no_telepon"),
                    rs.getString("keluhan"),
                    rs.getInt("id_poli"),
                    rs.getString("id_dokter")
                );
                listPasien.add(pasien);
            }
            logger.info("Retrieved " + listPasien.size() + " patients from database");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error getting all pasien", ex);
        }
        return listPasien;
    }
    
    // SEARCH - Cari pasien berdasarkan nama atau keluhan
    public List<Pasien> searchPasien(String keyword) {
        List<Pasien> listPasien = new ArrayList<>();
        String query = """
            SELECT p.*, poli.nama_poli, dokter.nama_dokter 
            FROM pasien p 
            LEFT JOIN poli ON p.id_poli = poli.id 
            LEFT JOIN dokter ON p.id_dokter = dokter.id_dokter 
            WHERE p.nama LIKE ? OR p.keluhan LIKE ? 
            ORDER BY p.id_pasien
            """;
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return listPasien;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Pasien pasien = new Pasien(
                        rs.getInt("id"),
                        rs.getString("id_pasien"),
                        rs.getString("nama"),
                        rs.getString("alamat"),
                        rs.getString("jenis_kelamin"),
                        rs.getDate("tanggal_lahir"),
                        rs.getString("no_telepon"),
                        rs.getString("keluhan"),
                        rs.getInt("id_poli"),
                        rs.getString("id_dokter")
                    );
                    listPasien.add(pasien);
                }
            }
            logger.info("Search found " + listPasien.size() + " patients for keyword: " + keyword);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error searching pasien", ex);
        }
        return listPasien;
    }
    
    // CREATE - Tambah pasien baru
    public boolean addPasien(Pasien pasien) {
        String query = """
            INSERT INTO pasien (id_pasien, nama, alamat, jenis_kelamin, tanggal_lahir, no_telepon, keluhan, id_poli, id_dokter) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return false;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, pasien.getIdPasien());
            pstmt.setString(2, pasien.getNama());
            pstmt.setString(3, pasien.getAlamat());
            pstmt.setString(4, pasien.getJenisKelamin());
            pstmt.setDate(5, new java.sql.Date(pasien.getTanggalLahir().getTime()));
            pstmt.setString(6, pasien.getNoTelepon());
            pstmt.setString(7, pasien.getKeluhan());
            pstmt.setInt(8, pasien.getIdPoli());
            pstmt.setString(9, pasien.getIdDokter());
            
            int rowsAffected = pstmt.executeUpdate();
            boolean success = rowsAffected > 0;
            if (success) {
                logger.info("Successfully added patient: " + pasien.getIdPasien() + " - " + pasien.getNama());
            } else {
                logger.warning("Failed to add patient: " + pasien.getIdPasien());
            }
            return success;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error adding pasien: " + pasien.getIdPasien(), ex);
            return false;
        }
    }
    
    // UPDATE - Update data pasien
    public boolean updatePasien(Pasien pasien) {
        String query = """
            UPDATE pasien SET nama = ?, alamat = ?, jenis_kelamin = ?, tanggal_lahir = ?, 
            no_telepon = ?, keluhan = ?, id_poli = ?, id_dokter = ? 
            WHERE id_pasien = ?
            """;
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return false;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, pasien.getNama());
            pstmt.setString(2, pasien.getAlamat());
            pstmt.setString(3, pasien.getJenisKelamin());
            pstmt.setDate(4, new java.sql.Date(pasien.getTanggalLahir().getTime()));
            pstmt.setString(5, pasien.getNoTelepon());
            pstmt.setString(6, pasien.getKeluhan());
            pstmt.setInt(7, pasien.getIdPoli());
            pstmt.setString(8, pasien.getIdDokter());
            pstmt.setString(9, pasien.getIdPasien());
            
            int rowsAffected = pstmt.executeUpdate();
            boolean success = rowsAffected > 0;
            if (success) {
                logger.info("Successfully updated patient: " + pasien.getIdPasien());
            } else {
                logger.warning("No patient found to update: " + pasien.getIdPasien());
            }
            return success;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error updating pasien: " + pasien.getIdPasien(), ex);
            return false;
        }
    }
    
    // DELETE - Hapus pasien
    public boolean deletePasien(String idPasien) {
        String query = "DELETE FROM pasien WHERE id_pasien = ?";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return false;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, idPasien);
            
            int rowsAffected = pstmt.executeUpdate();
            boolean success = rowsAffected > 0;
            if (success) {
                logger.info("Successfully deleted patient: " + idPasien);
            } else {
                logger.warning("No patient found to delete: " + idPasien);
            }
            return success;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error deleting pasien: " + idPasien, ex);
            return false;
        }
    }
    
    // Get pasien by ID
    public Pasien getPasienById(String idPasien) {
        String query = "SELECT * FROM pasien WHERE id_pasien = ?";
        Pasien pasien = null;
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return null;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, idPasien);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    pasien = new Pasien(
                        rs.getInt("id"),
                        rs.getString("id_pasien"),
                        rs.getString("nama"),
                        rs.getString("alamat"),
                        rs.getString("jenis_kelamin"),
                        rs.getDate("tanggal_lahir"),
                        rs.getString("no_telepon"),
                        rs.getString("keluhan"),
                        rs.getInt("id_poli"),
                        rs.getString("id_dokter")
                    );
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error getting pasien by ID: " + idPasien, ex);
        }
        return pasien;
    }
    
    // Get all poli untuk combo box
    public List<Poli> getAllPoli() {
        List<Poli> listPoli = new ArrayList<>();
        String query = "SELECT * FROM poli ORDER BY nama_poli";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return listPoli;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Poli poli = new Poli(
                    rs.getInt("id"),
                    rs.getString("nama_poli"),
                    rs.getString("keterangan")
                );
                listPoli.add(poli);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error getting all poli", ex);
        }
        return listPoli;
    }
    
    // Get all dokter untuk combo box
    public List<Dokter> getAllDokter() {
        List<Dokter> listDokter = new ArrayList<>();
        String query = "SELECT * FROM dokter ORDER BY nama_dokter";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return listDokter;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Dokter dokter = new Dokter(
                    rs.getInt("id"),
                    rs.getString("id_dokter"),
                    rs.getString("nama_dokter"),
                    rs.getString("spesialis"),
                    rs.getString("alamat"),
                    rs.getString("no_telepon")
                );
                listDokter.add(dokter);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error getting all dokter", ex);
        }
        return listDokter;
    }
}
