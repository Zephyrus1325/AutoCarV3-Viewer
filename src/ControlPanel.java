import processing.core.PApplet;
import processing.core.PConstants;

public class ControlPanel {
    private PApplet p;
    // Lotsa parameters
    private final int map_w = 900;
    private final int map_h = 600;
    private final int border = 10;
    private final int round = 20;

    // Swiches
    private Switch navMode;
    private Switch motorLMode;
    private Switch motorRMode;

    private Console console;
    private MapRenderer map;

    public ControlPanel(PApplet p){
        this.p = p;
        navMode = new Switch(1150,470, 100,30,p);
        motorLMode = new Switch(1150,505,100,30,p);
        motorRMode = new Switch(1150,540,100,30,p);
        console = new Console(map_w + border * 3, border*2 + 30, 350 - border * 2, 400 - 60, p);
        map = new MapRenderer(180,110, p);
    }


    public void update(){
        p.cursor(PConstants.ARROW);
        background();
        interactables();
        if(navMode.hadChange() | motorLMode.hadChange() | motorRMode.hadChange()){
            System.out.println("we had a change in parameters");
        }
        console.update();
        map.update();
    }

    private void background(){
        p.background(12,12,12);

        // Basic Shapes
        p.fill(52,52,52);
        p.stroke(100);
        p.strokeWeight(2);
        p.rect(border,border,map_w, 80, round); // Top panel
        p.rect(border,80 + border*2, map_w, map_h, round); // Map area
        p.rect(map_w + border * 2, border, 350, 80 + border + map_h, round); // Side Panel
        p.fill(0);
        p.rect(map_w + border * 3, border*2, 350 - border * 2, 400, round); // Side Panel Console
        p.fill(12);
        p.rect(map_w + border * 3, border*2, 350 - border * 2, 30, round); // Side Panel Console Name Title
        p.fill(42);
        p.rect(map_w + border * 3, border*3 + 400 ,350 - border * 2, 260, round); // Side panel Buttons
        p.fill(60);
        p.rect(map_w + border * 3, border*3 + 400, 350 - border * 2, 30, round); // Side Panel Buttons Name Title

        // Texts
        p.textSize(27);
        p.fill(200);
        p.textAlign(PConstants.CENTER, PConstants.CENTER);
        p.text("Console", map_w + border * 3, border*2, 350 - border * 2, 30);
        p.fill(0);
        p.text("Controls", map_w + border * 3, border*3 + 400, 350 - border * 2, 30);
    }

    private void interactables(){
        navMode.update();
        motorLMode.update();
        motorRMode.update();
    }
}
