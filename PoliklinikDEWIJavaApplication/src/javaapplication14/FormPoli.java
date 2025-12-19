package javaapplication14;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dwi
 */
public class FormPoli extends javax.swing.JFrame {
    Connection conn;
    DefaultTableModel model;
    
    public FormPoli() {
        initComponents();
        koneksiDB();
        tampilData();
        newidpoli();
    }
    
    private void koneksiDB() {
        try {
            String url = "jdbc:mysql://localhost:3306/poliklinik"; // Ganti dengan nama database Anda
            String user = "root"; // Ganti dengan username MySQL
            String pass = ""; // Ganti dengan password MySQL
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Koneksi berhasil");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
        }
    }
    
    private void newidpoli() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id_poli) FROM table_poli");
            
            if (rs.next()) {
                String lastid = rs.getString(1);
                if (lastid != null) {
                    int newId = Integer.parseInt(lastid) + 1;
                    txtIDPoli.setText(String.valueOf(newId));
                } else {
                    txtIDPoli.setText("1");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error generate ID: " + e.getMessage());
            txtIDPoli.setText("");
        }
    }
    
    private void tampilData() {
        model = new DefaultTableModel();
        model.addColumn("ID Poli");
        model.addColumn("Nama Poli");
        model.addColumn("Keterangan");
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM table_poli");
            
            while (rs.next()) {
                Object[] data = {
                    rs.getString("id_poli"),
                    rs.getString("nama_poli"),
                    rs.getString("keterangan")
                };
                model.addRow(data);
            }
            
            tbPoli.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error tampil data: " + e.getMessage());
        }
    }
    
    private void simpanData() {
        try {
            // Validasi input
            if (txtNamaPoli.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama Poli harus diisi!");
                return;
            }
            
            String query = "INSERT INTO table_poli (id_poli, nama_poli, keterangan) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            
            pst.setString(1, txtIDPoli.getText());
            pst.setString(2, txtNamaPoli.getText());
            pst.setString(3, txtKeterangan.getText());
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            
            // Reset form dan refresh data
            clearForm();
            tampilData();
            newidpoli();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error simpan data: " + e.getMessage());
        }
    }
    
    private void editData() {
        try {
            // Validasi input
            if (txtIDPoli.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih data yang akan diedit!");
                return;
            }
            
            if (txtNamaPoli.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama Poli harus diisi!");
                return;
            }
            
            String query = "UPDATE table_poli SET nama_poli = ?, keterangan = ? WHERE id_poli = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            
            pst.setString(1, txtNamaPoli.getText());
            pst.setString(2, txtKeterangan.getText());
            pst.setString(3, txtIDPoli.getText());
            
            int updated = pst.executeUpdate();
            
            if (updated > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil diedit!");
                
                // Reset form dan refresh data
                clearForm();
                tampilData();
                newidpoli();
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
            if (txtIDPoli.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus!");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(null, 
                "Yakin ingin menghapus data ini?", 
                "Konfirmasi", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM table_poli WHERE id_poli = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                
                pst.setString(1, txtIDPoli.getText());
                
                int deleted = pst.executeUpdate();
                
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                    
                    // Reset form dan refresh data
                    clearForm();
                    tampilData();
                    newidpoli();
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error hapus data: " + e.getMessage());
        }
    }
    
    private void clearForm() {
        txtNamaPoli.setText("");
        txtKeterangan.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtIDPoli = new javax.swing.JTextField();
        txtNamaPoli = new javax.swing.JTextField();
        txtKeterangan = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPoli = new javax.swing.JTable();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Form poli");

        jLabel2.setText("ID Poli");

        jLabel3.setText("Nama poli");

        jLabel4.setText("Keterangan");

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        tbPoli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "nama_poli", "keterangan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbPoli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPoliMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPoli);

        btnClear.setText("Reset");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIDPoli, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaPoli, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(460, 460, 460))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIDPoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnEdit))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNamaPoli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSimpan))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(btnHapus)))
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(btnClear)
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(192, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editData();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbPoliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPoliMouseClicked
        // Ambil data dari tabel yang diklik
        int row = tbPoli.getSelectedRow();
        if (row >= 0) {
            txtIDPoli.setText(model.getValueAt(row, 0).toString());
            txtNamaPoli.setText(model.getValueAt(row, 1).toString());
            txtKeterangan.setText(model.getValueAt(row, 2).toString());
        }
    }//GEN-LAST:event_tbPoliMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpanData();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
        newidpoli();
    }//GEN-LAST:event_btnClearActionPerformed

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
            java.util.logging.Logger.getLogger(FormPoli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPoli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPoli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPoli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPoli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbPoli;
    private javax.swing.JTextField txtIDPoli;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtNamaPoli;
    // End of variables declaration//GEN-END:variables
}
