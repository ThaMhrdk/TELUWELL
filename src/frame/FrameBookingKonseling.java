/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package frame;

import dao.DbBooking;
import dao.DbKonselor;
import dao.DbJadwal;
import model.Booking;
import model.Konselor;
import model.Jadwal;
import model.Mahasiswa;
import model.JadwalKons;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import frame.FrameKonseling;

/**
 * @author THA
 */

public class FrameBookingKonseling extends javax.swing.JFrame {
    private final DbBooking dbBooking = new DbBooking();
    private final DbKonselor dbKonselor = new DbKonselor();
    private final DbJadwal dbJadwal = new DbJadwal();
    
    private Mahasiswa loggedInMahasiswa;
    private DefaultTableModel tableModelJadwalKonselor;

    public FrameBookingKonseling() {
        initComponents();
        initializeFrame();
    }

    public FrameBookingKonseling(Mahasiswa mahasiswa) {
        this.loggedInMahasiswa = mahasiswa;
        initComponents();
        initializeFrame();
        
        if (mahasiswa != null) {
            jTextFieldNamaMahasiswa.setText(mahasiswa.getNmdepan() + " " + mahasiswa.getNmbelakang());
            jTextFieldNamaMahasiswa.setEditable(false);
        }
    }
    private void initializeFrame() {
        this.setLocationRelativeTo(null);
        this.setTitle("Booking Konseling - TELUWELL");
        
        setupTableModel();

        jTextFieldNamaMahasiswa.setText("");
        jTextFieldNomorHP.setText("");
        
        populateKonselorComboBox();
        populateMetodeKonselingComboBox();
        populateJadwalKonselorTable();
        
        jXDatePickerTanggalKonseling.setDate(new java.util.Date());
        
        jComboBoxJamKonseling.setEnabled(false);
    }

    private void setupTableModel() {
        tableModelJadwalKonselor = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModelJadwalKonselor.addColumn("Nama Konselor");
        tableModelJadwalKonselor.addColumn("Spesialisasi");
        tableModelJadwalKonselor.addColumn("Hari");
        tableModelJadwalKonselor.addColumn("Jam Tersedia");
        jTableJadwalKonselor.setModel(tableModelJadwalKonselor);
    }

    private void populateJadwalKonselorTable() {
        tableModelJadwalKonselor.setRowCount(0);
        
        try {
            ArrayList<Konselor> daftarKonselor = dbKonselor.getAllKonselor();
            
            for (Konselor konselor : daftarKonselor) {
                ArrayList<JadwalKons> jadwalKons = dbJadwal.getJadwalKonselorByKonselor(konselor.getIdkonselor());
                
                if (!jadwalKons.isEmpty()) {
                    for (JadwalKons jk : jadwalKons) {
                        ArrayList<Jadwal> allJadwal = dbJadwal.getAllJadwal();
                        for (Jadwal j : allJadwal) {
                            if (j.getIdjadwal().equals(jk.getIdjadwal())) {
                                Object[] rowData = {
                                    konselor.getNmkonselor(),
                                    konselor.getSpesialisasi(),
                                    convertHariToIndonesia(j.getHarij()),
                                    j.getJammulaij().toString().substring(0,5) + " - " + 
                                    j.getJamselesaij().toString().substring(0,5)
                                };
                                tableModelJadwalKonselor.addRow(rowData);
                                break;
                            }
                        }
                    }
                } else {
                    Object[] rowData = {
                        konselor.getNmkonselor(),
                        konselor.getSpesialisasi(),
                        "Belum ada jadwal",
                        "Belum ada jadwal"
                    };
                    tableModelJadwalKonselor.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error memuat tabel jadwal konselor: " + e.getMessage(), 
                "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void populateKonselorComboBox() {
        try {
            ArrayList<Konselor> daftarKonselor = dbKonselor.getAllKonselor();
            DefaultComboBoxModel<Konselor> modelKonselor = new DefaultComboBoxModel<>();
            
            if (daftarKonselor.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Tidak ada konselor yang tersedia di database.", 
                    "Info Konselor", JOptionPane.INFORMATION_MESSAGE);
                jComboBoxKonselor.setEnabled(false);
            } else {
                for (Konselor konselor : daftarKonselor) {
                    modelKonselor.addElement(konselor);
                }
                jComboBoxKonselor.setModel(modelKonselor);
                jComboBoxKonselor.setEnabled(true);
                
                jComboBoxKonselor.setRenderer(new javax.swing.DefaultListCellRenderer() {
                    @Override
                    public java.awt.Component getListCellRendererComponent(
                            javax.swing.JList<?> list, Object value, int index,
                            boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value instanceof Konselor) {
                            Konselor k = (Konselor) value;
                            setText(k.getNmkonselor() + " (" + k.getSpesialisasi() + ")");
                        }
                        return this;
                    }
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error memuat daftar konselor: " + e.getMessage(), 
                "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            jComboBoxKonselor.setEnabled(false);
        }
    }

    private void populateJamKonselingComboBox() {
        jComboBoxJamKonseling.removeAllItems();
        
        Konselor selectedKonselor = (Konselor) jComboBoxKonselor.getSelectedItem();
        java.util.Date selectedDate = jXDatePickerTanggalKonseling.getDate();
        
        if (selectedKonselor == null || selectedDate == null) {
            jComboBoxJamKonseling.setEnabled(false);
            return;
        }
        
        java.text.SimpleDateFormat dayFormat = new java.text.SimpleDateFormat("EEEE", java.util.Locale.ENGLISH);
        String hari = dayFormat.format(selectedDate);
        
        try {
            ArrayList<JadwalKons> jadwalKons = dbJadwal.getJadwalKonselorByKonselor(selectedKonselor.getIdkonselor());
            ArrayList<Jadwal> availableJadwal = new ArrayList<>();
            
            ArrayList<Jadwal> allJadwal = dbJadwal.getAllJadwal();
            
            for (JadwalKons jk : jadwalKons) {
                for (Jadwal j : allJadwal) {
                    if (j.getIdjadwal().equals(jk.getIdjadwal()) && j.getHarij().equals(hari)) {
                        availableJadwal.add(j);
                    }
                }
            }
            
            DefaultComboBoxModel<Jadwal> modelJadwal = new DefaultComboBoxModel<>();
            
            if (availableJadwal.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Tidak ada jadwal tersedia untuk " + selectedKonselor.getNmkonselor() + 
                    " pada hari " + convertHariToIndonesia(hari) + ".", 
                    "Info Jadwal", JOptionPane.INFORMATION_MESSAGE);
                jComboBoxJamKonseling.setEnabled(false);
            } else {
                for (Jadwal jadwal : availableJadwal) {
                    modelJadwal.addElement(jadwal);
                }
                jComboBoxJamKonseling.setModel(modelJadwal);
                jComboBoxJamKonseling.setEnabled(true);
                
                // Set custom renderer for jadwal combo box
                jComboBoxJamKonseling.setRenderer(new javax.swing.DefaultListCellRenderer() {
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
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error memuat jam konseling: " + e.getMessage(), 
                "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            jComboBoxJamKonseling.setEnabled(false);
        }
    }

    private void populateMetodeKonselingComboBox() {
        DefaultComboBoxModel<String> modelMetode = new DefaultComboBoxModel<>();
        modelMetode.addElement("Online");
        modelMetode.addElement("Offline");
        jComboBoxMetodeKonseling.setModel(modelMetode);
    }

    private boolean validateInputs() {
        if (jTextFieldNamaMahasiswa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama mahasiswa harus diisi!", 
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (jTextFieldNomorHP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nomor HP harus diisi!", 
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (!jTextFieldNomorHP.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Nomor HP hanya boleh berisi angka!", 
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (jComboBoxKonselor.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Pilih konselor terlebih dahulu!", 
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (jXDatePickerTanggalKonseling.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Pilih tanggal konseling!", 
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (jComboBoxJamKonseling.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Pilih jam konseling!", 
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (jComboBoxMetodeKonseling.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Pilih metode konseling!", 
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }

    private void clearForm() {
        if (loggedInMahasiswa != null) {
            jTextFieldNamaMahasiswa.setText(loggedInMahasiswa.getNmdepan() + " " + loggedInMahasiswa.getNmbelakang());
        } else {
            jTextFieldNamaMahasiswa.setText("");
        }
        jTextFieldNomorHP.setText("");
        
        if (jComboBoxKonselor.getItemCount() > 0) {
            jComboBoxKonselor.setSelectedIndex(0);
        }
        jXDatePickerTanggalKonseling.setDate(new java.util.Date());
        jComboBoxJamKonseling.removeAllItems();
        jComboBoxJamKonseling.setEnabled(false);
        if (jComboBoxMetodeKonseling.getItemCount() > 0) {
            jComboBoxMetodeKonseling.setSelectedIndex(0);
        }
    }

    private String convertHariToIndonesia(String hariEnglish) {
        switch (hariEnglish) {
            case "Monday": return "Senin";
            case "Tuesday": return "Selasa"; 
            case "Wednesday": return "Rabu";
            case "Thursday": return "Kamis";
            case "Friday": return "Jumat";
            case "Saturday": return "Sabtu";
            case "Sunday": return "Minggu";
            default: return hariEnglish;
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldNamaMahasiswa = new javax.swing.JTextField();
        jTextFieldNomorHP = new javax.swing.JTextField();
        jComboBoxKonselor = new javax.swing.JComboBox<>();
        jComboBoxJamKonseling = new javax.swing.JComboBox<>();
        jXDatePickerTanggalKonseling = new org.jdesktop.swingx.JXDatePicker();
        jComboBoxMetodeKonseling = new javax.swing.JComboBox<>();
        jButtonBatal = new javax.swing.JButton();
        jButtonBooking = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableJadwalKonselor = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        ButtonLihatBookingSaya = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("FORM BOOKING KONSELING");

        jLabel2.setText("Nama Mahasiswa          :");

        jLabel3.setText("Nomor HP                     :");

        jLabel4.setText("Pilih Konselor                :");

        jLabel5.setText("Tanggal Konseling          :");

        jLabel6.setText("Jam Konseling                 :");

        jLabel7.setText("Metode Konseling           :");

        jTextFieldNomorHP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNomorHPActionPerformed(evt);
            }
        });

        jComboBoxKonselor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxKonselorActionPerformed(evt);
            }
        });

        jComboBoxJamKonseling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxJamKonselingActionPerformed(evt);
            }
        });

        jXDatePickerTanggalKonseling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePickerTanggalKonselingActionPerformed(evt);
            }
        });

        jComboBoxMetodeKonseling.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Offline", "Online" }));

        jButtonBatal.setText("Batal");
        jButtonBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBatalActionPerformed(evt);
            }
        });

        jButtonBooking.setText("Booking");
        jButtonBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBookingActionPerformed(evt);
            }
        });

        jTableJadwalKonselor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Konselor", "Spesialisasi", "Hari", "Jam Tersedia"
            }
        ));
        jTableJadwalKonselor.setToolTipText("");
        jScrollPane1.setViewportView(jTableJadwalKonselor);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("JADWAL KONSELOR");

        ButtonLihatBookingSaya.setText("Konseling Saya");
        ButtonLihatBookingSaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLihatBookingSayaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(159, 159, 159))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonLihatBookingSaya))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxKonselor, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextFieldNomorHP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                        .addComponent(jTextFieldNamaMahasiswa, javax.swing.GroupLayout.Alignment.LEADING))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(18, 18, 18)
                                    .addComponent(jComboBoxMetodeKonseling, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jButtonBatal)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonBooking))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jXDatePickerTanggalKonseling, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBoxJamKonseling, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNamaMahasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldNomorHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxKonselor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jXDatePickerTanggalKonseling, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBoxJamKonseling, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBoxMetodeKonseling, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonBatal)
                            .addComponent(jButtonBooking))))
                .addGap(36, 36, 36)
                .addComponent(ButtonLihatBookingSaya)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBatalActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Anda yakin ingin membatalkan booking?", 
            "Konfirmasi Batal", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            FrameLoginMahasiswa frameLogin = new FrameLoginMahasiswa();
            frameLogin.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButtonBatalActionPerformed

    private void jTextFieldNomorHPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNomorHPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNomorHPActionPerformed

    private void jComboBoxJamKonselingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxJamKonselingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxJamKonselingActionPerformed

    private void jXDatePickerTanggalKonselingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePickerTanggalKonselingActionPerformed
        populateJamKonselingComboBox();
    }//GEN-LAST:event_jXDatePickerTanggalKonselingActionPerformed

    private void jComboBoxKonselorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxKonselorActionPerformed
       populateJamKonselingComboBox();
    }//GEN-LAST:event_jComboBoxKonselorActionPerformed

    private void jButtonBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBookingActionPerformed
    if (!validateInputs()) {
        return;
    }

    try {
        String idBooking = dbBooking.generateBookingId();
        String kodeKonseling = dbBooking.generateKonselingId();

        java.sql.Date tglBooking = new java.sql.Date(jXDatePickerTanggalKonseling.getDate().getTime());
        Jadwal selectedJadwal = (Jadwal) jComboBoxJamKonseling.getSelectedItem();
        String metode = (String) jComboBoxMetodeKonseling.getSelectedItem();
        String nimMahasiswa = loggedInMahasiswa != null ? loggedInMahasiswa.getNim() : "GUEST";

        boolean konselingCreated = dbBooking.insertKonseling(
            kodeKonseling, 
            "Online/Offline",
            tglBooking, 
            selectedJadwal.getJammulaij(), 
            selectedJadwal.getJamselesaij()
        );

        if (!konselingCreated) {
            JOptionPane.showMessageDialog(this, 
                "Gagal membuat sesi konseling!", 
                "Error Booking", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Booking newBooking = new Booking(
            idBooking,
            tglBooking,
            selectedJadwal.getJammulaij(),
            selectedJadwal.getJamselesaij(),
            metode,
            nimMahasiswa,
            selectedJadwal.getIdjadwal(),
            kodeKonseling,
            "ACTIVE",
            null,
            null
        );

        if (dbBooking.insertBooking(newBooking)) {
            JOptionPane.showMessageDialog(this, 
                "Booking Konseling Berhasil!\nID Booking: " + idBooking, 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            
            // Refresh data tabel jika diperlukan
            populateJadwalKonselorTable();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Booking Konseling Gagal!", 
                "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "Error Database saat booking: " + e.getMessage(), 
            "Error Booking", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Terjadi kesalahan: " + e.getMessage(), 
            "Error Booking", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_jButtonBookingActionPerformed

    private void ButtonLihatBookingSayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLihatBookingSayaActionPerformed
    if (loggedInMahasiswa == null) {
        JOptionPane.showMessageDialog(this, 
            "Data mahasiswa tidak tersedia! Silakan login terlebih dahulu.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        
        FrameLoginMahasiswa frameLogin = new FrameLoginMahasiswa();
        frameLogin.setVisible(true);
        this.dispose();
        return;
    }
    
    try {
        FrameKonseling frameKonseling = new FrameKonseling(loggedInMahasiswa);
        frameKonseling.setVisible(true);
        this.dispose();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Terjadi kesalahan: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_ButtonLihatBookingSayaActionPerformed

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
            java.util.logging.Logger.getLogger(FrameBookingKonseling.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new FrameBookingKonseling().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonLihatBookingSaya;
    private javax.swing.JButton jButtonBatal;
    private javax.swing.JButton jButtonBooking;
    private javax.swing.JComboBox<Jadwal> jComboBoxJamKonseling;
    private javax.swing.JComboBox<Konselor> jComboBoxKonselor;
    private javax.swing.JComboBox<String> jComboBoxMetodeKonseling;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableJadwalKonselor;
    private javax.swing.JTextField jTextFieldNamaMahasiswa;
    private javax.swing.JTextField jTextFieldNomorHP;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerTanggalKonseling;
    // End of variables declaration//GEN-END:variables
}
