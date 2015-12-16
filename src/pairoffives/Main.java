
package pairoffives;

import javax.swing.ImageIcon;

import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class Main {

    /**
     * @main
     */
    public static void main(String[] args) {
        System.out.println("");
       //Nieuw object "Hoofdscherm" 
        Hoofdscherm hoofdscherm = new Hoofdscherm();
        
        java.net.URL imageUrl = Main.class.getResource("/pairoffives/images/32.png");
        hoofdscherm.setIconImage(new ImageIcon(imageUrl).getImage());
        hoofdscherm.setTitle("Full House");
        hoofdscherm.setVisible(true);
        
        
        
    }
    
}
