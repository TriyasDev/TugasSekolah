package javaapplication14;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class FormPasien extends javax.swing.JFrame {
    Connection conn;
    DefaultTableModel model;
    
    /**
     * Creates new form FormPasien
     */
    public FormPasien() {
        initComponents();
        koneksiDB();
        tampilData();
        newidpasien();
    }
    
    private void koneksiDB() {
        try {
            String url = "jdbc:mysql://localhost:3306/poliklinik";
            String user = "root";
            String pass = "";
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Koneksi berhasil");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
        }
    }
    
    private void newidpasien() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id_pasien) FROM table_pasien");
            
            if (rs.next()) {
                String lastid = rs.getString(1);
                if (lastid != null) {
                    int newId = Integer.parseInt(lastid) + 1;
                    txtIDPasien.setText(String.valueOf(newId));
                } else {
                    txtIDPasien.setText("1");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error generate ID: " + e.getMessage());
            txtIDPasien.setText("");
        }
    }
    
    private void tampilData() {
        model = new DefaultTableModel();
        model.addColumn("ID Pasien");
        model.addColumn("Nama");
        model.addColumn("Tanggal Lahir");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Alamat");
        model.addColumn("No HP");
        model.addColumn("NIK");
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM table_pasien");
            
            while (rs.next()) {
                Object[] data = {
                    rs.getString("id_pasien"),
                    rs.getString("nama"),
                    rs.getString("tanggal_lahir"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("alamat"),
                    rs.getString("no_telepon"),
                    rs.getString("nik")
                };
                model.addRow(data);
            }
            
            tbPasien.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error tampil data: " + e.getMessage());
        }
    }
    
    private void simpanData() {
        try {
            // Validasi input
            if (txtNamaPasien.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama Pasien harus diisi!");
                return;
            }
            
            if (txtTanggalLahir.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tanggal Lahir harus diisi!");
                return;
            }
            
            if (txtJenisKelamin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Jenis Kelamin harus diisi!");
                return;
            }
            
            // Validasi format tanggal (YYYY-MM-DD)
            if (!txtTanggalLahir.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(null, "Format tanggal harus YYYY-MM-DD (contoh: 1990-01-15)");
                return;
            }
            
            String query = "INSERT INTO table_pasien (id_pasien, nama, tanggal_lahir, jenis_kelamin, alamat, no_telepon, nik) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            
            pst.setString(1, txtIDPasien.getText());
            pst.setString(2, txtNamaPasien.getText());
            pst.setString(3, txtTanggalLahir.getText());
            pst.setString(4, txtJenisKelamin.getText());
            pst.setString(5, txtAlamat.getText());
            pst.setString(6, txtNoHP.getText());
            pst.setString(7, txtNIK.getText());
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            
            // Reset form dan refresh data
            clearForm();
            tampilData();
            newidpasien();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error simpan data: " + e.getMessage());
        }
    }
    
    private void editData() {
        try {
            // Validasi input
            if (txtIDPasien.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih data yang akan diedit!");
                return;
            }
            
            if (txtNamaPasien.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama Pasien harus diisi!");
                return;
            }
            
            if (txtTanggalLahir.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tanggal Lahir harus diisi!");
                return;
            }
            
            if (txtJenisKelamin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Jenis Kelamin harus diisi!");
                return;
            }
            
            // Validasi format tanggal
            if (!txtTanggalLahir.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(null, "Format tanggal harus YYYY-MM-DD (contoh: 1990-01-15)");
                return;
            }
            
            String query = "UPDATE table_pasien SET nama = ?, tanggal_lahir = ?, jenis_kelamin = ?, alamat = ?, no_telepon = ?, nik = ? WHERE id_pasien = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            
            pst.setString(1, txtNamaPasien.getText());
            pst.setString(2, txtTanggalLahir.getText());
            pst.setString(3, txtJenisKelamin.getText());
            pst.setString(4, txtAlamat.getText());
            pst.setString(5, txtNoHP.getText());
            pst.setString(6, txtNIK.getText());
            pst.setString(7, txtIDPasien.getText());
            
            int updated = pst.executeUpdate();
            
            if (updated > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil diedit!");
                
                // Reset form dan refresh data
                clearForm();
                tampilData();
                newidpasien();
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error edit data: " + e.getMessage());
        }
    }
    
    private void hapusData() {
        try {
            // Validasi
            if (txtIDPasien.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus!");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(null, 
                "Yakin ingin menghapus data ini?", 
                "Konfirmasi", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM table_pasien WHERE id_pasien = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                
                pst.setString(1, txtIDPasien.getText());
                
                int deleted = pst.executeUpdate();
                
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                    
                    // Reset form dan refresh data
                    clearForm();
                    tampilData();
                    newidpasien();
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error hapus data: " + e.getMessage());
        }
    }
    
    private void clearForm() {
        txtNamaPasien.setText("");
        txtTanggalLahir.setText("");
        txtJenisKelamin.setText("");
        txtAlamat.setText("");
        txtNoHP.setText("");
        txtNIK.setText("");
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtIDPasien = new javax.swing.JTextField();
        txtNamaPasien = new javax.swing.JTextField();
        txtTanggalLahir = new javax.swing.JTextField();
        txtJenisKelamin = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        txtNoHP = new javax.swing.JTextField();
        txtNIK = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPasien = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText(" Form Pasien");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("input data pasien");

        jLabel3.setText("ID pasien");

        jLabel4.setText("Nama pasien");

        jLabel5.setText("Tanggal lahir");

        jLabel6.setText("Jenis kelamin");

        jLabel7.setText("Alamat");

        jLabel8.setText("No Telepon");

        jLabel9.setText("NIK");

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        tbPasien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID ", "Nama", "Tgl  lahir", "JK", "No.hp", "NIK", "Aksi"
            }
        ));
        tbPasien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPasienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPasien);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Tabel data pasien");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(jLabel10)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                        .addComponent(txtTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                            .addComponent(txtJenisKelamin, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                            .addComponent(txtNoHP, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                            .addComponent(txtNIK, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                            .addComponent(txtNamaPasien)
                                            .addComponent(txtIDPasien))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(177, 177, 177)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIDPasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNamaPasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNoHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtNIK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(btnSimpan)
                .addGap(42, 42, 42)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(193, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpanData();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        clearForm();
        newidpasien();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editData();
    }//GEN-LAST:event_btnEditActionPerformed

    private void tbPasienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPasienMouseClicked
        // Ambil data dari tabel yang diklik
        int row = tbPasien.getSelectedRow();
        if (row >= 0) {
            txtIDPasien.setText(model.getValueAt(row, 0).toString());
            txtNamaPasien.setText(model.getValueAt(row, 1).toString());
            txtTanggalLahir.setText(model.getValueAt(row, 2).toString());
            txtJenisKelamin.setText(model.getValueAt(row, 3).toString());
            txtAlamat.setText(model.getValueAt(row, 4).toString());
            txtNoHP.setText(model.getValueAt(row, 5).toString());
            txtNIK.setText(model.getValueAt(row, 6).toString());
        }
    }//GEN-LAST:event_tbPasienMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPasien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbPasien;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtIDPasien;
    private javax.swing.JTextField txtJenisKelamin;
    private javax.swing.JTextField txtNIK;
    private javax.swing.JTextField txtNamaPasien;
    private javax.swing.JTextField txtNoHP;
    private javax.swing.JTextField txtTanggalLahir;
    // End of variables declaration//GEN-END:variables
}
