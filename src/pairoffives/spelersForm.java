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

    public void spelers_overzicht() {

        int id;
        String naam;
        String adres;
        String postcode;
        String woonplaats;
        String telnr;
        String email;
        int gewonnen;
        int verloren;
        double rating;

        try {

            Connection conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();

            ResultSet result = stat.executeQuery("select * from speler");

            // refreshing table na het wijzigen van een speler.
            tablemodel.setColumnCount(0);
            tablemodel.setNumRows(0);
            jTable1.setModel(tablemodel);

            tablemodel.addColumn("id");
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

                id = result.getInt("id");
                naam = result.getString("naam");
                adres = result.getString("adres");
                postcode = result.getString("postcode");
                woonplaats = result.getString("woonplaats");
                telnr = result.getString("telnr");
                email = result.getString("email");
                gewonnen = result.getInt("gewonnen");
                verloren = result.getInt("verloren");
                rating = result.getDouble("rating");

                tablemodel.addRow(new Object[]{id, naam, adres, postcode,
                    woonplaats, telnr, email, gewonnen, verloren, rating});

            }
            
            jTable1.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
            jTable1.setModel(tablemodel);
            
        } catch (Exception ex) {

            System.out.println(ex);

        }

    }

    public void spelerOpslaanWijzigen() {

        try {

            Connection conn = SimpleDataSourceV2.getConnection();

            if(jLabel8.getText() == ""){
               
            String prepSqlStatement = "INSERT INTO speler (naam,adres,postcode,woonplaats,telnr,email) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stat = conn.prepareStatement(prepSqlStatement);
            stat.setString(1, jTextField1.getText());
            stat.setString(2, jTextField2.getText());
            stat.setString(3, jTextField3.getText());
            stat.setString(4, jTextField4.getText());
            stat.setString(5, jTextField5.getText());
            stat.setString(6, jTextField6.getText());

            int effectedRecords = stat.executeUpdate();

            //Table update          
            spelers_overzicht();

            JOptionPane.showMessageDialog(null, "Speler opgeslagen");

            
            stat.close();
            
            }else{
                
                try {

            PreparedStatement result = conn.prepareStatement("update speler SET naam=?, adres=?, postcode=?, woonplaats=?, telnr=?, email=? where id =" + jLabel8.getText());

            result.setString(1, jTextField1.getText());
            result.setString(2, jTextField2.getText());
            result.setString(3, jTextField3.getText());
            result.setString(4, jTextField4.getText());
            result.setString(5, jTextField5.getText());
            result.setString(6, jTextField6.getText());

            int res = result.executeUpdate();

            spelers_overzicht();

            JOptionPane.showMessageDialog(null, "Speler gewijzigd");

            result.close();

        } catch (Exception ex) {

            System.out.println(ex);
        }
                
            }
        } catch (SQLException e) {
            System.out.println("Fout bij opslaan speler: " + e);
        }
    }

    public void speler_verwijderen() {

        try {

            Connection connect = SimpleDataSourceV2.getConnection();

            PreparedStatement pstm = connect.prepareStatement("delete from speler where id=?");

            pstm.setString(1, jLabel8.getText());

            pstm.executeUpdate();

            //Refreshing spelers overzicht table
            spelers_overzicht();

            JOptionPane.showMessageDialog(null, "Speler verwijderd");

            pstm.close();

        } catch (Exception ex) {

            System.out.println(ex);
        }

    }

    public void vullen_spelers() {

        try {

            Connection conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("select * from speler");

            while (result.next()) {

                ModelItem item = new ModelItem();

                item.id = result.getInt("id");
                item.naam = result.getString("naam");
                item.adres = result.getString("adres");

            }

        } catch (Exception ex) {

            System.out.println(ex);
        }

    }

    public void speler_wijzigen() {

        try {

            Connection conn = SimpleDataSourceV2.getConnection();
            PreparedStatement result = conn.prepareStatement("update speler SET naam=?, adres=?, postcode=?, woonplaats=?, telnr=?, email=? where id =" + jLabel8.getText());

            result.setString(1, jTextField1.getText());
            result.setString(2, jTextField2.getText());
            result.setString(3, jTextField3.getText());
            result.setString(4, jTextField4.getText());
            result.setString(5, jTextField5.getText());
            result.setString(6, jTextField6.getText());

            int res = result.executeUpdate();

            spelers_overzicht();

            JOptionPane.showMessageDialog(null, "Speler gewijzigd");

            result.close();

        } catch (Exception ex) {

            System.out.println(ex);
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
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1075, 427));
        getContentPane().setLayout(null);

        jLabel1.setText("Naam:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 90, 60, 14);

        jLabel2.setText("Adres:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(40, 130, 50, 14);

        jLabel3.setText("Postcode:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 170, 70, 14);

        jLabel4.setText("Woonplaats:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 210, 80, 14);

        jLabel5.setText("Telnr:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(40, 250, 60, 14);

        jLabel6.setText("Email:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(40, 290, 50, 14);
        getContentPane().add(jLabel7);
        jLabel7.setBounds(10, 230, 0, 0);
        getContentPane().add(jTextField1);
        jTextField1.setBounds(110, 80, 150, 25);
        getContentPane().add(jTextField2);
        jTextField2.setBounds(110, 120, 150, 25);
        getContentPane().add(jTextField3);
        jTextField3.setBounds(110, 160, 150, 25);
        getContentPane().add(jTextField4);
        jTextField4.setBounds(110, 200, 150, 25);
        getContentPane().add(jTextField5);
        jTextField5.setBounds(110, 240, 150, 25);
        getContentPane().add(jTextField6);
        jTextField6.setBounds(110, 280, 150, 30);

        jButton1.setText("Speler opslaan/wijzigen");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(100, 340, 170, 23);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Speler aanmaken");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(100, 10, 160, 22);

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(420, 80, 580, 180);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Spelers overzicht");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(630, 30, 160, 22);

        jButton2.setText("Speler verwijderen");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(420, 280, 130, 23);

        jLabel11.setText("Id:");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(60, 60, 34, 14);
        getContentPane().add(jLabel8);
        jLabel8.setBounds(110, 50, 40, 20);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        spelerOpslaanWijzigen();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        speler_verwijderen();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        try {

            Connection conn = SimpleDataSourceV2.getConnection();

            int row = jTable1.getSelectedRow();
            String Table_click = (jTable1.getModel().getValueAt(row, 0).toString());

            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("select * from speler where id ='" + Table_click + "'");

            while (result.next()) {

                int add0 = result.getInt("id");
                jLabel8.setText(Integer.toString(add0));
                String add1 = result.getString("naam");
                String add2 = result.getString("adres");
                String add3 = result.getString("postcode");
                String add4 = result.getString("woonplaats");
                String add5 = result.getString("telnr");
                String add6 = result.getString("email");

                jTextField1.setText(add1);
                jTextField2.setText(add2);
                jTextField3.setText(add3);
                jTextField4.setText(add4);
                jTextField5.setText(add5);
                jTextField6.setText(add6);
            }

        } catch (Exception ex) {

            System.out.println(ex);
        }

    }//GEN-LAST:event_jTable1MouseClicked

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
