package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PoliDAO {
    private static final Logger logger = Logger.getLogger(PoliDAO.class.getName());
    
    // CREATE - Tambah poli baru
    public boolean addPoli(Poli poli) {
        String query = "INSERT INTO poli (nama_poli, keterangan) VALUES (?, ?)";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return false;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, poli.getNamaPoli());
            pstmt.setString(2, poli.getKeterangan());
            
            int rowsAffected = pstmt.executeUpdate();
            boolean success = rowsAffected > 0;
            if (success) {
                logger.info("Successfully added poli: " + poli.getNamaPoli());
            }
            return success;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error adding poli: " + poli.getNamaPoli(), ex);
            return false;
        }
    }
    
    // READ - Get all poli
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
    
    // UPDATE - Update data poli
    public boolean updatePoli(Poli poli) {
        String query = "UPDATE poli SET nama_poli = ?, keterangan = ? WHERE id = ?";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return false;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, poli.getNamaPoli());
            pstmt.setString(2, poli.getKeterangan());
            pstmt.setInt(3, poli.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            boolean success = rowsAffected > 0;
            if (success) {
                logger.info("Successfully updated poli: " + poli.getNamaPoli());
            }
            return success;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error updating poli: " + poli.getNamaPoli(), ex);
            return false;
        }
    }
    
    // DELETE - Hapus poli
    public boolean deletePoli(int id) {
        String query = "DELETE FROM poli WHERE id = ?";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return false;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            boolean success = rowsAffected > 0;
            if (success) {
                logger.info("Successfully deleted poli with ID: " + id);
            }
            return success;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error deleting poli with ID: " + id, ex);
            return false;
        }
    }
    
    // SEARCH - Cari poli berdasarkan nama
    public List<Poli> searchPoli(String keyword) {
        List<Poli> listPoli = new ArrayList<>();
        String query = "SELECT * FROM poli WHERE nama_poli LIKE ? OR keterangan LIKE ? ORDER BY nama_poli";
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return listPoli;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Poli poli = new Poli(
                        rs.getInt("id"),
                        rs.getString("nama_poli"),
                        rs.getString("keterangan")
                    );
                    listPoli.add(poli);
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error searching poli", ex);
        }
        return listPoli;
    }
    
    // Get poli by ID
    public Poli getPoliById(int id) {
        String query = "SELECT * FROM poli WHERE id = ?";
        Poli poli = null;
        
        Connection conn = DBkoneksi.getConnection();
        if (conn == null) {
            return null;
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    poli = new Poli(
                        rs.getInt("id"),
                        rs.getString("nama_poli"),
                        rs.getString("keterangan")
                    );
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error getting poli by ID: " + id, ex);
        }
        return poli;
    }
}