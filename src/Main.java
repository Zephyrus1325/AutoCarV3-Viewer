
import processing.core.PApplet;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main extends PApplet {
    ControlPanel panel;
    public void settings(){
        size(1280,720);
    }
    public void setup(){
        frameRate(30);
        panel = new ControlPanel(this);

    }

    public void draw() {
        panel.update();
    }

    public static void main(String[] args){
        PApplet.main("Main");
    }
}