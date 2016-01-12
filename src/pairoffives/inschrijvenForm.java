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
import java.util.ArrayList;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Tony
 */
public class inschrijvenForm extends javax.swing.JFrame {

    /**
     * Creates new form inschrijvenForm
     */
    DefaultComboBoxModel box1 = new DefaultComboBoxModel();
    DefaultComboBoxModel box2 = new DefaultComboBoxModel();    // Spelernamen en toernooinamen

    public void Vullen_Spelers() {

        try {

            box1.removeAllElements();

            Connection conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();

            ResultSet result = stat.executeQuery("select * from speler");

            while (result.next()) {

                ModelItem item = new ModelItem();

                item.id = result.getInt("id");
                item.naam = result.getString("naam");

                box1.addElement(item);

            }

            jComboBox1.setModel(box1);

        } catch (Exception ex) {

            System.out.println(ex);
        }

    }

    public void Vullen_Toernooi() {

        try {

            Connection conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();

            ResultSet result = stat.executeQuery("select * from toernooi");

            while (result.next()) {

                ModelItem item = new ModelItem();

                item.id = result.getInt("id");
                item.naam = result.getString("naam");
                item.kosten = result.getDouble("kosten");

                box2.addElement(item);

            }

            jComboBox2.setModel(box2);

        } catch (Exception ex) {

            System.out.println(ex);
        }

    }

    public int Vullen_Deelnemers() {

        try {

            Connection conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();
            ModelItem combobox1 = (ModelItem) jComboBox1.getSelectedItem();
            ResultSet result = stat.executeQuery("select * from deelnemer where speler_id = " + combobox1.id);

            if (result.next()) {

                return result.getInt("id");

            }

        } catch (Exception ex) {

            System.out.println(ex);
        }

        return 0;

    }

    public int id;

    public void checkTafel() {

        ArrayList<String> obj3 = new ArrayList<String>();

        try {

            ModelItem combobox2 = (ModelItem) jComboBox2.getSelectedItem();
            ModelItem combobox1 = (ModelItem) jComboBox1.getSelectedItem();

            Connection conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();

            ResultSet result = stat.executeQuery("SELECT T.* FROM TAFEL AS T JOIN RoundRegistration AS RR ON RR.TafelID = T.ID "
                    + "WHERE RR.RONDE = 1 AND ToernooiID = " + combobox2.id + " GROUP BY T.ID HAVING COUNT(RR.ID) < T.Max_Aantal_spelers LIMIT 1"); //credits Thomas

            ModelItem item = new ModelItem();
            while (result.next()) {

                item.id = result.getInt("id");
                id = result.getInt("id");
                item.toernooi_id = result.getInt("toernooi_id");
                item.maxDeelnemers = result.getInt("Max_aantal_spelers");

            }

            String prepSqlStatement = "INSERT INTO RoundRegistration (Ronde, ToernooiID, TafelID, DeelnemerID) VALUES (?, ?, ?, ?)";

            PreparedStatement stat1 = conn.prepareStatement(prepSqlStatement);

            stat1.setInt(1, 1);
            stat1.setInt(2, combobox2.id);
            stat1.setInt(3, id);
            stat1.setInt(4, Vullen_Deelnemers());

            stat1.executeUpdate();

        } catch (Exception ex) {

            System.out.println(ex);
        }

    }

    public void checkTafel2() {

        ArrayList<String> obj3 = new ArrayList<String>();

        try {
            int maxdeelnemerpertafel = 0;

            int deelnemers = 0;
            int tafelID = 0;
            int test = 0;
            int test2 = 0;

            ModelItem combobox2 = (ModelItem) jComboBox2.getSelectedItem();
            ModelItem combobox1 = (ModelItem) jComboBox1.getSelectedItem();

            Connection conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();

            ResultSet result2 = stat.executeQuery("SELECT id FROM tafel where toernooi_id = " + combobox2.id + " ORDER BY id limit 1;");

            while (result2.next()) {

                tafelID = result2.getInt("id");
                System.out.println(tafelID + "TafelID");

            }

            ResultSet result = stat.executeQuery("SELECT max_aantal_spelers from tafel T where T.toernooi_id = " + combobox2.id + " limit 1"); //Max aantal deelnemers per tafel gesorteerd op toernooiID

            while (result.next()) {

                maxdeelnemerpertafel = result.getInt("max_aantal_spelers");
                System.out.println(maxdeelnemerpertafel + "TafelID");

            }

            ResultSet result1 = stat.executeQuery("SELECT count(DeelnemerID) as deelnemers from roundregistration where TafelID = " + tafelID + " and toernooiID = " + combobox2.id);

            while (result1.next()) {

                deelnemers = result1.getInt("deelnemers");
                System.out.println(deelnemers + "TafelID");

            }

            //ResultSet result7 = stat.executeQuery("SELECT tafel.id, count(RR.DeelnemerID) as test FROM tafel join roundregistration RR on RR.TafelID = tafel.id where RR.ToernooiID = "+combobox2.id+" and TafelID = 1");
            // while(result7.next()){
            //test = result.getInt("test");
            //test2 = result.getInt("id");
            //}
            if (deelnemers >= maxdeelnemerpertafel) {

                tafelID += 1;
                System.out.println(tafelID + "TafelInew");

            }

            String prepSqlStatement = "INSERT INTO RoundRegistration (Ronde, ToernooiID, TafelID, DeelnemerID) VALUES (?, ?, ?, ?)";

            PreparedStatement stat1 = conn.prepareStatement(prepSqlStatement);

            stat1.setInt(1, 1);
            stat1.setInt(2, combobox2.id);
            stat1.setInt(3, tafelID);
            stat1.setInt(4, Vullen_Deelnemers());

            stat1.executeUpdate();

        } catch (Exception ex) {

            System.out.println(ex);
        }

    }

    public void checkTafel3() {

        ArrayList<Integer> volletafels = new ArrayList<Integer>();
        ArrayList<Integer> tafelIDD = new ArrayList<Integer>();
        ArrayList<Integer> legetafels = new ArrayList<Integer>();

        int deelnemers = 0;
        int tafelID = 0;
        int tafelIDLast = 0;
        int volleTafels = 0;
        int legetafel = 0;

        try {

            ModelItem combobox2 = (ModelItem) jComboBox2.getSelectedItem();
            ModelItem combobox1 = (ModelItem) jComboBox1.getSelectedItem();

            Connection conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();

            ResultSet result4 = stat.executeQuery("SELECT id from tafel");

            while (result4.next()) {

                tafelIDD.add(result4.getInt("id"));

            }

            ResultSet result2 = stat.executeQuery("SELECT RR.TafelID as tafelID, count(RR.DeelnemerID) as deelnemers FROM roundregistration RR\n"
                    + "inner join tafel T on T.id = RR.TafelID\n"
                    + "where T.toernooi_id = " + combobox2.id + "\n"
                    + "group by t.id\n"
                    + "having count(RR.DeelnemerID) >= 10");

            while (result2.next()) {

                volletafels.add((result2.getInt("tafelID")));
                System.out.println(volletafels.get(1));

            }

            for (int i = 0; i < volletafels.size(); i++) {

                while (volletafels.get(i) != tafelIDD.get(i)) {

                    legetafels.add(i);
                    System.out.println("contains" + i);

                }

            }

            String prepSqlStatement = "INSERT INTO RoundRegistration (Ronde, ToernooiID, TafelID, DeelnemerID) VALUES (?, ?, ?, ?)";

            PreparedStatement stat1 = conn.prepareStatement(prepSqlStatement);

            stat1.setInt(1, 1);
            stat1.setInt(2, combobox2.id);
            stat1.setInt(3, legetafels.get(1));
            stat1.setInt(4, Vullen_Deelnemers());

            stat1.executeUpdate();

        } catch (Exception ex) {

            System.out.println(ex);
        }

    }

    public void Speler_Inschrijven() {

        ArrayList<String> obj = new ArrayList<String>();
        ArrayList<String> obj1 = new ArrayList<String>();

        try {

            //jTextField2.setText(null);
            ModelItem combobox1 = (ModelItem) jComboBox1.getSelectedItem();
            ModelItem combobox2 = (ModelItem) jComboBox2.getSelectedItem();
            double betaling = Double.parseDouble(jTextField2.getText());
            int maxSpelers = 0;
            int ingeschrevenSpelers = 0;

            Connection conn = SimpleDataSourceV2.getConnection();

            String prepSqlStatement = "INSERT INTO deelnemer (speler_id,toernooi_id,betaling) VALUES (?, ?, ?)";
            PreparedStatement stat = conn.prepareStatement(prepSqlStatement);

            stat.setInt(1, combobox1.id);
            stat.setInt(2, combobox2.id);

            //Speler kan zich alleen inschrijven als het volledige inleg bedrag is overgemaakt
            if (betaling > combobox2.kosten) {

                JOptionPane.showMessageDialog(null, "Je kan niet meer inleggeld vragen dan het inleg bedrag voor het toernooi " + combobox2.naam);

                return;

            } else if (betaling < combobox2.kosten) {

                JOptionPane.showMessageDialog(null, "Speler heeft niet genoeg betaald en kan dan niet ingeschreven worden voor het toernooi " + combobox2.naam);

                return;

            }

            ResultSet result4 = stat.executeQuery("SELECT SUM(Max_aantal_spelers) as Max_aantal_spelers FROM TAFEL WHERE Toernooi_ID = " + combobox2.id);

            while (result4.next()) {

                maxSpelers = result4.getInt("Max_aantal_spelers");

            }

            ResultSet result5 = stat.executeQuery("SELECT COUNT(*) as aantal FROM RoundRegistration WHERE ToernooiID = " + combobox2.id);

            while (result5.next()) {

                ingeschrevenSpelers = result5.getInt("aantal");

            }

            if (ingeschrevenSpelers >= maxSpelers) {

                JOptionPane.showMessageDialog(null, "Max aantal deelnemers voor dit toernooi is bereikt");
                return;
            }

            ResultSet result6 = stat.executeQuery("select speler_id from deelnemer where toernooi_id = " + combobox2.id);

            while (result6.next()) {

                obj.add(Integer.toString(result6.getInt("speler_id")));

            }

            String choosen;

            for (int i = 0; i < obj.size(); i++) {

                if (Integer.parseInt(obj.get(i)) == combobox1.id) {

                    choosen = obj.get(i);
                    System.out.println(obj.get(i));
                    JOptionPane.showMessageDialog(null, "Speler " + combobox1 + " heeft zich al ingeschreven voor het toernooi " + combobox2.naam);

                    return;
                }

            }

            JOptionPane.showMessageDialog(null, "Speler " + combobox1 + " heeft zich succesvol ingeschreven voor het toernooi " + combobox2);
            stat.setDouble(3, (betaling));
            int effectedRecords = stat.executeUpdate();

            System.out.println(obj.size());

            //Vullen_Spelers();
        } catch (SQLException e) {

            System.out.println("Fout bij het inschrijven: " + e);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(null, "Geen betaling ingevoerd");
        }

    }

    public inschrijvenForm() {
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

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(374, 413));
        setResizable(false);

        jLabel1.setText("Toernooi naam:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("              Nog geen speler?");

        jLabel3.setText("Speler naam: ");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Kosten gekozen toernooi:");

        jLabel5.setText("Betaling:");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setText("Inschrijven");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Inschrijven voor een toernooi");

        jButton2.setText("Speler aanmaken");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jButton1)
                .addGap(38, 38, 38)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Speler_Inschrijven();
        checkTafel3();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed

        ModelItem string = (ModelItem) jComboBox2.getSelectedItem();
        jLabel7.setText("€ " + Double.toString(string.kosten));

    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        spelersForm spelersForm = new spelersForm();
        spelersForm.spelers_overzicht();
        spelersForm.setVisible(true);

    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(inschrijvenForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(inschrijvenForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(inschrijvenForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(inschrijvenForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new inschrijvenForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
