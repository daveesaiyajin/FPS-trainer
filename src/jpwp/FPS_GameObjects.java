package jpwp;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Constructor of the objects that will pop-up on the screen
 */

public class FPS_GameObjects {

    /** Starting point of the object - axis X */
    public int startPosX;
    /** Starting point of the object - axis Y */
    public int startPosY;
    /** Current position of the object on axis X */
    public int currX;
    /** Current position of the object on axis Y */
    public int currY;
    /** Width of the object */
    public int objectWidth;
    /** Height of the object */
    public int objectHeight;

    /** Krok przesunięcia obiektu z dołu do góry */
    public int dy;
    /** Kąt w funkcji sinus opisującej ruch balonu */
    private double angle;
    /** Amplituda w funkcji sinus opisującej ruch balonu */
    public int ampl;
    /** Częstotliwość funkcji sinus opisującej ruch balonu */
    public double freq;
    /** Kolor balonu */
    public int color;
    /** Omega 2Pi*f */
    public final static double w = 2 * Math.PI;
    /** Szerokość pola graficznego */
    public int sWidth;
    /** Wysokość pola graficznego */
    public int sHeight;

    /** Czy trafiono balon */
    public boolean hit;
    /** Ikona obiektu - balonu */
    public Image icon;

    /**
     * Konstruktor - ustawienie parametrów obiektu, wylosowanie koloru balonu
     * 
     * @param x      początkowa współrzędna x
     * @param y      początkowa współrzędna y
     * @param ampl   amplituda sinus (ruch balonu)
     * @param freq   częstotliwość w funkcji sinus
     * @param images tablica ikon z balonami
     */
    public FPS_GameObjects(int x, int y, int ampl, double freq, Image[] images) {
        this.startPosX = x;
        this.startPosY = y;
        currX = x;
        currY = y;
        this.dy = 10;
        sWidth = 1920;
        sHeight = 1080;
        hit = false;

        this.ampl = ampl;
        this.freq = freq;
        // losujemy kolor balonu
        color = (int) (0.4 + Math.random() * images.length);
        if (color >= images.length)
            color = images.length - 1;
        icon = images[color];
        objectWidth = icon.getWidth(null);
        objectHeight = icon.getHeight(null);
        // ustawiamy pulsację w funkcji sinus 2 Pi f
        setOmega(this.freq);

    }

    /**
     * Balon trafiony - ustaw stan i odtwórz dźwięk
     */
    public void setHit() {
        if (!hit) {
            hit = true;
            playSound(new File("sounds/balloon_boom.wav"));
        }
    }// setHit()

    /**
     * Ustaw pozycję balonu
     * 
     * @param cX współrzędna x
     * @param cY współrzędna y
     */
    public void setPosition(int cX, int cY) {
        currX = cX;
        currY = cY;
    }// setPosition()

    /**
     * Ustaw rozmiar pola graficznego
     * 
     * @param gWidth  szerokość
     * @param gHeight wysokość
     */
    public void setScreenSize(int gWidth, int gHeight) {
        sWidth = gWidth;
        sHeight = gHeight;
    }

    /**
     * Ustaw pozycję y obiektu/balonu
     * 
     * @param cY
     */
    public void setYPos(int cY) {
        currY = cY;
    }// setYPos()

    /**
     * Pobierz pozycję balonu
     * 
     * @return pozycja balonu
     */
    public Point getPosition() {
        return new Point(currX, currY);
    }// getPosition()

    /**
     * Ustaw 2 Pi f
     * 
     * @param freq
     */
    public void setOmega(double freq) {
        angle = w * freq;
    }// setOmega()

    /**
     * Metoda obliczania pozycji balonu - symulacja ruchu
     * 
     * @param mode Tryb określający ruch: 1 - liniowo (prawie) do góry
     *             2 - sinus do góry
     */
    public void calculatePathPos(int mode) {
        int tmpX = 0;
        switch (mode) {
            case 1: // liniowo
                currY = currY + dy;
                if (currY > sHeight) {
                    currY = 0;
                }
                tmpX = 0;
                currX = startPosX + tmpX;
                break;
            case 2:// sinus
                currY = currY + dy;
                if (currY > sHeight) {
                    currY = 0;
                }
                tmpX = (int) (ampl * Math.sin(angle * currY));
                currX = startPosX + tmpX;

                break;
            default:
                break;
        }

    }// calculatePathPos()

    /**
     * Funkcja określająca czy określone współrzędne (np. klik myszką
     * są w obrębie obiektu/balonu
     * 
     * @param x wsp. x
     * @param y wsp. y
     * @return true jeśli obszar balonu zawiera dany punkt
     */
    public boolean containsPoint(int x, int y) {
        // skalowanie rozmiaru balonu wraz z ruchem z dołu w górę
        scaleWidthHeight((double) sHeight);
        if (x >= currX && x < currX + objectWidth) {
            if (y >= (sHeight - currY) && y < (sHeight - currY + objectHeight)) {
                return true;
            }
        }

        return false;
    }// containsPoint()

    /**
     * Skalowanie rozmiaru balonu wraz z ruchem z dołu w górę
     * 
     * @param scale
     */
    public void scaleWidthHeight(double scale) {
        objectWidth = (int) (icon.getWidth(null) * (1.0 - currY / scale));
        objectHeight = (int) (icon.getHeight(null) * (1.0 - currY / scale));
    }// scaleWidthHeight()

    /**
     * Funkcja odtwarzania dźwięku z pliku
     * 
     * @param f - obiekt klasy File reprezentujący ścieżkę do pliku MP3
     */
    public static synchronized void playSound(final File f) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }// playSound()

}
// koniec class FlyingBallonn
