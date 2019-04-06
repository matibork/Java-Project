package ksztalty;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Mateusz
 */

// postanowilem zrezygnowac z pierwotnego planu i zrobic program troche inaczej
// przez co sa drobne poprawki 
// usuniete jest tlo gry aby rozgrywka byla bardziej przejrzysta i figury nie zlewaly sie z tlem
public class Ksztalty {
        static JFrame window = new JFrame();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        int screenWidth=Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight=Toolkit.getDefaultToolkit().getScreenSize().height;
        
        int gameWidth=800, gameHeight=600;
        
        int xCenter=(screenWidth-gameWidth)/2;
        int yCenter=(screenHeight-gameHeight)/2;       
        window.setLocation(xCenter, yCenter);
        
   
        int gameWidth2=800, gameHeight2=800;
        
        int xCenter2=(screenWidth-gameWidth2)/2;
        int yCenter2=(screenHeight-gameHeight2)/2;       
        
        
        
        
        JButton bGraj = new JButton("Graj");
        JButton bZakoncz = new JButton("Zakoncz");
        bGraj.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFrame oknoGry = new JFrame();
                oknoGry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Rys rys = new Rys(window);
                oknoGry.add(rys);
                oknoGry.setSize(800, 800);
                oknoGry.setVisible(true);
                window.setVisible(false);
                           
                oknoGry.setLocation(xCenter2, yCenter2);
            }
        });
        bZakoncz.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                window.dispose();
            }
        });
        
        window.setLayout(new GridLayout(2, 1));
        window.add(bGraj);
        window.add(bZakoncz);
        window.setSize(500, 500);
        window.setVisible(true);
    }
    
}
