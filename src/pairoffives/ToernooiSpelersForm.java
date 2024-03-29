
package pairoffives;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ian
 */
public class ToernooiSpelersForm extends javax.swing.JFrame {

    DefaultTableModel tablemodel = new DefaultTableModel();
    protected int toernooiId;

    /**
     * Creates new form ToernooiSpelersForm
     *
     * @param toernooiId
     */
    public ToernooiSpelersForm(int toernooiId) {
        this.toernooiId = toernooiId;
        initComponents();
        spelerOverzicht();

    }

    private void spelerOverzicht() {
        int id;
        String adres;
        String naam;
        String email;
        int betaling;
        int deelnemer_id;
        try {

            Connection conn = SimpleDataSourceV2.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT speler.id, deelnemer.id as deelnemer_id, naam, adres, email, betaling FROM deelnemer inner join speler ON deelnemer.speler_id = speler.id WHERE toernooi_id = ?");

            stat.setInt(1, toernooiId);
            ResultSet result = stat.executeQuery();

            // refreshing table na het wijzigen van een speler.
            tablemodel.setColumnCount(0);
            tablemodel.setNumRows(0);
            jTable1.setModel(tablemodel);

            tablemodel.addColumn("id");
            tablemodel.addColumn("naam");
            tablemodel.addColumn("adres");
            tablemodel.addColumn("email");
            tablemodel.addColumn("betaling");
            tablemodel.addColumn("deelnemer_id");

            while (result.next()) {

                id = result.getInt("id");
                adres = result.getString("adres");
                naam = result.getString("naam");
                email = result.getString("email");
                betaling = result.getInt("betaling");
                deelnemer_id = result.getInt("deelnemer_id");

                tablemodel.addRow(new Object[]{id, naam, adres, email, betaling, deelnemer_id});

            }
            jTable1.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
            jTable1.removeColumn(jTable1.getColumnModel().getColumn(5));
            jTable1.setModel(tablemodel);

        } catch (Exception ex) {

            System.out.println(ex);

        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        jLabel1 = new javax.swing.JLabel();
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Spelers");

        deleteButton.setText("Verwijderen");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(deleteButton)))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deleteButton)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        verwijderDeelnemer();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void verwijderDeelnemer() {
        try {

            Connection connect = SimpleDataSourceV2.getConnection();

            PreparedStatement pstm = connect.prepareStatement("delete from deelnemer where id=?");
            int row = jTable1.getSelectedRow();
            String Table_click = (jTable1.getModel().getValueAt(row, 5).toString());
            pstm.setString(1, Table_click);

            pstm.executeUpdate();

            //Refreshing spelers overzicht table
            spelerOverzicht();

            JOptionPane.showMessageDialog(null, "Deelnemer verwijderd");

            pstm.close();

        } catch (Exception ex) {

            System.out.println(ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
