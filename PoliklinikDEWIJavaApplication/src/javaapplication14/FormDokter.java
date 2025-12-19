package javaapplication14;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class FormDokter extends javax.swing.JFrame {
    Connection conn;
    DefaultTableModel model;
    
    /**
     * Creates new form 
     */
    public FormDokter() {
        initComponents();
        koneksiDB();
        tampilData();
        newiddokter();
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
    
    private void newiddokter() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id_dokter) FROM table_dokter");
            
            if (rs.next()) {
                String lastid = rs.getString(1);
                if (lastid != null) {
                    int newId = Integer.parseInt(lastid) + 1;
                    txtIDDokter.setText(String.valueOf(newId));
                } else {
                    txtIDDokter.setText("1");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error generate ID: " + e.getMessage());
            txtIDDokter.setText("");
        }
    }
    
    private void tampilData() {
        model = new DefaultTableModel();
        model.addColumn("ID Dokter");
        model.addColumn("Nama Dokter");
        model.addColumn("Spesialis");
        model.addColumn("No HP");
        model.addColumn("Jadwal Praktek");
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM table_dokter");
            
            while (rs.next()) {
                Object[] data = {
                    rs.getString("id_dokter"),
                    rs.getString("nama_dokter"),
                    rs.getString("spesialis"),
                    rs.getString("no_hp"),
                    rs.getString("jadwal_praktek")
                };
                model.addRow(data);
            }
            
            tbDokter.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error tampil data: " + e.getMessage());
        }
    }
    
    private void simpanData() {
        try {
            // Validasi input
            if (txtNamaDokter.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama Dokter harus diisi!");
                return;
            }
            
            if (txtSpesialis.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Spesialis harus diisi!");
                return;
            }
            
            if (txtJadwalPraktek.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Jadwal Praktek harus diisi!");
                return;
            }
            
            String query = "INSERT INTO table_dokter (id_dokter, nama_dokter, spesialis, no_hp, jadwal_praktek) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            
            pst.setString(1, txtIDDokter.getText());
            pst.setString(2, txtNamaDokter.getText());
            pst.setString(3, txtSpesialis.getText());
            pst.setString(4, txtNoHP.getText());
            pst.setString(5, txtJadwalPraktek.getText());
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            
            // Reset form dan refresh data
            clearForm();
            tampilData();
            newiddokter();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error simpan data: " + e.getMessage());
        }
    }
    
    private void editData() {
        try {
            // Validasi input
            if (txtIDDokter.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih data yang akan diedit!");
                return;
            }
            
            if (txtNamaDokter.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama Dokter harus diisi!");
                return;
            }
            
            if (txtSpesialis.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Spesialis harus diisi!");
                return;
            }
            
            if (txtJadwalPraktek.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Jadwal Praktek harus diisi!");
                return;
            }
            
            String query = "UPDATE table_dokter SET nama_dokter = ?, spesialis = ?, no_hp = ?, jadwal_praktek = ? WHERE id_dokter = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            
            pst.setString(1, txtNamaDokter.getText());
            pst.setString(2, txtSpesialis.getText());
            pst.setString(3, txtNoHP.getText());
            pst.setString(4, txtJadwalPraktek.getText());
            pst.setString(5, txtIDDokter.getText());
            
            int updated = pst.executeUpdate();
            
            if (updated > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil diedit!");
                
                // Reset form dan refresh data
                clearForm();
                tampilData();
                newiddokter();
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
            if (txtIDDokter.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus!");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(null, 
                "Yakin ingin menghapus data ini?", 
                "Konfirmasi", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM table_dokter WHERE id_dokter = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                
                pst.setString(1, txtIDDokter.getText());
                
                int deleted = pst.executeUpdate();
                
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                    
                    // Reset form dan refresh data
                    clearForm();
                    tampilData();
                    newiddokter();
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error hapus data: " + e.getMessage());
        }
    }
    
    private void clearForm() {
        txtNamaDokter.setText("");
        txtSpesialis.setText("");
        txtNoHP.setText("");
        txtJadwalPraktek.setText("");
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
        txtIDDokter = new javax.swing.JTextField();
        txtNamaDokter = new javax.swing.JTextField();
        txtSpesialis = new javax.swing.JTextField();
        txtNoHP = new javax.swing.JTextField();
        txtJadwalPraktek = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDokter = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Form Data Dokter");

        jLabel2.setText("ID dokter");

        jLabel3.setText("Nama dokter");

        jLabel4.setText("Spesialis");

        jLabel5.setText("No hp");

        jLabel6.setText("Jadwal praktek");

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

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

        jLabel7.setText("Tabel Data Dokter");

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama dokter", "Spesialis", "No hp", "Jadwal praktek"
            }
        ));
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDokter);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel2)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addComponent(btnSimpan)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIDDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNamaDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSpesialis, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNoHP, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtJadwalPraktek, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(167, 167, 167)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIDDokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNamaDokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSpesialis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJadwalPraktek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnSimpan)
                    .addComponent(btnHapus)
                    .addComponent(btnReset))
                .addGap(30, 30, 30)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpanData();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editData();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        clearForm();
        newiddokter();
    }//GEN-LAST:event_btnResetActionPerformed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        // Ambil data dari tabel yang diklik
        int row = tbDokter.getSelectedRow();
        if (row >= 0) {
            txtIDDokter.setText(model.getValueAt(row, 0).toString());
            txtNamaDokter.setText(model.getValueAt(row, 1).toString());
            txtSpesialis.setText(model.getValueAt(row, 2).toString());
            txtNoHP.setText(model.getValueAt(row, 3).toString());
            txtJadwalPraktek.setText(model.getValueAt(row, 4).toString());
        }
    }//GEN-LAST:event_tbDokterMouseClicked

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
            java.util.logging.Logger.getLogger(FormDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDokter().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbDokter;
    private javax.swing.JTextField txtIDDokter;
    private javax.swing.JTextField txtJadwalPraktek;
    private javax.swing.JTextField txtNamaDokter;
    private javax.swing.JTextField txtNoHP;
    private javax.swing.JTextField txtSpesialis;
    // End of variables declaration//GEN-END:variables
}
