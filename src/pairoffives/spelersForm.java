/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pairoffives;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tony
 */
public class spelersForm extends javax.swing.JFrame {

    /**
     * Creates new form spelersForm
     */
    
    DefaultListModel jlistmodel = new DefaultListModel();
    DefaultTableModel tablemodel = new DefaultTableModel();
    JTable jtable = new JTable();
    
    public void spelersoverzicht(){
            
            String naam;
            String adres;
            String postcode;
            String woonplaats;
            int telnr;
            String email;
            int gewonnen;
            int verloren;
            double rating;
           
 
            
            try {
                
             Connection conn = SimpleDataSourceV2.getConnection();

             Statement stat = conn.createStatement();

             ResultSet result = stat.executeQuery("select * from speler");
                
                    
                    
                    tablemodel.addColumn("naam");
                    tablemodel.addColumn("adres");
                    tablemodel.addColumn("postcode");
                    tablemodel.addColumn("woonplaats");
                    tablemodel.addColumn("telnr");
                    tablemodel.addColumn("email");
                    tablemodel.addColumn("gewonnen");
                    tablemodel.addColumn("verloren");
                    tablemodel.addColumn("rating");
             
                while (result.next()) {
                    
                    naam = result.getString("naam");
                    adres = result.getString("adres");
                    postcode = result.getString("postcode");
                    woonplaats = result.getString("woonplaats");
                    telnr = result.getInt("telnr");
                    email = result.getString("email");
                    gewonnen = result.getInt("gewonnen");
                    verloren = result.getInt("verloren");
                    rating = result.getDouble("rating");
                    
                   
                    tablemodel.addRow(new Object[]{naam, adres, postcode, 
                        woonplaats, telnr, email, gewonnen, verloren, rating});
                    

                }
                jTable1.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
                jTable1.setModel(tablemodel);
                
            } catch (Exception ex) {

                System.out.println(ex);
            
        }
        
        
    }
    
    public void speleropslaan(){
        
               try {
              
            Connection conn = SimpleDataSourceV2.getConnection();

            //Statement stat = conn.createStatement();

            String prepSqlStatement = "INSERT INTO speler (naam,adres,postcode,woonplaats,telnr,email) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stat = conn.prepareStatement(prepSqlStatement);
            stat.setString(1, (jTextField1.getText()));
            stat.setString(2, (jTextField2.getText()));
            stat.setString(3, jTextField3.getText());
            stat.setString(4, jTextField4.getText());
            stat.setString(5, (jTextField5.getText()));
            stat.setString(6, jTextField6.getText());
            
            int effectedRecords = stat.executeUpdate();

            JOptionPane.showMessageDialog(null, "Speler opgeslagen");
            
            spelersoverzicht();
            
            stat.close();
        } catch (SQLException e) {
            System.out.println("Fout bij opslaan speler: " + e);
        }
    }
    
    
    
    public spelersForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(883, 427));
        setMinimumSize(new java.awt.Dimension(883, 427));
        setPreferredSize(new java.awt.Dimension(883, 427));
        getContentPane().setLayout(null);

        jLabel1.setText("Naam:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 90, 60, 20);

        jLabel2.setText("Adres:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(40, 130, 50, 20);

        jLabel3.setText("Postcode:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 170, 70, 20);

        jLabel4.setText("Woonplaats:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 210, 80, 20);

        jLabel5.setText("Telnr:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(40, 250, 60, 20);

        jLabel6.setText("Email:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(40, 290, 50, 20);
        getContentPane().add(jLabel7);
        jLabel7.setBounds(10, 230, 0, 0);
        getContentPane().add(jTextField1);
        jTextField1.setBounds(110, 80, 129, 25);
        getContentPane().add(jTextField2);
        jTextField2.setBounds(110, 120, 129, 25);
        getContentPane().add(jTextField3);
        jTextField3.setBounds(110, 160, 129, 25);
        getContentPane().add(jTextField4);
        jTextField4.setBounds(110, 200, 129, 25);
        getContentPane().add(jTextField5);
        jTextField5.setBounds(110, 240, 129, 25);
        getContentPane().add(jTextField6);
        jTextField6.setBounds(110, 280, 129, 25);

        jButton1.setText("Opslaan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(120, 330, 90, 32);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Spelers overzicht");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(280, 360, 160, 22);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Speler aanmaken");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(90, 20, 160, 22);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(260, 80, 580, 180);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Spelers overzicht");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(460, 20, 160, 22);

        jButton2.setText("Verwijderen");
        getContentPane().add(jButton2);
        jButton2.setBounds(560, 510, 120, 32);

        jButton3.setText("Wijzigen");
        getContentPane().add(jButton3);
        jButton3.setBounds(470, 300, 80, 32);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Naam", "Achternaam", "Adres", "postcode"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(290, 80, 440, 90);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        speleropslaan();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(spelersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(spelersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(spelersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(spelersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new spelersForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
