/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import dao.DbBooking;
import dao.DbMahasiswa;
import dao.DbKonselor;
import dao.DbJadwal;
import model.JadwalKons;
import model.Booking;
import model.Mahasiswa;
import model.Konselor;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import frame.FrameLoginMahasiswa;
import frame.FrameBookingKonseling;


/**
 * @author THA
 */

public class FrameKonseling extends javax.swing.JFrame {
    
    private final DbBooking dbBooking = new DbBooking();
    private final DbMahasiswa dbMahasiswa = new DbMahasiswa();
    private final DbKonselor dbKonselor = new DbKonselor();
    private final DbJadwal dbJadwal = new DbJadwal();
    
    private DefaultTableModel tableModel;
    private Mahasiswa loggedInMahasiswa;
    
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

    public FrameKonseling() {
        initComponents();
        initializeFrame();
    }

    public FrameKonseling(Mahasiswa mahasiswa) {
        this.loggedInMahasiswa = mahasiswa;
        initComponents();
        initializeFrame();
        
        if (mahasiswa != null) {
            setTitle("Status Konseling - " + mahasiswa.getNmdepan() + " " + mahasiswa.getNmbelakang());
        }
        
        loadBookingData();
    }

    private void initializeFrame() {
        this.setLocationRelativeTo(null);
        this.setTitle("Status Konseling - TELUWELL");
        setupTable();
    }

    private void setupTable() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableModel.addColumn("ID Booking");
        tableModel.addColumn("Konselor");
        tableModel.addColumn("Tanggal");
        tableModel.addColumn("Jam");
        tableModel.addColumn("Status");
        
        tableBookingList.setModel(tableModel);
        
        tableBookingList.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableBookingList.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableBookingList.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableBookingList.getColumnModel().getColumn(3).setPreferredWidth(120);
        tableBookingList.getColumnModel().getColumn(4).setPreferredWidth(80);
    }

private void loadBookingData() {
    if (loggedInMahasiswa == null) {
        JOptionPane.showMessageDialog(this, 
            "Data mahasiswa tidak tersedia!", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    try {
        tableModel.setRowCount(0);
        
        ArrayList<Object[]> bookingData = dbBooking.getBookingByMahasiswa(loggedInMahasiswa.getNim());
        
        for (Object[] row : bookingData) {
            String idBooking = (String) row[0];
            String namaKonselor = (String) row[1];
            String tanggal = dateFormatter.format((java.sql.Date) row[2]);
            String jam = timeFormatter.format((java.sql.Time) row[3]) + " - " + 
                        timeFormatter.format((java.sql.Time) row[4]);
            String status = (String) row[6];
            
            tableModel.addRow(new Object[]{idBooking, namaKonselor, tanggal, jam, status});
        }
        
        int totalBookings = tableModel.getRowCount();
        setTitle("Status Konseling - " + loggedInMahasiswa.getNmdepan() + " (" + totalBookings + " booking)");
        
        if (totalBookings == 0) {
            JOptionPane.showMessageDialog(this, 
                "Anda belum memiliki booking konseling.", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "Error loading booking data: " + e.getMessage(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

private String getKonselorNameByJadwal(String idJadwal) {
    try {
        ArrayList<JadwalKons> jadwalKons = dbJadwal.getJadwalKonselorByKonselor("");
        for (JadwalKons jk : jadwalKons) {
            if (jk.getIdjadwal().equals(idJadwal)) {
                ArrayList<Konselor> allKonselor = dbKonselor.getAllKonselor();
                for (Konselor k : allKonselor) {
                    if (k.getIdkonselor().equals(jk.getIdkonselor())) {
                        return k.getNmkonselor();
                    }
                }
            }
        }
        return "Unknown";
    } catch (SQLException e) {
        return "Unknown";
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

        jLabel4 = new javax.swing.JLabel();
        labelTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBookingList = new javax.swing.JTable();
        ButtonKembali = new javax.swing.JButton();
        ButtonSelesai = new javax.swing.JButton();

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelTitle.setText("CATATAN STATUS KONSELING");

        tableBookingList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Konselor", "Tanggal", "Jam", "Status"
            }
        ));
        jScrollPane1.setViewportView(tableBookingList);

        ButtonKembali.setText("Kembali");
        ButtonKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonKembaliActionPerformed(evt);
            }
        });

        ButtonSelesai.setText("Selesai");
        ButtonSelesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSelesaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(ButtonKembali)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonSelesai)))
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(labelTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(labelTitle)
                .addGap(63, 63, 63)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonKembali)
                    .addComponent(ButtonSelesai))
                .addGap(63, 63, 63))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSelesaiActionPerformed
    int selectedRow = tableBookingList.getSelectedRow();
    
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this,
            "Pilih booking yang ingin diselesaikan!",
            "Peringatan",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String idBooking = (String) tableModel.getValueAt(selectedRow, 0);
    String status = (String) tableModel.getValueAt(selectedRow, 4);
    
    if ("COMPLETED".equals(status)) {
        JOptionPane.showMessageDialog(this,
            "Booking ini sudah diselesaikan!",
            "Informasi",
            JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(this,
        "Apakah Anda yakin ingin menyelesaikan booking " + idBooking + "?",
        "Konfirmasi Selesai",
        JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            boolean success = dbBooking.completeBooking(idBooking);
            
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Booking berhasil diselesaikan!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
                loadBookingData();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Gagal menyelesaikan booking!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error database: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_ButtonSelesaiActionPerformed

    private void ButtonKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonKembaliActionPerformed
        String[] options = {"Booking Konseling", "Login Mahasiswa", "Batal"};
        int choice = JOptionPane.showOptionDialog(this,
            "Kemana Anda ingin kembali?",
            "Pilih Tujuan",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        switch (choice) {
            case 0:
                if (loggedInMahasiswa != null) {
                    FrameBookingKonseling frameBooking = new FrameBookingKonseling(loggedInMahasiswa);
                    frameBooking.setVisible(true);
                    this.dispose();
                }
                break;
            case 1:
                FrameLoginMahasiswa frameLogin = new FrameLoginMahasiswa();
                frameLogin.setVisible(true);
                this.dispose();
                break;
            case 2:
            default:
                break;
        }
    }//GEN-LAST:event_ButtonKembaliActionPerformed

    /**
     * @param args the command line arguments
     */
// Ganti lambda di main method dengan anonymous class
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameKonseling.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameKonseling().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonKembali;
    private javax.swing.JButton ButtonSelesai;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JTable tableBookingList;
    // End of variables declaration//GEN-END:variables
}
