import processing.core.PApplet;
import processing.core.PConstants;


public class Button {
    private int posX, posY, width, height;
    private PApplet p;
    private boolean pressed = false;
    private boolean ignoreInput = false;
    private int colorNormal = 0xFFA0A0A0;
    private int colorHover = 0xFF606060;
    private int colorClicked = 0xFF303030;

    public Button(int posX, int posY, int width, int height, PApplet p){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.p = p;
    }

    public Button(int posX, int posY, int width, int height, int normalColor, int hoverColor, int pressedColor, PApplet p){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.colorNormal = normalColor;
        this.colorClicked = pressedColor;
        this.colorHover = hoverColor;
        this.p = p;
    }

    public void update(){
        int color = colorNormal;
        if(p.mouseX > posX && p.mouseX < posX + width && p.mouseY > posY && p.mouseY < posY + height){
            color = colorHover;
            p.cursor(PConstants.HAND);
            if(p.mousePressed){
                if(!pressed && !ignoreInput){
                    pressed = true;
                    ignoreInput = true;
                } else {
                    pressed = false;
                }
                color = colorClicked;
            } else {
                ignoreInput = false;
            }
        }

        p.fill(color);
        p.rectMode(PConstants.CORNER);
        p.rect(posX,posY,width,height);
    }

    boolean isPressed(){
        return pressed;
    }

    void setColorNormal(int color){
        this.colorNormal = color;
    }


}
