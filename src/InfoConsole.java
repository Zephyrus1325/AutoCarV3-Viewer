import processing.core.PApplet;

public class InfoConsole {
    private PApplet p;
    private int posX, posY;

    private int throttleL = 0;
    private int throttleR = 0;

    private float speedL = 0;
    private float speedR = 0;

    private float setPointL = 0;
    private float setPointR = 0;

    public InfoConsole(int posX, int posY, PApplet p){
        this.posX = posX;
        this.posY = posY;
        this.p = p;
    }


}
