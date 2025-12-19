package controller;

import model.*;
import view.PoliklinikView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PoliklinikController {
    private PoliklinikView view;
    private PoliDAO poliDAO;
    private DefaultTableModel tableModel;
    private static final Logger logger = Logger.getLogger(PoliklinikController.class.getName());
    
    private boolean isEditMode = false;
    private int currentEditingId = -1;
    
    public PoliklinikController(PoliklinikView view) {
        this.view = view;
        this.poliDAO = new PoliDAO();
        this.tableModel = (DefaultTableModel) view.getTblPoli().getModel();
        
        initController();
        loadDataPoli();
    }
    
    private void initController() {
        // Add action listeners
        view.getBtnSave().addActionListener(e -> savePoli());
        view.getBtnEdit().addActionListener(e -> updatePoli());
        view.getBtnDelete().addActionListener(e -> deletePoli());
        view.getBtnSearch().addActionListener(e -> searchPoli());
        
        // Add clear button listener
        view.getBtnClear().addActionListener(e -> clearForm()); 
        
        // Add mouse listener for table selection
        view.getTblPoli().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked();
            }
        });
        
        // Add key listener for Enter key in search field
        view.getTxtPencarian().addActionListener(e -> searchPoli());
        
        resetFormState();
    }
    
    private void loadDataPoli() {
        try {
            List<Poli> listPoli = poliDAO.getAllPoli();
            tableModel.setRowCount(0);
            
            for (Poli poli : listPoli) {
                tableModel.addRow(new Object[]{
                    poli.getNamaPoli(),
                    poli.getKeterangan()
                });
            }
            logger.info("Loaded " + listPoli.size() + " poli data to table");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error loading poli data", ex);
            JOptionPane.showMessageDialog(view, 
                "Error loading data: " + ex.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void savePoli() {
        try {
            if (!validateInput()) return;
            
            Poli poli = new Poli(
                view.getTxtNamaPoli().getText().trim(),
                view.getTxtKeterangan().getText().trim()
            );
            
            if (poliDAO.addPoli(poli)) {
                JOptionPane.showMessageDialog(view, 
                    "Data poliklinik berhasil disimpan", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadDataPoli();
                resetFormState();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Gagal menyimpan data poliklinik", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error saving poli", ex);
            JOptionPane.showMessageDialog(view, 
                "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updatePoli() {
        try {
            int selectedRow = view.getTblPoli().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, 
                    "Pilih data poliklinik yang akan diupdate", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (!validateInput()) return;
            
            if (currentEditingId == -1) {
                JOptionPane.showMessageDialog(view, 
                    "Tidak dapat menemukan ID data yang akan diupdate", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Poli poli = new Poli(
                currentEditingId,
                view.getTxtNamaPoli().getText().trim(),
                view.getTxtKeterangan().getText().trim()
            );
            
            if (poliDAO.updatePoli(poli)) {
                JOptionPane.showMessageDialog(view, 
                    "Data poliklinik berhasil diupdate", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadDataPoli();
                resetFormState();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Gagal mengupdate data poliklinik", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error updating poli", ex);
            JOptionPane.showMessageDialog(view, 
                "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deletePoli() {
        try {
            int selectedRow = view.getTblPoli().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, 
                    "Pilih data poliklinik yang akan dihapus", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (currentEditingId == -1) {
                JOptionPane.showMessageDialog(view, 
                    "Tidak dapat menemukan ID data yang akan dihapus", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String namaPoli = view.getTxtNamaPoli().getText().trim();
            
            int confirm = JOptionPane.showConfirmDialog(view, 
                "Apakah Anda yakin ingin menghapus data poliklinik:\n" + 
                "Nama: " + namaPoli + "?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (poliDAO.deletePoli(currentEditingId)) {
                    JOptionPane.showMessageDialog(view, 
                        "Data poliklinik berhasil dihapus", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadDataPoli();
                    resetFormState();
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "Gagal menghapus data poliklinik", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error deleting poli", ex);
            JOptionPane.showMessageDialog(view, 
                "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void searchPoli() {
        try {
            String keyword = view.getTxtPencarian().getText().trim();
            List<Poli> listPoli;
            
            if (keyword.isEmpty()) {
                listPoli = poliDAO.getAllPoli();
            } else {
                listPoli = poliDAO.searchPoli(keyword);
            }
            
            tableModel.setRowCount(0);
            for (Poli poli : listPoli) {
                tableModel.addRow(new Object[]{
                    poli.getNamaPoli(),
                    poli.getKeterangan()
                });
            }
            
            if (listPoli.isEmpty() && !keyword.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Tidak ditemukan data dengan kata kunci: " + keyword, 
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error searching poli", ex);
            JOptionPane.showMessageDialog(view, 
                "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void tableMouseClicked() {
        try {
            int selectedRow = view.getTblPoli().getSelectedRow();
            if (selectedRow != -1) {
                String namaPoli = (String) tableModel.getValueAt(selectedRow, 0);
                String keterangan = (String) tableModel.getValueAt(selectedRow, 1);
                
                view.getTxtNamaPoli().setText(namaPoli);
                view.getTxtKeterangan().setText(keterangan);
                
                // Cari ID berdasarkan nama poli (karena tidak ditampilkan di tabel)
                List<Poli> allPoli = poliDAO.getAllPoli();
                for (Poli poli : allPoli) {
                    if (poli.getNamaPoli().equals(namaPoli)) {
                        currentEditingId = poli.getId();
                        break;
                    }
                }
                
                setButtonState(false, true, true);
                isEditMode = true;
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error handling table click", ex);
        }
    }
    
    private boolean validateInput() {
        // Validate Nama Poli
        if (view.getTxtNamaPoli().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Nama poliklinik harus diisi", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            view.getTxtNamaPoli().requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        view.getTxtNamaPoli().setText("");
        view.getTxtKeterangan().setText("");
        view.getTblPoli().clearSelection();
    }
    
    private void resetFormState() {
        clearForm();
        setButtonState(true, false, false);
        isEditMode = false;
        currentEditingId = -1;
        view.getTxtPencarian().setText("");
    }
    
    private void setButtonState(boolean save, boolean edit, boolean delete) {
        view.getBtnSave().setEnabled(save);
        view.getBtnEdit().setEnabled(edit);
        view.getBtnDelete().setEnabled(delete);
    }
    
    public void refreshData() {
        loadDataPoli();
        resetFormState();
    }
}
