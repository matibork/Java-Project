package ksztalty;

import java.awt.*;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

/**
 *
 * @author Mateusz
 */


public class Rys extends JPanel {

    JPanel thisPanel = this;
    JFrame menuFrame;
    JLabel counterLabel = new JLabel(), imgLabel = new JLabel(), pointsLabel = new JLabel(), comboLabel = new JLabel();
    JButton upButton = new JButton();
    JButton downButton = new JButton();
    JButton menuButton = new JButton("Menu");
    JButton startButton = new JButton("Start");
    JButton nextLevelButton = new JButton("Next Level");
    int points = 0, maxPoints = 0, combo = 0, maxCombo = 0, prevImageIndex, currentImageIndex;
    long startTime, lastChangeTime, interval = 3000;
    boolean started = false;
    

    /**
     *
     * @param menuFrame
     */
    public Rys(JFrame menuFrame) {
        this.menuFrame = menuFrame;
        initComponents();
    }

    private void initComponents() {
        downButton.setIcon(new javax.swing.ImageIcon(new ImageIcon("images\\zla.png").getImage()));
        upButton.setIcon(new javax.swing.ImageIcon(new ImageIcon("images\\dobra.png").getImage()));
        upButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (!started) {
                    return;
                }
                if (prevImageIndex == currentImageIndex) {
                    points++;
                    combo++;
                } else {
                    combo = 0;
                }
                updateLabels();
                next();
            }
        });
        downButton.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (!started) {
                    return;
                }
                if (prevImageIndex != currentImageIndex) {
                    points++;
                    combo++;
                } else {
                    combo = 0;
                }

                updateLabels();
                next();
            }
        });
        menuButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {

                menuFrame.setVisible(true);
                SwingUtilities.getWindowAncestor(thisPanel).dispose();

            }
        });
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                start();
            }
        });
        nextLevelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                nextLevel();
            }
        });
        nextLevelButton.setVisible(false);
        downButton.setVisible(false);
        upButton.setVisible(false);

        counterLabel.setHorizontalAlignment(JLabel.CENTER);
        pointsLabel.setHorizontalAlignment(JLabel.CENTER);
        comboLabel.setHorizontalAlignment(JLabel.CENTER);
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        updateLabels();

        setLayout(new GridLayout(3, 3));
        add(comboLabel);
        add(counterLabel);
        add(pointsLabel);
        add(startButton);
        add(imgLabel);
        add(nextLevelButton);
        add(downButton);
        add(menuButton);
        add(upButton);
        counterLabel.setText("30");
        imgLabel.setIcon(new javax.swing.ImageIcon(getRandomShape()));
    }
        // start gry
    private void start() {
        started = true;
        prevImageIndex = currentImageIndex;
        next();
        Date date = new Date();
        startTime = date.getTime();
        startButton.setVisible(false);
        upButton.setVisible(true);
        downButton.setVisible(true);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new UpdateCounterTask(), 0, 1000);
        timer.schedule(new StopTask(), 30000);
    }
        // pokazanie przycisku do nowego poziomu
    private void stop() {
        downButton.setVisible(false);
        upButton.setVisible(false);
        if (interval > 1000) {
            nextLevelButton.setVisible(true);
        }
    }

    private void next() {
        prevImageIndex = currentImageIndex;
        imgLabel.setIcon(new javax.swing.ImageIcon(getRandomShape()));
        Date date = new Date();
        lastChangeTime = date.getTime();
        Timer timer = new Timer();
        timer.schedule(new CheckFor3SecondsTask(), interval);
    }
    // ustawienie nowego poziomu
    private void nextLevel() {
        interval -= 1000;
        points = 0;
        combo = 0;
        updateLabels();
        nextLevelButton.setVisible(false);
        start();
    }
    // aktualizacja punktów i serii
    private void updateLabels() {
        if (points > maxPoints) {
            maxPoints = points;
        }
        if (combo > maxCombo) {
            maxCombo = combo;
        }
        pointsLabel.setText("Points: " + String.valueOf(points) + " / Max: " + String.valueOf(maxPoints));
        comboLabel.setText("Combo: " + String.valueOf(combo) + " / Max: " + String.valueOf(maxCombo));

    }
    // losowanie figury
    private Image getRandomShape() {
        Random rand = new Random();
        int chance = rand.nextInt(100);
        if (chance < 10) {
            currentImageIndex = prevImageIndex;
        } else {
            currentImageIndex = rand.nextInt(6);
        }
        Image img = null;
        switch (currentImageIndex) {
            case 0: {
                img = new ImageIcon("images\\k150.png").getImage();
                break;
            }
            case 1: {
                img = new ImageIcon("images\\k250.png").getImage();
                break;
            }
            case 2: {
                img = new ImageIcon("images\\r150.png").getImage();
                break;
            }
            case 3: {
                img = new ImageIcon("images\\r250.png").getImage();
                break;
            }
            case 4: {
                img = new ImageIcon("images\\t150.png").getImage();
                break;
            }
            case 5: {
                img = new ImageIcon("images\\t250.png").getImage();
                break;
            }
        }//koniec switcha
        return img;
    }

    class UpdateCounterTask extends TimerTask {

        public UpdateCounterTask() {
        }

        @Override
        public void run() {
            try {
                Date date = new Date();
                long time = date.getTime();
                if (time - startTime >= 30050) //opoznienie - przy 30000 staje na 1s
                {
                    return;
                }
                counterLabel.setText(String.valueOf((startTime - time) / 1000 + 30) + " (" + String.valueOf(interval / 1000) + "s)");
            } catch (Exception ex) {
                System.out.println("error running thread " + ex.getMessage());
            }
        }
    }
    // sprawdza czy minely 3 sekundy 
    class CheckFor3SecondsTask extends TimerTask {

        public CheckFor3SecondsTask() {
        }

        @Override
        public void run() {
            try {

                Date date = new Date();
                long time = date.getTime();
                if (time - startTime >= 30000) {
                    return;
                }

                if (time - lastChangeTime >= interval) {
                    combo = 0;
                    updateLabels();
                    next();
                }
            } catch (Exception ex) {
                System.out.println("error running thread " + ex.getMessage());
            }
        }
    }

    class StopTask extends TimerTask {

        public StopTask() {
        }

        @Override
        public void run() {
            try {
                stop();
            } catch (Exception ex) {
                System.out.println("error running thread " + ex.getMessage());
            }
        }
    }
}
