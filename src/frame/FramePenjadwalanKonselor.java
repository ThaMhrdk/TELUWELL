/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import dao.DbJadwal;
import dao.DbKonselor;
import model.Jadwal;
import model.Konselor;
import model.JadwalKons;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionListener;


/**
 *
 * @author THA
 */
public class FramePenjadwalanKonselor extends javax.swing.JFrame {
    private final DbJadwal dbJadwal = new DbJadwal();
    private final DbKonselor dbKonselor = new DbKonselor();
    private DefaultTableModel tableModelJadwalKonselorPenjadwalan;
    private Konselor loggedInKonselor;

    public FramePenjadwalanKonselor() {
        initComponents();
        initializeFrame();
    }
    

    public FramePenjadwalanKonselor(Konselor konselor) {
        this.loggedInKonselor = konselor;
        initComponents();
        initializeFrame();
        
        // Set data konselor yang login
        if (loggedInKonselor != null) {
            jTextFieldIdKonselor.setText(loggedInKonselor.getIdkonselor());
            jTextFieldNamaKonselor.setText(loggedInKonselor.getNmkonselor() + " (" + loggedInKonselor.getSpesialisasi() + ")");
        }
    }

    private void initializeFrame() {
        this.setLocationRelativeTo(null);
        this.setTitle("Penjadwalan Konselor - TELUWELL");
        
        // Setup table model
        setupTableModel();
        
        // Set field properties
        jTextFieldIdKonselor.setEditable(false);
        jTextFieldNamaKonselor.setEditable(false);
        
        // Populate combo boxes
        populateHariComboBox();
        populateJamComboBox();
        
        // Load data if logged in
        if (loggedInKonselor != null) {
            populateJadwalKonselorTable();
        }
        
        // Setup listeners
        setupListeners();
        
        // Set initial button states
        jButtonHapusJadwal.setEnabled(false);
    }

    private void setupTableModel() {
        tableModelJadwalKonselorPenjadwalan = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        tableModelJadwalKonselorPenjadwalan.addColumn("ID Jadwal Konselor");
        tableModelJadwalKonselorPenjadwalan.addColumn("Nama Konselor");
        tableModelJadwalKonselorPenjadwalan.addColumn("Hari");
        tableModelJadwalKonselorPenjadwalan.addColumn("Jam Mulai");
        tableModelJadwalKonselorPenjadwalan.addColumn("Jam Selesai");
        jTableJadwalKonselorPenjadwalan.setModel(tableModelJadwalKonselorPenjadwalan);
    }

    private void setupListeners() {
        // Table selection listener
        jTableJadwalKonselorPenjadwalan.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jTableJadwalKonselorPenjadwalanValueChanged(evt);
            }
        });
    }

    private void populateJadwalKonselorTable() {
        tableModelJadwalKonselorPenjadwalan.setRowCount(0);
        
        try {
            // Tampilkan jadwal konselor yang login
            if (loggedInKonselor != null) {
                ArrayList<JadwalKons> daftarJadwalKons = dbJadwal.getJadwalKonselorByKonselor(loggedInKonselor.getIdkonselor());
                
                for (JadwalKons jk : daftarJadwalKons) {
                    // Get jadwal detail from jadwal table
                    ArrayList<Jadwal> allJadwal = dbJadwal.getAllJadwal();
                    for (Jadwal j : allJadwal) {
                        if (j.getIdjadwal().equals(jk.getIdjadwal())) {
                            Object[] rowData = {
                                jk.getIdjadwalKons(),
                                loggedInKonselor.getNmkonselor(),
                                convertHariToIndonesian(j.getHarij()),
                                j.getJammulaij().toString().substring(0,5),
                                j.getJamselesaij().toString().substring(0,5)
                            };
                            tableModelJadwalKonselorPenjadwalan.addRow(rowData);
                            break;
                        }
                    }
                }
                
                if (daftarJadwalKons.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Anda belum memiliki jadwal. Silakan tambahkan jadwal baru.", 
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error saat memuat jadwal: " + e.getMessage(),
                "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void populateHariComboBox() {
        String[] hari = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        DefaultComboBoxModel<String> modelHari = new DefaultComboBoxModel<>(hari);
        jComboBoxHari.setModel(modelHari);
    }

    private void populateJamComboBox() {
        try {
            ArrayList<Jadwal> daftarJadwal = dbJadwal.getAllJadwal();
            DefaultComboBoxModel<Jadwal> modelJam = new DefaultComboBoxModel<>();
            
            for (Jadwal jadwal : daftarJadwal) {
                modelJam.addElement(jadwal);
            }
            jComboBoxJam.setModel(modelJam);
            
            // Override toString for Jadwal objects in combo box
            jComboBoxJam.setRenderer(new javax.swing.DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(
                        javax.swing.JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Jadwal) {
                        Jadwal j = (Jadwal) value;
                        setText(j.getJammulaij().toString().substring(0,5) + " - " + 
                               j.getJamselesaij().toString().substring(0,5));
                    }
                    return this;
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error saat memuat jadwal: " + e.getMessage(),
                "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean validateInputs() {
        if (loggedInKonselor == null) {
            JOptionPane.showMessageDialog(this, "Data konselor tidak tersedia!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (jComboBoxHari.getSelectedItem() == null || jComboBoxJam.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Pilih hari dan jam terlebih dahulu!", 
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearForm() {
        if (loggedInKonselor != null) {
            jTextFieldIdKonselor.setText(loggedInKonselor.getIdkonselor());
            jTextFieldNamaKonselor.setText(loggedInKonselor.getNmkonselor() + " (" + loggedInKonselor.getSpesialisasi() + ")");
        }
        
        if (jComboBoxHari.getItemCount() > 0) {
            jComboBoxHari.setSelectedIndex(0);
        }
        if (jComboBoxJam.getItemCount() > 0) {
            jComboBoxJam.setSelectedIndex(0);
        }
        
        jTableJadwalKonselorPenjadwalan.clearSelection();
        jButtonSimpanJadwal.setText("Simpan Jadwal");
        jButtonHapusJadwal.setEnabled(false);
    }

    private String convertHariToIndonesian(String hariEnglish) {
        switch (hariEnglish) {
            case "Monday": return "Senin";
            case "Tuesday": return "Selasa"; 
            case "Wednesday": return "Rabu";
            case "Thursday": return "Kamis";
            case "Friday": return "Jumat";
            default: return hariEnglish;
        }
    }

    private String convertIndonesianToEnglish(String hariIndonesia) {
        switch (hariIndonesia) {
            case "Senin": return "Monday";
            case "Selasa": return "Tuesday";
            case "Rabu": return "Wednesday";
            case "Kamis": return "Thursday";
            case "Jumat": return "Friday";
            default: return hariIndonesia;
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableJadwalKonselorPenjadwalan = new javax.swing.JTable();
        jComboBoxHari = new javax.swing.JComboBox<>();
        jComboBoxJam = new javax.swing.JComboBox<>();
        jTextFieldIdKonselor = new javax.swing.JTextField();
        jTextFieldNamaKonselor = new javax.swing.JTextField();
        jButtonSimpanJadwal = new javax.swing.JButton();
        jButtonBatal = new javax.swing.JButton();
        jButtonHapusJadwal = new javax.swing.JButton();

        jTextField3.setText("jTextField3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Penjadwalan Konselor");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Jadwal Konselor");

        jLabel3.setText("ID Konselor                 :");

        jLabel4.setText("Nama Konselor           :");

        jLabel5.setText("Pilih Hari                      :");

        jLabel6.setText("Pilih Jam                      :");

        jTableJadwalKonselorPenjadwalan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nama Konselor", "Spesialisasi", "Hari", "Jam Mulai", "Jam Selesai"
            }
        ));
        jScrollPane1.setViewportView(jTableJadwalKonselorPenjadwalan);

        jComboBoxHari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxHariActionPerformed(evt);
            }
        });

        jComboBoxJam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxJamActionPerformed(evt);
            }
        });

        jTextFieldIdKonselor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIdKonselorActionPerformed(evt);
            }
        });

        jTextFieldNamaKonselor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNamaKonselorActionPerformed(evt);
            }
        });

        jButtonSimpanJadwal.setText("Simpan Jadwal");
        jButtonSimpanJadwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSimpanJadwalActionPerformed(evt);
            }
        });

        jButtonBatal.setText("Batal");
        jButtonBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBatalActionPerformed(evt);
            }
        });

        jButtonHapusJadwal.setText("Hapus Jadwal");
        jButtonHapusJadwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHapusJadwalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(166, 166, 166))
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldIdKonselor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldNamaKonselor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxHari, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxJam, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonBatal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSimpanJadwal)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonHapusJadwal)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldIdKonselor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNamaKonselor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxHari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSimpanJadwal)
                    .addComponent(jButtonBatal)
                    .addComponent(jButtonHapusJadwal))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Listener saat baris tabel dipilih
private void jTableJadwalKonselorPenjadwalanValueChanged(javax.swing.event.ListSelectionEvent evt) {
        if (!evt.getValueIsAdjusting() && jTableJadwalKonselorPenjadwalan.getSelectedRow() != -1) {
            int selectedRow = jTableJadwalKonselorPenjadwalan.getSelectedRow();
            
            // Get data from selected row
            String hariIndonesia = tableModelJadwalKonselorPenjadwalan.getValueAt(selectedRow, 2).toString();
            String jamMulaiStr = tableModelJadwalKonselorPenjadwalan.getValueAt(selectedRow, 3).toString();
            String jamSelesaiStr = tableModelJadwalKonselorPenjadwalan.getValueAt(selectedRow, 4).toString();

            // Set combo boxes
            String hariEnglish = convertIndonesianToEnglish(hariIndonesia);
            jComboBoxHari.setSelectedItem(hariEnglish);

            // Find matching jadwal in combo box
            for (int i = 0; i < jComboBoxJam.getItemCount(); i++) {
                Jadwal jadwal = (Jadwal) jComboBoxJam.getItemAt(i);
                if (jadwal.getJammulaij().toString().substring(0,5).equals(jamMulaiStr) &&
                    jadwal.getJamselesaij().toString().substring(0,5).equals(jamSelesaiStr)) {
                    jComboBoxJam.setSelectedIndex(i);
                    break;
                }
            }

            jButtonSimpanJadwal.setText("Update Jadwal");
            jButtonHapusJadwal.setEnabled(true);
        }
    }


    private void jComboBoxHariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxHariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxHariActionPerformed

    private void jButtonSimpanJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSimpanJadwalActionPerformed
        if (!validateInputs()) {
            return;
        }

        try {
            Jadwal selectedJadwal = (Jadwal) jComboBoxJam.getSelectedItem();
            String idJadwal = selectedJadwal.getIdjadwal();

            if (jButtonSimpanJadwal.getText().equals("Update Jadwal")) {
                // Update existing jadwal
                int selectedRow = jTableJadwalKonselorPenjadwalan.getSelectedRow();
                if (selectedRow != -1) {
                    String idJadwalKons = tableModelJadwalKonselorPenjadwalan.getValueAt(selectedRow, 0).toString();
                    
                    // For simplicity, we'll delete and re-insert
                    boolean deleted = dbJadwal.deleteJadwalKonselor(idJadwalKons);
                    if (deleted) {
                        boolean inserted = dbJadwal.insertJadwalKonselor(loggedInKonselor.getIdkonselor(), idJadwal);
                        if (inserted) {
                            JOptionPane.showMessageDialog(this, "Jadwal berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            } else {
                // Insert new jadwal
                boolean inserted = dbJadwal.insertJadwalKonselor(loggedInKonselor.getIdkonselor(), idJadwal);
                if (inserted) {
                    JOptionPane.showMessageDialog(this, "Jadwal berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menambahkan jadwal!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            clearForm();
            populateJadwalKonselorTable();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error Database: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButtonSimpanJadwalActionPerformed

    private void jButtonHapusJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHapusJadwalActionPerformed
        int selectedRow = jTableJadwalKonselorPenjadwalan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih jadwal yang ingin dihapus!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idJadwalKons = tableModelJadwalKonselorPenjadwalan.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Yakin ingin menghapus jadwal ini?", 
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean deleted = dbJadwal.deleteJadwalKonselor(idJadwalKons);
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Jadwal berhasil dihapus!", 
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    populateJadwalKonselorTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus jadwal!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error Database: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButtonHapusJadwalActionPerformed

    private void jComboBoxJamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxJamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxJamActionPerformed

    private void jTextFieldIdKonselorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIdKonselorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIdKonselorActionPerformed

    private void jTextFieldNamaKonselorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNamaKonselorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNamaKonselorActionPerformed

    private void jButtonBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBatalActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Anda yakin ingin kembali?", 
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            FramePilihLogin framePilihLogin = new FramePilihLogin();
            framePilihLogin.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButtonBatalActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FramePenjadwalanKonselor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new FramePenjadwalanKonselor().setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBatal;
    private javax.swing.JButton jButtonHapusJadwal;
    private javax.swing.JButton jButtonSimpanJadwal;
    private javax.swing.JComboBox<String> jComboBoxHari;
    private javax.swing.JComboBox<Jadwal> jComboBoxJam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableJadwalKonselorPenjadwalan;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextFieldIdKonselor;
    private javax.swing.JTextField jTextFieldNamaKonselor;
    // End of variables declaration//GEN-END:variables
}
