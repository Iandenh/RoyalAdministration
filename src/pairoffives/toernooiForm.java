/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pairoffives;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.WindowConstants;

/**
 *
 * @author ian
 */
public class toernooiForm extends javax.swing.JFrame {

    /**
     * Creates new form toernooiForm
     */
    public toernooiForm() {
        initComponents();
    }
    DefaultTableModel tablemodel = new DefaultTableModel();
        
    public void toernooiOverzicht() {

        int id;
        String locatie;
        String naam;
        String soorttoernooi;
        double kosten;
        String datum;
        int min_deelnemers;
        int max_deelnemers;
        try {

            Connection conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();

            ResultSet result = stat.executeQuery("select * from toernooi");

            // refreshing table na het wijzigen van een speler.
            tablemodel.setColumnCount(0);
            tablemodel.setNumRows(0);
            toernooiTable.setModel(tablemodel);

            tablemodel.addColumn("id");
            tablemodel.addColumn("locatie");
            tablemodel.addColumn("naam");
            tablemodel.addColumn("soorttoernooi");
            tablemodel.addColumn("kosten");
            tablemodel.addColumn("datum");
            tablemodel.addColumn("min_deelnemers");
            tablemodel.addColumn("max_deelnemers");

            while (result.next()) {

                id = result.getInt("id");
                locatie = result.getString("locatie");
                naam = result.getString("naam");
                soorttoernooi = result.getString("soorttoernooi");
                kosten = result.getInt("kosten");
                datum = result.getString("datum");
                min_deelnemers = result.getInt("min_deelnemers");
                max_deelnemers = result.getInt("max_deelnemers");

                tablemodel.addRow(new Object[]{id, locatie, naam, soorttoernooi,
                    kosten, datum, min_deelnemers, max_deelnemers});

            }
            toernooiTable.setAutoResizeMode(toernooiTable.AUTO_RESIZE_OFF);
            toernooiTable.setModel(tablemodel);

        } catch (Exception ex) {

            System.out.println(ex);

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

        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        toernooiTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        locatieField = new javax.swing.JTextField();
        naamField = new javax.swing.JTextField();
        soortField = new javax.swing.JTextField();
        kostenField = new javax.swing.JTextField();
        datumField = new javax.swing.JTextField();
        minField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        maxField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1074, 470));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Toernooi overzicht");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(630, 30, 190, 22);

        toernooiTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        toernooiTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                toernooiTableMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toernooiTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(toernooiTable);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(460, 80, 561, 257);

        jButton2.setText("Toernooi verwijderen");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(460, 350, 160, 32);

        jLabel1.setText("Locatie:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(80, 90, 60, 20);

        jLabel2.setText("Naam:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(90, 130, 40, 20);

        jLabel3.setText("Soort:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(90, 170, 41, 20);

        jLabel4.setText("Kosten:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(80, 210, 50, 20);

        jLabel5.setText("Datum:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(80, 250, 60, 20);

        jLabel6.setText("Minimaal deelnemers:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 290, 130, 20);
        getContentPane().add(locatieField);
        locatieField.setBounds(150, 80, 150, 25);
        getContentPane().add(naamField);
        naamField.setBounds(150, 120, 150, 25);

        soortField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soortFieldActionPerformed(evt);
            }
        });
        getContentPane().add(soortField);
        soortField.setBounds(150, 160, 150, 25);
        getContentPane().add(kostenField);
        kostenField.setBounds(150, 200, 150, 25);
        getContentPane().add(datumField);
        datumField.setBounds(150, 240, 150, 25);

        minField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minFieldActionPerformed(evt);
            }
        });
        getContentPane().add(minField);
        minField.setBounds(150, 280, 150, 30);

        jButton1.setText("Toernooi opslaan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(40, 370, 130, 32);

        jButton3.setText("Toernooi wijzigen");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(200, 370, 150, 32);
        getContentPane().add(jLabel11);
        jLabel11.setBounds(140, 50, 34, 0);
        getContentPane().add(jLabel8);
        jLabel8.setBounds(150, 50, 40, 20);

        maxField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxFieldActionPerformed(evt);
            }
        });
        getContentPane().add(maxField);
        maxField.setBounds(150, 320, 150, 30);

        jLabel7.setText("Maximaal deelnemers:");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(10, 330, 140, 20);

        jLabel12.setText("Id:");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(100, 50, 34, 20);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Toernooi aanmaken");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(140, 10, 160, 22);

        jButton4.setText("Zie rondes");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4);
        jButton4.setBounds(630, 350, 160, 32);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        toernooiVerwijderen();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        toernooiOpslaan();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        toernooiWijzigen();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void minFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minFieldActionPerformed

    private void maxFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maxFieldActionPerformed

    private void toernooiTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toernooiTableMouseClicked
        try {

            Connection conn = SimpleDataSourceV2.getConnection();

            int row = toernooiTable.getSelectedRow();
            String Table_click = (toernooiTable.getModel().getValueAt(row, 0).toString());

            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("select * from toernooi where id ='" + Table_click + "'");

            while (result.next()) {

                int add0 = result.getInt("id");
                jLabel8.setText(Integer.toString(add0));
                String locatie = result.getString("locatie");
                String naam = result.getString("naam");
                String soorttoernooi = result.getString("soorttoernooi");
                String kosten = result.getString("kosten");
                String datum = result.getString("datum");
                String minDeelnemers = result.getString("min_deelnemers");
                String maxDeelnemers = result.getString("max_deelnemers");

                locatieField.setText(locatie);
                naamField.setText(naam);
                soortField.setText(soorttoernooi);
                kostenField.setText(kosten);
                datumField.setText(datum);
                minField.setText(minDeelnemers);
                maxField.setText(maxDeelnemers);
            }

        } catch (Exception ex) {

            System.out.println(ex);
        }
    }//GEN-LAST:event_toernooiTableMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int toernooiID = 0;
        String toernooiNaam = naamField.getText();
        
        try {
            toernooiID = Integer.parseInt(jLabel8.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Toernooi ID is niet gevonden.");
        }       
            
        if (toernooiID > 0) {
            RoundsForm roundsForm = new RoundsForm(toernooiID, toernooiNaam);
            roundsForm.setVisible(true);
        }        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void soortFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soortFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_soortFieldActionPerformed

    private void toernooiTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toernooiTableMousePressed
        // TODO add your handling code here:
        //JTable JTable1 = (JTable) evt.getSource();
        Point p = evt.getPoint();
        int row = toernooiTable.rowAtPoint(p);
        if (evt.getClickCount() == 2) {
            // your valueChanged overridden method 
            int selectedObject = (int) toernooiTable.getModel().getValueAt(row, 0);
            System.out.println(selectedObject);
            ToernooiSpelersForm toernooiSpeler = new ToernooiSpelersForm(selectedObject);
            toernooiSpeler.setVisible(true);
            toernooiSpeler.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            toernooiSpeler.setTitle((String) toernooiTable.getModel().getValueAt(row, 2));
        }
    }//GEN-LAST:event_toernooiTableMousePressed
    public void toernooiOpslaan() {

        try {

            Connection conn = SimpleDataSourceV2.getConnection();

            String prepSqlStatement = "INSERT INTO toernooi (locatie, naam,soorttoernooi,kosten,datum,min_deelnemers,max_deelnemers) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stat = conn.prepareStatement(prepSqlStatement);
            stat.setString(1, locatieField.getText());
            stat.setString(2, naamField.getText());
            stat.setString(3, soortField.getText());
            stat.setString(4, kostenField.getText());
            SimpleDateFormat from = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd");
            Date date = from.parse(datumField.getText());       // 01/02/2014
            String mysqlString = to.format(date);     // 2014-02-01
            stat.setString(5, mysqlString);
            stat.setString(6, minField.getText());
            stat.setString(7, maxField.getText());

            int effectedRecords = stat.executeUpdate();

            //Table update          
            toernooiOverzicht();

            JOptionPane.showMessageDialog(null, "Speler opgeslagen");

            stat.close();
        } catch (SQLException e) {
            System.out.println("Fout bij opslaan speler: " + e);
        } catch (Exception e) {
            
        }
    }
    
    public void toernooiVerwijderen() {

        try {

            Connection connect = SimpleDataSourceV2.getConnection();

            PreparedStatement pstm = connect.prepareStatement("delete from toernooi where id=?");

            pstm.setString(1, jLabel8.getText());

            pstm.executeUpdate();

            //Refreshing spelers overzicht table
            toernooiOverzicht();

            JOptionPane.showMessageDialog(null, "Toernooi verwijderd");

            pstm.close();

        } catch (Exception ex) {

            System.out.println(ex);
        }

    }
    
    public void toernooiWijzigen() {

        try {

            Connection conn = SimpleDataSourceV2.getConnection();
            PreparedStatement result = conn.prepareStatement("update toernooi SET locatie=?, naam=?, soorttoernooi=?, kosten=?, datum=?, min_deelnemers=?, max_deelnemers=? where id = " + jLabel8.getText());
            result.setString(1, locatieField.getText());
            result.setString(2, naamField.getText());
            result.setString(3, soortField.getText());
            result.setString(4, kostenField.getText());
            SimpleDateFormat from = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd");
            Date date = from.parse(datumField.getText());       // 01/02/2014
            String mysqlString = to.format(date);     // 2014-02-01
            result.setString(5, mysqlString);
            result.setString(6, minField.getText());
            result.setString(7, maxField.getText());

            int res = result.executeUpdate();

            toernooiOverzicht();

            JOptionPane.showMessageDialog(null, "Speler gewijzigd");

            result.close();

        } catch (Exception ex) {

            System.out.println(ex);
        }
    }
    
    
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
            java.util.logging.Logger.getLogger(toernooiForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(toernooiForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(toernooiForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(toernooiForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new toernooiForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField datumField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField kostenField;
    private javax.swing.JTextField locatieField;
    private javax.swing.JTextField maxField;
    private javax.swing.JTextField minField;
    private javax.swing.JTextField naamField;
    private javax.swing.JTextField soortField;
    private javax.swing.JTable toernooiTable;
    // End of variables declaration//GEN-END:variables
}
