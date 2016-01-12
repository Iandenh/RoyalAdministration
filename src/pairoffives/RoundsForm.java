/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pairoffives;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

/**
 *
 * @author Thomas
 */
public class RoundsForm extends javax.swing.JFrame {

    Connection conn = null;
    private int ToernooiID = 0;
    private final List<RoundRegistrationObject> roundRegistrationObjectsList = new ArrayList<RoundRegistrationObject>() {
    };
    private final List<Integer> roundsList = new ArrayList<Integer>() {
    };
    private final Map<Integer, List<Integer>> tablesPerRoundMap = new HashMap<Integer, List<Integer>>() {
    };
    private final Map<Integer, Deelnemer> participantsList = new HashMap<Integer, Deelnemer>() {
    };
    private final Map<String, List<Integer>> tableParticipantsList = new HashMap<String, List<Integer>>() {
    };

    // Hi nakijker, meeste is gecomment, als het niet gecomment is dan was er geen tijd meer voor
    // Groetjes Thomas
    
    /**
     * Blub
     */
    public RoundsForm() {
        initComponents();
    }

    public RoundsForm(Integer toernooiID, String toernooiNaam) {
        // Deze constructor wordt gebruikt om deze class aan te roepen vanaf de toernooiForm.
        initComponents();

        // Zetten van informatie
        jLabel4.setText(toernooiNaam);
        this.ToernooiID = toernooiID;

        // Data ophalen en in de properties stoppen
        getData(toernooiID);

        // Vanuit de properties, de lijsten vullen.
        fillFields();

        // Sluit zooi
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private boolean getData(int toernooiID) {
        boolean value = false; //This boolean indicates if the process succeeded or failed.

        // ++ RoundRegistrationObject ++
        // Ophalen van alle data uit de RoundRegistration tabel en in een property zetten
        try {

            conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM RoundRegistration WHERE ToernooiID ='" + toernooiID + "'");

            while (result.next()) {

                int ID = result.getInt("ID");
                int ronde = result.getInt("Ronde");
                int deelnemerID = result.getInt("DeelnemerID");
                boolean uitslag = result.getBoolean("Uitslag");

                roundRegistrationObjectsList.add(new RoundRegistrationObject(ID, ronde, deelnemerID, uitslag));
            }

        } catch (Exception ex) {

            System.out.println(ex);
            value = false;

        }
         // -- RoundRegistrationObject --

        // ++ Rondes ++
        for (RoundRegistrationObject item : roundRegistrationObjectsList) {
            if (roundsList.contains(item.Ronde) == false) {
                roundsList.add(item.Ronde);
            }
        }

        // -- Rondes --
        // ++ Tafels ++
        // Vullen van de tablesPerRoundMap property om later te kunnen gebruiken bij het selecteren.
        for (int ronde : roundsList) {

            List<Integer> tafelIDs = new ArrayList<Integer>() {
            };

            try {

                conn = SimpleDataSourceV2.getConnection();

                Statement stat = conn.createStatement();
                ResultSet result = stat.executeQuery("SELECT * FROM RoundRegistration WHERE ToernooiID ='" + toernooiID + "' AND Ronde='" + ronde + "'");

                while (result.next()) {
                    int ID = result.getInt("tafelID");

                    if (!tafelIDs.contains(ID)) {
                        tafelIDs.add(ID);
                    }
                }

            } catch (Exception ex) {

                System.out.println(ex);
                value = false;
            }

            // Sorteren van de tafels (1 t/m x)
            Collections.sort(tafelIDs);

            tablesPerRoundMap.put(ronde, tafelIDs);
        }
        // -- Tafels --

        // ++ Deelnemers per tafel per ronde ++
        // Vullen van de tablesPerRoundMap property om later te kunnen gebruiken bij het selecteren.
        for (int ronde : roundsList) {

            List<Integer> tafels = tablesPerRoundMap.get(ronde);

            for (int tafel : tafels) {

                List<Integer> deelnemerIDs = new ArrayList<Integer>() {
                };

                try {

                    conn = SimpleDataSourceV2.getConnection();

                    Statement stat = conn.createStatement();
                    ResultSet result = stat.executeQuery("SELECT * FROM RoundRegistration WHERE Uitslag IS NULL AND ToernooiID ='" + toernooiID + "' AND Ronde='" + ronde + "' AND TafelID ='" + tafel + "'");

                    while (result.next()) {
                        int ID = result.getInt("DeelnemerID");

                        if (!deelnemerIDs.contains(ID)) {
                            deelnemerIDs.add(ID);
                        }
                    }

                } catch (Exception ex) {

                    System.out.println(ex);
                    value = false;
                }

                tableParticipantsList.put(ronde + "_" + tafel, deelnemerIDs);

            }
        }

        // -- Deelnemers per tafel per ronde --
        // ++ Deelnemers opslaan in lijst ++\    
        Map<Integer, Integer> spelerIDs = new HashMap<Integer, Integer>() {
        };
        try {

            conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM Deelnemer WHERE toernooi_ID ='" + toernooiID + "'");

            while (result.next()) {
                int ID = result.getInt("ID");
                int spelerID = result.getInt("speler_id");

                if (spelerIDs.get(ID) == null) {
                    spelerIDs.put(ID, spelerID);
                }
            }

        } catch (Exception ex) {

            System.out.println(ex);
            value = false;
        }

        //for (int spelerID : spelerIDs.values()){            
        for (Map.Entry<Integer, Integer> entry : spelerIDs.entrySet()) {
            try {

                conn = SimpleDataSourceV2.getConnection();

                Statement stat = conn.createStatement();
                ResultSet result = stat.executeQuery("SELECT * FROM Speler WHERE ID ='" + entry.getValue() + "'");

                while (result.next()) {
                    Deelnemer deelnemer = new Deelnemer();
                    deelnemer.DeelnemerID = entry.getKey();
                    deelnemer.ID = result.getInt("ID");
                    deelnemer.Naam = result.getString("Naam");

                    if (participantsList.get(entry.getKey()) == null) {
                        participantsList.put(entry.getKey(), deelnemer);
                    }
                }

            } catch (Exception ex) {

                System.out.println(ex);
                value = false;
            }
        }
        // -- Deelnemers opslaan in lijst --

        return value;
    }

    private boolean getRoundLastRound(int huidigeRonde) {
        // Is huidig geselecteerde ronde de laatste ronde?
        boolean value = false;

        try {
            conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT COUNT(DISTINCT TafelID) AS Tafels FROM RoundRegistration WHERE ToernooiID = " + ToernooiID + " AND Ronde = " + huidigeRonde);

            if (result.next()) {
                value = result.getInt("Tafels") == 1;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return value;
    }
    
    private void setGewonnenVerloren(int deelnemerID, boolean status) {
        int aantal = 0;
        int spelerID = 0;
        // Ophalen info
        try {

                conn = SimpleDataSourceV2.getConnection();

                String queryString;
                
                if (status) { // gewonnen 
                    queryString = "SELECT S.Verloren AS Aantal, S.ID FROM Speler AS S Join Deelnemer AS D ON D.Speler_ID = S.ID WHERE D.ID = " + deelnemerID;
                } else { // verloren
                    queryString = "SELECT S.Gewonnen AS Aantal, S.ID FROM Speler AS S Join Deelnemer AS D ON D.Speler_ID = S.ID WHERE D.ID = " + deelnemerID;
                }
                
                Statement stat = conn.createStatement();
                ResultSet result = stat.executeQuery(queryString);

                if (result.next()) {
                    aantal = result.getInt("aantal");
                    spelerID = result.getInt("ID");
                }

            } catch (Exception ex) {

                System.out.println(ex);
            }        
        
        // Zetten info
        
        try {
            conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();
            String query;
            
            if (status) { 
               query = "UPDATE Speler SET Gewonnen = ? WHERE ID = ?";
            } else {
               query = "UPDATE Speler SET Verloren = ? WHERE ID = ?"; 
            }
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setInt(1, aantal + 1);
            preparedStmt.setInt(2, spelerID);
            preparedStmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private List<Integer> getAvailableTable(int ronde) {
        // Beschikbare tafel halen
        List<Integer> value = new ArrayList<Integer>() {};
        Map<Integer, Integer> tafels = new HashMap<Integer, Integer>() {};
        Map<Integer, Integer> huidigeTafels = new HashMap<Integer, Integer>() {};
                
        try {

            conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM Tafel WHERE Toernooi_ID = " + ToernooiID);

            while (result.next()) {
                int ID = result.getInt("ID");
                int maxAantalSpelers = result.getInt("Max_aantal_spelers");

                if (tafels.get(ID) == null) {
                    tafels.put(ID, maxAantalSpelers);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        try {

            conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT TafelID, COUNT(TafelID) AS Aantal FROM RoundRegistration WHERE ToernooiID = " + ToernooiID + " GROUP BY TafelID");

            while (result.next()) {
                int ID = result.getInt("tafelID");
                int aantalSpelers = result.getInt("Aantal");
                if (huidigeTafels.get(ID) == null) {
                    huidigeTafels.put(ID, aantalSpelers);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        for (Map.Entry<Integer, Integer> entry : huidigeTafels.entrySet())
        {
            if (tafels.get(entry.getKey()) != null) {
                if (entry.getValue() >= tafels.get(entry.getKey())) {
                    tafels.remove(entry.getKey());
                }
            }
        }
        
        for (Map.Entry<Integer, Integer> entry : tafels.entrySet())
        {
            value.add((int) entry.getKey());
        }
        
        Collections.sort(value);
        
        return value;
    }

    private Map<Integer, Integer> getChosenWinners(int ronde) {
        Map<Integer, Integer> value = new HashMap<Integer, Integer>() {};
        
        try {

            conn = SimpleDataSourceV2.getConnection();

            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT (SELECT ID FROM RoundRegistration WHERE Ronde = " + ronde + " AND Uitslag = 1 LIMIT 1) AS '1', (SELECT ID FROM RoundRegistration WHERE Ronde = " + ronde + " AND Uitslag = 2 LIMIT 1) AS '2', (SELECT ID FROM RoundRegistration WHERE Ronde = " + ronde + " AND Uitslag = 3 LIMIT 1) AS '3'");

            if (result.next()) {
                value.put(1, result.getInt("1"));
                value.put(2, result.getInt("2"));
                value.put(3, result.getInt("3"));
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return value;
    }
    
    private void fillFields() {
        // ++ Rondes ++
        // Vullen van de jComboBox1 (rondes)
        DefaultComboBoxModel rondes = new DefaultComboBoxModel();

        for (Integer item : roundsList) {
            rondes.addElement(item);
        }

        jComboBox1.setModel(rondes);
        // -- Rondes --

        if (jComboBox1.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "Er zijn geen ronden beschikbaar voor dit toernooi!");

            return;
        }

        // ++ Tafels ++
        // Zetten van de tafels aan de hand van het eerst geselecteerde item van de jComboBox1 (rondes).
        int firstRound = 0;
        if (roundsList.size() > 0) {
            firstRound = (int) jComboBox1.getSelectedItem();
        }

        if (firstRound > 0) {
            // Tafels zetten
            DefaultComboBoxModel tafels = new DefaultComboBoxModel();
            List<Integer> tafelIDs = tablesPerRoundMap.get(firstRound);

            for (Integer ID : tafelIDs) {
                tafels.addElement(ID);
            }

            jComboBox2.setModel(tafels);
        }
        // -- Tafels --

        // Zet speler tabel
        int ronde = (int) jComboBox1.getSelectedItem();
        int tafel = (int) jComboBox2.getSelectedItem();

        DefaultComboBoxModel spelers = new DefaultComboBoxModel();

        List<Integer> deelnemers = tableParticipantsList.get(ronde + "_" + tafel);

        for (int ID : deelnemers) {
            Deelnemer deelnemer = participantsList.get(ID);
            spelers.addElement(deelnemer);
        }

        jList1.setModel(spelers);
        // -- Spelers zetten --
    }

    private void setButtonText() {
        // Button text zetten
        int huidigeRonde = (int) jComboBox1.getSelectedItem();
        boolean lastRound = getRoundLastRound(huidigeRonde);
        if (lastRound) {
            Map<Integer, Integer> winners = getChosenWinners(huidigeRonde);
            if (winners.get(1) == 0){
                jButton1.setText("Promoveren tot winnaar van het toernooi");
            } else if (winners.get(2) == 0) {
                jButton1.setText("Promoveren tot tweede van het toernooi");
            } else if (winners.get(3) == 0) {
                jButton1.setText("Promoveren tot derde van het toernooi");
            } else {
                jButton1.setEnabled(false);
            }
            
        } else {
            jButton1.setText("Promoveren tot ronde " + (huidigeRonde + 1));
        }

    }
    
    private class Deelnemer {

        int ID = 0;
        int DeelnemerID = 0;
        String Naam = "";

        private Deelnemer() {

        }

        private Deelnemer(int ID, int deelnemerID, String naam, int ronde, int tafelID) {
            this.ID = ID;
            this.DeelnemerID = deelnemerID;
            this.Naam = naam;
        }

        @Override
        public String toString() {
            //Een JList of JComboBox laat deze return waarde zien
            return this.Naam;
        }

    }

    private class RoundRegistrationObject {

        int ID = 0;
        int Ronde = 0;
        int DeelnemerID = 0;
        boolean Uitslag = false;

        private RoundRegistrationObject(int ID, int ronde, int deelnemerID, boolean uitslag) {
            this.ID = ID;
            this.Ronde = ronde;
            this.DeelnemerID = deelnemerID;
            this.Uitslag = uitslag;
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

        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rounds");
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setMaximumSize(new java.awt.Dimension(400, 300));
        setMinimumSize(new java.awt.Dimension(400, 300));
        setResizable(false);
        getContentPane().setLayout(null);

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(110, 40, 97, 20);

        jLabel1.setText("Ronde:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(50, 40, 40, 14);

        jLabel2.setText("Tafel:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(60, 70, 30, 14);

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox2);
        jComboBox2.setBounds(110, 70, 97, 20);

        jLabel3.setText("Toernooi naam: ");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 20, 90, 14);

        jLabel4.setText("Toernooi Naam");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(110, 20, 100, 14);

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setMaximumSize(new java.awt.Dimension(28, 80));
        jList1.setMinimumSize(new java.awt.Dimension(28, 80));
        jList1.setPreferredSize(new java.awt.Dimension(28, 80));
        jScrollPane1.setViewportView(jList1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(110, 100, 100, 130);

        jLabel5.setText("Spelers:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(50, 100, 40, 14);

        jButton1.setText("Promoveren tot ronde 0");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(217, 207, 147, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // Tafels zetten

        int geselecteerdeRonde = (int) jComboBox1.getSelectedItem();

        DefaultComboBoxModel tafels = new DefaultComboBoxModel();
        List<Integer> tafelIDs = tablesPerRoundMap.get(geselecteerdeRonde);

        for (Integer ID : tafelIDs) {
            tafels.addElement(ID);
        }

        jComboBox2.setModel(tafels);

        // Zet speler tabel
        int ronde = (int) jComboBox1.getSelectedItem();
        int tafel = (int) jComboBox2.getSelectedItem();

        DefaultComboBoxModel spelers = new DefaultComboBoxModel();

        List<Integer> deelnemers = tableParticipantsList.get(ronde + "_" + tafel);

        for (int ID : deelnemers) {
            Deelnemer deelnemer = participantsList.get(ID);
            spelers.addElement(deelnemer);
        }

        jList1.setModel(spelers);

        setButtonText();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        setButtonText();

        // Zet speler tabel
        int ronde = (int) jComboBox1.getSelectedItem();
        int tafel = (int) jComboBox2.getSelectedItem();

        DefaultComboBoxModel spelers = new DefaultComboBoxModel();

        List<Integer> deelnemers = tableParticipantsList.get(ronde + "_" + tafel);

        for (int ID : deelnemers) {
            Deelnemer deelnemer = participantsList.get(ID);
            spelers.addElement(deelnemer);
        }

        jList1.setModel(spelers);

    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jList1.getSelectedIndex() != -1) {

            Deelnemer selectedItem = (Deelnemer) jList1.getSelectedValue();

            int huidigeRonde = (int) jComboBox1.getSelectedItem();
            int volgendeRonde = huidigeRonde + 1;
            int tafelID = 0;
            boolean lastRound = getRoundLastRound(huidigeRonde);
            int reply;

            if (lastRound) {
                reply = JOptionPane.showConfirmDialog(null, "Weet je zeker dat je de speler '" + selectedItem.Naam + "' tot winnaar van het toernooi wilt promoveren?", "Weet je het zeker", JOptionPane.YES_NO_OPTION);
            } else {
                reply = JOptionPane.showConfirmDialog(null, "Weet je zeker dat je de speler '" + selectedItem.Naam + "' wilt promoveren tot de volgende ronde?", "Weet je het zeker", JOptionPane.YES_NO_OPTION);
            }

            if (reply == JOptionPane.YES_OPTION) {
                ListModel lm = jList1.getModel();

                // Alle spelers hun uitslag updaten
                if (lastRound == false) {
                    try {
                        for (int i = 0; i < lm.getSize(); i++) {
                            Deelnemer deelnemer = (Deelnemer) lm.getElementAt(i);

                            conn = SimpleDataSourceV2.getConnection();

                            String query = "UPDATE RoundRegistration SET Uitslag = ? WHERE DeelnemerID = ?";
                            PreparedStatement preparedStmt = conn.prepareStatement(query);

                            if (deelnemer.DeelnemerID == selectedItem.DeelnemerID) {
                                preparedStmt.setInt(1, 1);
                                setGewonnenVerloren(deelnemer.DeelnemerID, true);
                            } else {
                                preparedStmt.setInt(1, 0);
                                setGewonnenVerloren(deelnemer.DeelnemerID, false);
                            }
                            preparedStmt.setInt(2, deelnemer.DeelnemerID);
                            preparedStmt.executeUpdate();
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                } else {
                    // Ik weet dat dit zielige code is
                    int uitslag = 0;
                    Map<Integer, Integer> winners = getChosenWinners(huidigeRonde);
                    if (winners.get(1) == 0){
                        uitslag = 1;
                    } else if (winners.get(2) == 0) {
                        uitslag = 2;
                    } else if (winners.get(3) == 0) {
                        uitslag = 3;
                    }
                    
                    try {
                        conn = SimpleDataSourceV2.getConnection();

                        String query = "UPDATE RoundRegistration SET Uitslag = ? WHERE DeelnemerID = ?";
                        PreparedStatement preparedStmt = conn.prepareStatement(query);

                        preparedStmt.setInt(1, uitslag);
                        preparedStmt.setInt(2, selectedItem.DeelnemerID);
                        preparedStmt.executeUpdate();
                        
                        setGewonnenVerloren(selectedItem.DeelnemerID, true);
                        
                        if (uitslag == 3) {
                            for (int i = 0; i < lm.getSize(); i++) {
                                Deelnemer deelnemer = (Deelnemer) lm.getElementAt(i);

                                conn = SimpleDataSourceV2.getConnection();

                                Statement stat2 = conn.createStatement();
                                String query2 = "UPDATE RoundRegistration SET Uitslag = ? WHERE DeelnemerID = ?";
                                PreparedStatement preparedStmt2 = conn.prepareStatement(query2);

                                if (deelnemer.DeelnemerID == selectedItem.DeelnemerID) {
                                    preparedStmt2.setInt(1, 3);
                                } else {
                                    preparedStmt2.setInt(1, 0);
                                    setGewonnenVerloren(deelnemer.DeelnemerID, false);
                                }
                                preparedStmt2.setInt(2, deelnemer.DeelnemerID);
                                preparedStmt2.executeUpdate();
                            }
                        }
                        
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    
                }

                // Als het de laatste ronde is, hoef je geen nieuwe ronde meer aan te maken!
                if (lastRound == false) {
                    // Eerstevolgende beschikbare Tafel ID ophalen
                    try {

                        conn = SimpleDataSourceV2.getConnection();

                        Statement stat = conn.createStatement();
                        ResultSet result = stat.executeQuery("SELECT T.* FROM TAFEL AS T JOIN RoundRegistration AS RR ON RR.TafelID = T.ID WHERE RR.RONDE = " + volgendeRonde + " AND ToernooiID = " + ToernooiID + " GROUP BY T.ID HAVING COUNT(RR.ID) < T.Max_Aantal_spelers LIMIT 1");

                        if (result.next()) {
                            int ID = result.getInt("ID");
                            tafelID = ID;
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }

                    // Geselecteerde speler aan een nieuwe tafel toevoegen                  
                    try {
                        String prepSqlStatement = "INSERT INTO RoundRegistration (Ronde, ToernooiID, TafelID, DeelnemerID) VALUES (?, ?, ?, ?)";
                        PreparedStatement stat = conn.prepareStatement(prepSqlStatement);
                        stat.setInt(1, volgendeRonde);
                        stat.setInt(2, ToernooiID);
                        if (tafelID == 0) {
                            List<Integer> tafels = getAvailableTable(volgendeRonde);
                            if (tafels.size() >= 1){
                                stat.setInt(3, tafels.get(0));
                            } else {
                                JOptionPane.showMessageDialog(null, "Er zijn geen beschikbare tafels meer!");
                            }
                        } else {
                            stat.setInt(3, tafelID);
                        }
                        stat.setInt(4, selectedItem.DeelnemerID);

                        stat.executeUpdate();

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }

                getData(ToernooiID);
                fillFields();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Je moet eerst een speler selecteren!");
        }
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
            java.util.logging.Logger.getLogger(RoundsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoundsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoundsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoundsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RoundsForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
