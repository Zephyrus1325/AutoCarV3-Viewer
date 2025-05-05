import processing.core.PApplet;
import processing.core.PConstants;


public class Switch {

    private PApplet p;

    private int posX, posY, width, height;

    private final int colorOn_normal = 0xFF00FF00;
    private final int colorOn_hover = 0xFF77FF77;
    private final int colorOn_click = 0xFF00AA00;
    private final int colorOn_inactive = 0x4000FF00;

    private final int colorOff_normal = 0xFFFF0000;
    private final int colorOff_hover = 0xFFFF7777;
    private final int colorOff_click = 0xFFAA0000;
    private final int colorOff_inactive = 0x40FF0000;

    private Button onButton;
    private Button offButton;

    private boolean isActive = false;
    private boolean hadChange = false;

    public Switch(int posX, int posY, int width, int height, PApplet p){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.p = p;
        offButton = new Button(posX,posY,width/2, height, colorOff_normal, colorOff_hover, colorOff_click ,p);
        onButton = new Button(posX+width/2,posY,width/2, height,colorOn_normal,colorOn_hover ,colorOn_click , p);
    }

    public Switch(int posX, int posY, int width, int height, int normalColor, int hoverColor, int pressedColor, PApplet p){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.p = p;
    }

    public void update(){
        if(isActive){
            onButton.setColorNormal(colorOn_normal);
            offButton.setColorNormal(colorOff_inactive);
        } else {
            onButton.setColorNormal(colorOn_inactive);
            offButton.setColorNormal(colorOff_normal);
        }
        onButton.update();
        offButton.update();
        logic();
        p.fill(0);
        p.textSize(23);
        p.text("OFF", posX,posY,width/2.f, height);
        p.text("ON", posX+width/2.f,posY,width/2.f, height);
    }

    private void logic(){
        hadChange = false;
        if(onButton.isPressed()){
            if(!isActive){
                hadChange = true;
            }
            isActive = true;
        }
        if (offButton.isPressed()){
            if(isActive){
                hadChange = true;
            }
            isActive = false;
        }
    }

    public boolean hadChange(){
        return hadChange;
    }

}
