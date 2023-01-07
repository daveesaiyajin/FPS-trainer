/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import java.awt.*;

public class FPS_Panel extends JPanel {

    public int panelWidth;
    public int panelHeight;
    public int barHeight;

    public FPS_Status fpsStatus;
    public Font menuFont;
    public Font alertFont;

    /** Table of objects */
    private FlyingBalloon[] fBalloon;

    public FPS_Panel(int width, int height) {
        fpsStatus = new FPS_Status();
        fpsStatus.reset();
        menuFont = new Font("Dialog", Font.BOLD, 36);
        alertFont = new Font("Dialog", Font.BOLD, 92);

        this.panelWidth = width;
        this.panelHeight = height;
        barHeight = 50;

        restartFPSGame();

        /* Mouse action definition */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mEvent) {

                int mouseX = mEvent.getX(); // position X of the coursor
                int mouseY = mEvent.getY(); // position Y of the coursor

                /* Check if the menu bar was choosen */
                if (mouseX > (width - 150) && mouseY > (height - barHeight)) {
                    FPS_GPars.pause = !FPS_GPars.pause;
                    return;
                }

                /* Check if user choose Exit button */
                if(mouseX<300 && mouseY>(height-barHeight)){
                  if(FPS_GPars.pause){
                      System.exit(1);
                  }
            }

        });
    }

    /**
     * Nadpisz metodę odpowiedzialną za odrysowanie panelu - własne wypełnienie
     * pola graficznego gry, zgodnie z wybraną treścią.
     * 
     * @param gs
     */
    @Override
    protected void paintComponent(Graphics gs) {
        Graphics2D g = (Graphics2D) gs;
        // Set antyalisiang and texture smoothing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Draw the background
        g.drawImage(FPS_GPars.bgImage, 0, 0, null);

        // Na tle obiektu pierwszego planu
        for (int i = 0; i < fBalloon.length; i++) {
            fBalloon[i].calculatePathPos(FPS_GPars.MoveMODE);
            if (!fBalloon[i].hit)
                g.drawImage(fBalloon[i].icon, fBalloon[i].currX, sHeight - fBalloon[i].currY,
                        (int) (fBalloon[i].icon.getWidth(null) * (1.0 - fBalloon[i].currY / (double) sHeight)),
                        (int) (fBalloon[i].icon.getHeight(null) * (1.0 - fBalloon[i].currY / (double) sHeight)), null);
        }

        // Ustaw kolor dolnego paska Menu i narysuj pasek
        g.setColor(new Color(50, 30, 0));
        g.fillRect(0, sHeight - barHeight, sWidth, barHeight);
        // Ustaw kolor domyślny
        g.setColor(Color.white);
        // Ustaw czcionki do wypełnienia paska Menu
        g.setFont(menuFont);

        // Jeśli już wybrano Menu (czyli pausa) narysuj stosowną wersję paska Menu
        if (FPS_GPars.pause) {
            g.drawImage(FPS_GPars.menuGameImage, sWidth - 150, sHeight - barHeight - 30, null);
            g.setColor(Color.red);
            g.drawString("KONIEC GRY!", 10, sHeight - 10);
            g.setColor(Color.white);
            g.drawString("O GRZE...", 300, sHeight - 10);
            g.drawString("NOWA GRA!", 550, sHeight - 10);
            if (FPS_GPars.end) { // Czy wszystkie poziomy skończone - koniec gry
                g.setColor(Color.RED);
                // g.drawString("KONIEC?",500, sHeight-10);
                g.setFont(alertFont);
                DecimalFormat df = new DecimalFormat("#.##");
                g.drawString("KONIEC GRY! ", 170, sHeight / 2);
                g.drawString("CZAS RAZEM=" + df.format(gStatus.time) + "s", 10, sHeight / 2 + 100);
                g.setColor(Color.white);
                g.setFont(menuFont);
            }
            // Nie wybranu nic z menu - pokaż poziom i stan punktów w trakcie gry
        } else {
            g.drawString("POZIOM:", 10, sHeight - 10);

            g.drawString("" + gStatus.level, 200, sHeight - 10);
            g.drawString("PUNKTY:", 300, sHeight - 10);
            // Czy ukończono poziom - wskazano wszystkie obiekty pozciomu
            if (gStatus.points == FPS_GPars.noOfObjects) {
                if (!FPS_GPars.levelPause) {
                    long stopTime = System.currentTimeMillis();
                    GPars.levelTime = (stopTime - GPars.startTime) / 1000.0;
                    GPars.levelPause = true;
                }
                g.setColor(Color.RED);
                g.drawString("GRASZ DALEJ?", 500, sHeight - 10);
                g.setFont(alertFont);
                DecimalFormat df = new DecimalFormat("#.##");
                g.drawString("WYGRANA:" + df.format(FPS_GPars.levelTime) + "s", 150, sHeight / 2);
                g.setColor(Color.white);
                g.setFont(menuFont);
                // Jak nie zmień punkty jeśli stosowne
            } else
                g.drawString("" + gStatus.points, 500, sHeight - 10);
            // narysuj ikonę z napisem Menu
            g.drawImage(FPS_GPars.menuImage, sWidth - 150, sHeight - barHeight - 30, null);
        }
        // narysuj ikonę z logo
        g.drawImage(FPS_GPars.logoImage, sWidth - 180, sHeight - barHeight + 15, null);

    }

    public void restartFPSGame() {
        fpsStatus.reset();
        FPS_GPars.startTime = System.currentTimeMillis();
        FPS_GPars.pause = false;
    }
}
