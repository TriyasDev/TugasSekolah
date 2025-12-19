package controller;

import model.Dokter;
import model.DokterDAO;
import view.FormDokterView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DokterController {
    private FormDokterView view;
    private DokterDAO dokterDAO;
    private DefaultTableModel tableModel;
    private static final Logger logger = Logger.getLogger(DokterController.class.getName());
    
    // State management
    private boolean isEditMode = false;
    private String currentEditingId = null;
    
    public DokterController(FormDokterView view) {
        this.view = view;
        this.dokterDAO = new DokterDAO();
        this.tableModel = (DefaultTableModel) view.getJTable1().getModel();
        initController();
        loadDataDokter();
    }
    
    private void initController() {
        // Add action listeners
        view.getSave().addActionListener(e -> saveDokter());
        view.getEdit().addActionListener(e -> updateDokter());
        view.getDelete().addActionListener(e -> deleteDokter());
        view.getSearch().addActionListener(e -> searchDokter());
        
        // Add clear button listener
        view.getBtnClear().addActionListener(e -> clearForm());
        
        // Add mouse listener for table selection
        view.getJTable1().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked();
            }
        });
        
        // Add key listener for Enter key in search field
        view.getFieldSearch().addActionListener(e -> searchDokter());
        
        // Set initial state
        resetFormState();
    }
    
    private void loadDataDokter() {
        SwingWorker<List<Dokter>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Dokter> doInBackground() throws Exception {
                return dokterDAO.getAllDokter();
            }
            
            @Override
            protected void done() {
                try {
                    List<Dokter> listDokter = get();
                    tableModel.setRowCount(0);
                    
                    for (Dokter dokter : listDokter) {
                        tableModel.addRow(new Object[]{
                            dokter.getId(),
                            dokter.getIdDokter(),
                            dokter.getNamaDokter(),
                            dokter.getSpesialis(),
                            dokter.getAlamat(),
                            dokter.getNoTelepon()
                        });
                    }
                    logger.info("Loaded " + listDokter.size() + " doctors to table");
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error loading dokter data", ex);
                    JOptionPane.showMessageDialog(view, 
                        "Error loading data: " + ex.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }
    
    private void saveDokter() {
        try {
            // Validate input
            if (!validateInput()) return;
            
            // Generate new ID
            String newId = dokterDAO.generateIdDokter();
            
            // Create new Dokter object
            Dokter dokter = new Dokter(
                newId,
                view.getNamaDokter().getText().trim(),
                view.getSpesialis().getText().trim(),
                view.getAlamat().getText().trim(),
                view.getNoTelepon().getText().trim()
            );
            
            // Save to database
            if (dokterDAO.addDokter(dokter)) {
                JOptionPane.showMessageDialog(view, 
                    "Data dokter berhasil disimpan\nID Dokter: " + newId, 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadDataDokter();
                resetFormState();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Gagal menyimpan data dokter", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error saving dokter", ex);
            JOptionPane.showMessageDialog(view, 
                "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateDokter() {
        try {
            int selectedRow = view.getJTable1().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, 
                    "Pilih data dokter yang akan diupdate", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validate input
            if (!validateInput()) return;
            
            String idDokter = (String) tableModel.getValueAt(selectedRow, 1);
            
            // Create updated Dokter object
            Dokter dokter = new Dokter(
                idDokter,
                view.getNamaDokter().getText().trim(),
                view.getSpesialis().getText().trim(),
                view.getAlamat().getText().trim(),
                view.getNoTelepon().getText().trim()
            );
            
            // Update in database
            if (dokterDAO.updateDokter(dokter)) {
                JOptionPane.showMessageDialog(view, 
                    "Data dokter berhasil diupdate", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadDataDokter();
                resetFormState();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Gagal mengupdate data dokter", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error updating dokter", ex);
            JOptionPane.showMessageDialog(view, 
                "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteDokter() {
        try {
            int selectedRow = view.getJTable1().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, 
                    "Pilih data dokter yang akan dihapus", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String idDokter = (String) tableModel.getValueAt(selectedRow, 1);
            String namaDokter = (String) tableModel.getValueAt(selectedRow, 2);
            
            int confirm = JOptionPane.showConfirmDialog(view, 
                "Apakah Anda yakin ingin menghapus data dokter:\n" + 
                "ID: " + idDokter + "\n" +
                "Nama: " + namaDokter + "?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (dokterDAO.deleteDokter(idDokter)) {
                    JOptionPane.showMessageDialog(view, 
                        "Data dokter berhasil dihapus", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadDataDokter();
                    resetFormState();
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "Gagal menghapus data dokter", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error deleting dokter", ex);
            JOptionPane.showMessageDialog(view, 
                "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void searchDokter() {
        try {
            String keyword = view.getFieldSearch().getText().trim();
            List<Dokter> listDokter;
            
            if (keyword.isEmpty()) {
                listDokter = dokterDAO.getAllDokter();
            } else {
                listDokter = dokterDAO.searchDokter(keyword);
            }
            
            tableModel.setRowCount(0);
            for (Dokter dokter : listDokter) {
                tableModel.addRow(new Object[]{
                    dokter.getId(),
                    dokter.getIdDokter(),
                    dokter.getNamaDokter(),
                    dokter.getSpesialis(),
                    dokter.getAlamat(),
                    dokter.getNoTelepon()
                });
            }
            
            if (listDokter.isEmpty() && !keyword.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Tidak ditemukan data dengan kata kunci: " + keyword, 
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error searching dokter", ex);
            JOptionPane.showMessageDialog(view, 
                "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void tableMouseClicked() {
        try {
            int selectedRow = view.getJTable1().getSelectedRow();
            if (selectedRow != -1) {
                view.getNamaDokter().setText((String) tableModel.getValueAt(selectedRow, 2));
                view.getSpesialis().setText((String) tableModel.getValueAt(selectedRow, 3));
                view.getAlamat().setText((String) tableModel.getValueAt(selectedRow, 4));
                view.getNoTelepon().setText((String) tableModel.getValueAt(selectedRow, 5));
                
                setButtonState(false, true, true);
                isEditMode = true;
                currentEditingId = (String) tableModel.getValueAt(selectedRow, 1);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error handling table click", ex);
        }
    }
    
    private boolean validateInput() {
        // Validate Nama Dokter
        if (view.getNamaDokter().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Nama dokter harus diisi", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            view.getNamaDokter().requestFocus();
            return false;
        }
        
        // Validate Spesialis
        if (view.getSpesialis().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Spesialis harus diisi", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            view.getSpesialis().requestFocus();
            return false;
        }
        
        // Validate No Telepon (basic validation)
        String noTelepon = view.getNoTelepon().getText().trim();
        if (!noTelepon.isEmpty() && !noTelepon.matches("^[0-9+\\-\\s()]{10,15}$")) {
            JOptionPane.showMessageDialog(view, 
                "Format nomor telepon tidak valid", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            view.getNoTelepon().requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        view.getNamaDokter().setText("");
        view.getSpesialis().setText("");
        view.getAlamat().setText("");
        view.getNoTelepon().setText("");
        view.getJTable1().clearSelection();
    }
    
    private void resetFormState() {
        clearForm();
        setButtonState(true, false, false);
        isEditMode = false;
        currentEditingId = null;
        view.getFieldSearch().setText("");
    }
    
    private void setButtonState(boolean save, boolean edit, boolean delete) {
        view.getSave().setEnabled(save);
        view.getEdit().setEnabled(edit);
        view.getDelete().setEnabled(delete);
    }
    
    // Public method untuk refresh data dari luar
    public void refreshData() {
        loadDataDokter();
        resetFormState();
    }
}