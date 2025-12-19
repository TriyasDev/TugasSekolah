package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DokterDAO {
    private static final Logger logger = Logger.getLogger(DokterDAO.class.getName());
    
    // Generate ID Dokter otomatis (DR001, DR002, dst)
    public String generateIdDokter() {
        String newId = "DR001";
        String query = "SELECT id_dokter FROM dokter ORDER BY id_dokter DESC LIMIT 1";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            logger.severe("Database connection is null - cannot generate ID");
            return newId;
        }
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                String lastId = rs.getString("id_dokter");
                int number = Integer.parseInt(lastId.substring(2)) + 1;
                newId = String.format("DR%03d", number);
                logger.info("Generated new ID: " + newId);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error generating ID Dokter", ex);
        } catch (NumberFormatException ex) {
            logger.log(Level.SEVERE, "Error parsing ID Dokter", ex);
        }
        return newId;
    }
    
    // READ - Get all dokter
    public List<Dokter> getAllDokter() {
        List<Dokter> listDokter = new ArrayList<>();
        String query = "SELECT * FROM dokter ORDER BY id_dokter";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            logger.severe("Cannot get database connection");
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
            logger.info("Retrieved " + listDokter.size() + " doctors from database");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error getting all dokter", ex);
        }
        return listDokter;
    }
    
    // SEARCH - Cari dokter berdasarkan nama atau spesialis
    public List<Dokter> searchDokter(String keyword) {
        List<Dokter> listDokter = new ArrayList<>();
        String query = "SELECT * FROM dokter WHERE nama_dokter LIKE ? OR spesialis LIKE ? ORDER BY id_dokter";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return listDokter;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
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
            }
            logger.info("Search found " + listDokter.size() + " doctors for keyword: " + keyword);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error searching dokter", ex);
        }
        return listDokter;
    }
    
    // CREATE - Tambah dokter baru
    public boolean addDokter(Dokter dokter) {
        String query = "INSERT INTO dokter (id_dokter, nama_dokter, spesialis, alamat, no_telepon) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return false;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, dokter.getIdDokter());
            pstmt.setString(2, dokter.getNamaDokter());
            pstmt.setString(3, dokter.getSpesialis());
            pstmt.setString(4, dokter.getAlamat());
            pstmt.setString(5, dokter.getNoTelepon());
            
            int rowsAffected = pstmt.executeUpdate();
            boolean success = rowsAffected > 0;
            if (success) {
                logger.info("Successfully added doctor: " + dokter.getIdDokter() + " - " + dokter.getNamaDokter());
            } else {
                logger.warning("Failed to add doctor: " + dokter.getIdDokter());
            }
            return success;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error adding dokter: " + dokter.getIdDokter(), ex);
            return false;
        }
    }
    
    // UPDATE - Update data dokter
    public boolean updateDokter(Dokter dokter) {
        String query = "UPDATE dokter SET nama_dokter = ?, spesialis = ?, alamat = ?, no_telepon = ? WHERE id_dokter = ?";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return false;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, dokter.getNamaDokter());
            pstmt.setString(2, dokter.getSpesialis());
            pstmt.setString(3, dokter.getAlamat());
            pstmt.setString(4, dokter.getNoTelepon());
            pstmt.setString(5, dokter.getIdDokter());
            
            int rowsAffected = pstmt.executeUpdate();
            boolean success = rowsAffected > 0;
            if (success) {
                logger.info("Successfully updated doctor: " + dokter.getIdDokter());
            } else {
                logger.warning("No doctor found to update: " + dokter.getIdDokter());
            }
            return success;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error updating dokter: " + dokter.getIdDokter(), ex);
            return false;
        }
    }
    
    // DELETE - Hapus dokter
    public boolean deleteDokter(String idDokter) {
        String query = "DELETE FROM dokter WHERE id_dokter = ?";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return false;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, idDokter);
            
            int rowsAffected = pstmt.executeUpdate();
            boolean success = rowsAffected > 0;
            if (success) {
                logger.info("Successfully deleted doctor: " + idDokter);
            } else {
                logger.warning("No doctor found to delete: " + idDokter);
            }
            return success;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error deleting dokter: " + idDokter, ex);
            return false;
        }
    }
    
    // Get dokter by ID
    public Dokter getDokterById(String idDokter) {
        String query = "SELECT * FROM dokter WHERE id_dokter = ?";
        Dokter dokter = null;
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return null;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, idDokter);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dokter = new Dokter(
                        rs.getInt("id"),
                        rs.getString("id_dokter"),
                        rs.getString("nama_dokter"),
                        rs.getString("spesialis"),
                        rs.getString("alamat"),
                        rs.getString("no_telepon")
                    );
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error getting dokter by ID: " + idDokter, ex);
        }
        return dokter;
    }
    
    // Check if id_dokter already exists
    public boolean isIdDokterExists(String idDokter) {
        String query = "SELECT COUNT(*) FROM dokter WHERE id_dokter = ?";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return false;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, idDokter);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error checking ID existence: " + idDokter, ex);
        }
        return false;
    }
}