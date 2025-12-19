package controller;

import model.*;
import view.PasienView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasienController {
    private PasienView view;
    private PasienDAO pasienDAO;
    private DefaultTableModel tableModel;
    private static final Logger logger = Logger.getLogger(PasienController.class.getName());
    private boolean isEditMode = false;
    private String currentEditingId = null;
    private List<Poli> listPoli;
    private List<Dokter> listDokter;
    
    public PasienController(PasienView view) {
        this.view = view;
        this.pasienDAO = new PasienDAO();
        this.tableModel = (DefaultTableModel) view.getJTable1().getModel();
        
        initializeJenisKelamin(); // Pindahkan inisialisasi ke sini
        initController();
        loadComboBoxData();
        loadDataPasien();
    }
    
    private void initializeJenisKelamin() {
        // Tidak perlu inisialisasi lagi karena sudah di-set di PasienView
        // Method ini bisa dihapus atau digunakan untuk validasi
        JComboBox<String> jenisKelamin = view.getJenisKelamin();
        if (jenisKelamin.getItemCount() == 0) {
            jenisKelamin.addItem("Laki-laki");
            jenisKelamin.addItem("Perempuan");
        }
    }
    
    private void initController() {
        // Add action listeners
        view.getSave().addActionListener(e -> savePasien());
        view.getEdit().addActionListener(e -> updatePasien());
        view.getDelete().addActionListener(e -> deletePasien());
        view.getSearch().addActionListener(e -> searchPasien());
        
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
        view.getPencarian().addActionListener(e -> searchPasien());
        
        resetFormState();
    }
    
private void loadComboBoxData() {
    // Load data poli ke combo box sebagai String
    listPoli = pasienDAO.getAllPoli();
    DefaultComboBoxModel<String> poliModel = new DefaultComboBoxModel<>();
    
    for (Poli poli : listPoli) {
        poliModel.addElement(poli.getNamaPoli());
    }
    view.getJComboBoxPoli().setModel(poliModel);
    
    // ✅ TAMBAH: Load data dokter ke combo box
    listDokter = pasienDAO.getAllDokter();
    DefaultComboBoxModel<String> dokterModel = new DefaultComboBoxModel<>();
    
    for (Dokter dokter : listDokter) {
        dokterModel.addElement(dokter.getNamaDokter() + " (" + dokter.getSpesialis() + ")");
    }
    view.getJComboBoxDokter().setModel(dokterModel);
    
    // Validasi jika tidak ada dokter
    if (listDokter.isEmpty()) {
        JOptionPane.showMessageDialog(view, 
            "Peringatan: Tidak ada data dokter di database.\n" +
            "Silakan tambahkan data dokter terlebih dahulu.", 
            "Warning", JOptionPane.WARNING_MESSAGE);
    }
}



private String getSelectedDokterId() {
    int selectedIndex = view.getJComboBoxDokter().getSelectedIndex();
    if (selectedIndex >= 0 && selectedIndex < listDokter.size()) {
        return listDokter.get(selectedIndex).getIdDokter();
    }
    return ""; // Return empty string jika tidak ada pilihan
}

private String getSelectedDokterName() {
    int selectedIndex = view.getJComboBoxDokter().getSelectedIndex();
    if (selectedIndex >= 0 && selectedIndex < listDokter.size()) {
        return listDokter.get(selectedIndex).getNamaDokter();
    }
    return "";
}
    
    // Method untuk mendapatkan ID poli berdasarkan nama yang dipilih
    private int getSelectedPoliId() {
        String selectedPoliName = (String) view.getJComboBoxPoli().getSelectedItem();
        if (selectedPoliName != null && listPoli != null) {
            for (Poli poli : listPoli) {
                if (poli.getNamaPoli().equals(selectedPoliName)) {
                    return poli.getId();
                }
            }
        }
        return 1; // default value jika tidak ditemukan
    }
    
private void loadDataPasien() {
    try {
        List<Pasien> listPasien = pasienDAO.getAllPasien();
        tableModel.setRowCount(0);
        
        for (Pasien pasien : listPasien) {
            // Ambil data dokter untuk pasien ini
            String namaDokter = "Tidak ada dokter";
            
            // Cari nama dokter berdasarkan ID dokter
            if (pasien.getIdDokter() != null && !pasien.getIdDokter().isEmpty()) {
                for (Dokter dokter : listDokter) {
                    if (dokter.getIdDokter().equals(pasien.getIdDokter())) {
                        namaDokter = dokter.getNamaDokter();
                        break;
                    }
                }
            }
            
            // Ambil nama poli
            String namaPoli = "Umum";
            for (Poli poli : listPoli) {
                if (poli.getId() == pasien.getIdPoli()) {
                    namaPoli = poli.getNamaPoli();
                    break;
                }
            }
            
            tableModel.addRow(new Object[]{
                pasien.getId(),
                pasien.getIdPasien(),
                pasien.getNama(),
                pasien.getAlamat(),
                pasien.getJenisKelamin(),
                pasien.getTanggalLahir(),
                pasien.getNoTelepon(),
                pasien.getKeluhan(),
                namaDokter,  // ✅ TAMBAH KOLOM DOKTER
                namaPoli     // ✅ (Opsional) Tambah kolom poli
            });
        }
        logger.info("Loaded " + listPasien.size() + " patients to table");
    } catch (Exception ex) {
        logger.log(Level.SEVERE, "Error loading pasien data", ex);
        JOptionPane.showMessageDialog(view, 
            "Error loading data: " + ex.getMessage(), 
            "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
private void savePasien() {
    try {
        if (!validateInput()) return;
        
        String newId = pasienDAO.generateIdPasien();
        int idPoli = getSelectedPoliId();
        String idDokter = getSelectedDokterId();  // ✅ Ambil dari combo box dokter
        
        // ✅ TAMBAH: Validasi dokter terpilih
        if (idDokter == null || idDokter.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Silakan pilih dokter", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Pasien pasien = new Pasien(
            newId,
            view.getNama().getText().trim(),
            view.getAlamat().getText().trim(),
            (String) view.getJenisKelamin().getSelectedItem(),
            view.getTanggalLahir().getDate(),
            view.getNoTelepon().getText().trim(),
            view.getKeluhan().getText().trim(),
            idPoli,
            idDokter  // ✅ Gunakan ID dokter dari combo box
        );
        
        if (pasienDAO.addPasien(pasien)) {
            JOptionPane.showMessageDialog(view, 
                "Data pasien berhasil disimpan\n" +
                "ID Pasien: " + newId + "\n" +
                "Dokter: " + getSelectedDokterName(),  // ✅ Tampilkan nama dokter
                "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadDataPasien();
            resetFormState();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal menyimpan data pasien", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        logger.log(Level.SEVERE, "Error saving pasien", ex);
        JOptionPane.showMessageDialog(view, 
            "Error: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
private void updatePasien() {
    try {
        int selectedRow = view.getJTable1().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, 
                "Pilih data pasien yang akan diupdate", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateInput()) return;
        
        String idPasien = (String) tableModel.getValueAt(selectedRow, 1);
        int idPoli = getSelectedPoliId();
        String idDokter = getSelectedDokterId();  // ✅ Ambil dari combo box dokter
        
        // ✅ TAMBAH: Validasi dokter terpilih
        if (idDokter == null || idDokter.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Silakan pilih dokter", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Pasien pasien = new Pasien(
            idPasien,
            view.getNama().getText().trim(),
            view.getAlamat().getText().trim(),
            (String) view.getJenisKelamin().getSelectedItem(),
            view.getTanggalLahir().getDate(),
            view.getNoTelepon().getText().trim(),
            view.getKeluhan().getText().trim(),
            idPoli,
            idDokter  // ✅ Gunakan ID dokter dari combo box
        );
        
        if (pasienDAO.updatePasien(pasien)) {
            JOptionPane.showMessageDialog(view, 
                "Data pasien berhasil diupdate\n" +
                "Dokter: " + getSelectedDokterName(),  // ✅ Tampilkan nama dokter
                "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadDataPasien();
            resetFormState();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Gagal mengupdate data pasien", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        logger.log(Level.SEVERE, "Error updating pasien", ex);
        JOptionPane.showMessageDialog(view, 
            "Error: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    
    private void deletePasien() {
        try {
            int selectedRow = view.getJTable1().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, 
                    "Pilih data pasien yang akan dihapus", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String idPasien = (String) tableModel.getValueAt(selectedRow, 1);
            String namaPasien = (String) tableModel.getValueAt(selectedRow, 2);
            
            int confirm = JOptionPane.showConfirmDialog(view, 
                "Apakah Anda yakin ingin menghapus data pasien:\n" + 
                "ID: " + idPasien + "\n" +
                "Nama: " + namaPasien + "?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (pasienDAO.deletePasien(idPasien)) {
                    JOptionPane.showMessageDialog(view, 
                        "Data pasien berhasil dihapus", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadDataPasien();
                    resetFormState();
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "Gagal menghapus data pasien", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error deleting pasien", ex);
            JOptionPane.showMessageDialog(view, 
                "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
private void searchPasien() {
    try {
        String keyword = view.getPencarian().getText().trim();
        List<Pasien> listPasien;
        
        if (keyword.isEmpty()) {
            listPasien = pasienDAO.getAllPasien();
        } else {
            listPasien = pasienDAO.searchPasien(keyword);
        }
        
        tableModel.setRowCount(0);
        for (Pasien pasien : listPasien) {
            // Ambil data dokter untuk pasien ini
            String namaDokter = "Tidak ada dokter";
            
            if (pasien.getIdDokter() != null && !pasien.getIdDokter().isEmpty()) {
                for (Dokter dokter : listDokter) {
                    if (dokter.getIdDokter().equals(pasien.getIdDokter())) {
                        namaDokter = dokter.getNamaDokter();
                        break;
                    }
                }
            }
            
            // Ambil nama poli
            String namaPoli = "Umum";
            for (Poli poli : listPoli) {
                if (poli.getId() == pasien.getIdPoli()) {
                    namaPoli = poli.getNamaPoli();
                    break;
                }
            }
            
            tableModel.addRow(new Object[]{
                pasien.getId(),
                pasien.getIdPasien(),
                pasien.getNama(),
                pasien.getAlamat(),
                pasien.getJenisKelamin(),
                pasien.getTanggalLahir(),
                pasien.getNoTelepon(),
                pasien.getKeluhan(),
                namaDokter,  // ✅ TAMBAH KOLOM DOKTER
                namaPoli     // ✅ (Opsional) Tambah kolom poli
            });
        }
        
        if (listPasien.isEmpty() && !keyword.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Tidak ditemukan data dengan kata kunci: " + keyword, 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception ex) {
        logger.log(Level.SEVERE, "Error searching pasien", ex);
        JOptionPane.showMessageDialog(view, 
            "Error: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
private void tableMouseClicked() {
    try {
        int selectedRow = view.getJTable1().getSelectedRow();
        if (selectedRow != -1) {
            view.getNama().setText((String) tableModel.getValueAt(selectedRow, 2));
            view.getAlamat().setText((String) tableModel.getValueAt(selectedRow, 3));
            
            // Set jenis kelamin di JComboBox
            String jk = (String) tableModel.getValueAt(selectedRow, 4);
            if (jk != null) {
                view.getJenisKelamin().setSelectedItem(jk);
            }
            
            java.util.Date tglLahir = (java.util.Date) tableModel.getValueAt(selectedRow, 5);
            view.getTanggalLahir().setDate(tglLahir);
            
            view.getNoTelepon().setText((String) tableModel.getValueAt(selectedRow, 6));
            view.getKeluhan().setText((String) tableModel.getValueAt(selectedRow, 7));
            
            // ✅ TAMBAH: Ambil data pasien untuk mendapatkan ID dokter
            String idPasien = (String) tableModel.getValueAt(selectedRow, 1);
            Pasien pasien = pasienDAO.getPasienById(idPasien);
            
            if (pasien != null) {
                // Set poli berdasarkan ID poli
                for (int i = 0; i < listPoli.size(); i++) {
                    if (listPoli.get(i).getId() == pasien.getIdPoli()) {
                        view.getJComboBoxPoli().setSelectedIndex(i);
                        break;
                    }
                }
                
                // Set dokter berdasarkan ID dokter
                for (int i = 0; i < listDokter.size(); i++) {
                    if (listDokter.get(i).getIdDokter().equals(pasien.getIdDokter())) {
                        view.getJComboBoxDokter().setSelectedIndex(i);
                        break;
                    }
                }
            }
            
            setButtonState(false, true, true);
            isEditMode = true;
            currentEditingId = idPasien;
        }
    } catch (Exception ex) {
        logger.log(Level.SEVERE, "Error handling table click", ex);
    }
}
    
private boolean validateInput() {
    if (view.getNama().getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(view, 
            "Nama pasien harus diisi", 
            "Validation Error", JOptionPane.WARNING_MESSAGE);
        view.getNama().requestFocus();
        return false;
    }
    
    if (view.getJenisKelamin().getSelectedItem() == null) {
        JOptionPane.showMessageDialog(view, 
            "Jenis kelamin harus dipilih", 
            "Validation Error", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    
    if (view.getTanggalLahir().getDate() == null) {
        JOptionPane.showMessageDialog(view, 
            "Tanggal lahir harus diisi", 
            "Validation Error", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    
    // ✅ TAMBAH: Validasi dokter
    if (view.getJComboBoxDokter().getSelectedItem() == null) {
        JOptionPane.showMessageDialog(view, 
            "Silakan pilih dokter", 
            "Validation Error", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    
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
    view.getNama().setText("");
    view.getAlamat().setText("");
    view.getJenisKelamin().setSelectedIndex(0); // Reset ke index 0
    view.getTanggalLahir().setDate(null);
    view.getNoTelepon().setText("");
    view.getKeluhan().setText("");
    view.getJComboBoxPoli().setSelectedIndex(0);  // ✅ TAMBAH: Reset combo box poli
    view.getJComboBoxDokter().setSelectedIndex(0); // ✅ TAMBAH: Reset combo box dokter
    view.getJTable1().clearSelection();
}
    
    private void resetFormState() {
        clearForm();
        setButtonState(true, false, false);
        isEditMode = false;
        currentEditingId = null;
        view.getPencarian().setText("");
    }
    
    private void setButtonState(boolean save, boolean edit, boolean delete) {
        view.getSave().setEnabled(save);
        view.getEdit().setEnabled(edit);
        view.getDelete().setEnabled(delete);
    }
    
    public void refreshData() {
        loadDataPasien();
        resetFormState();
    }
}