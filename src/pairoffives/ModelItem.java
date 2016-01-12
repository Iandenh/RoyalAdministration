/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pairoffives;

/**
 *
 * @author Tony
 */
public class ModelItem {
    
    
    public int id;
    public String naam;
    public String achternaam;
    public String adres;
    public String postcode;
    public String woonplaats;
    public int telnr;
    public String email;
    public int gewonnen;
    public int verloren;
    public double rating;
    public int ingeschreven;
    public double kosten;
    public int toernooi_id;
    public int speler_id;
    public double betaling;
    public int maxDeelnemers;
    public int minRatingpunten;
    public int ratingpunten;
    
    
    
    
    

    @Override
    public String toString() {
        //Een JList of JComboBox laat deze return waarde zien
        return naam;
       
    }
    
}
