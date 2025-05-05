import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class Console {
    private PApplet p;
    private int posX, posY, width, height;
    private final int textHeight = 23;
    ArrayList<String> data = new ArrayList<>();

    public Console(int posX, int posY, int width, int height, PApplet p){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.p = p;
        data.add("ERROR: No SD card attached");
        data.add("ERROR: Lidar task was interrupted for some reason");
        data.add("ERROR: Failed to open file for writing: chunks/-1_2.chunk");
    }

    public void update(){
        p.textSize(20);
        p.fill(255);
        p.textAlign(PConstants.LEFT, PConstants.TOP);
        //String text = "Error: There was a error with something, lmao, i guess";
        // If text too big, splice it up
        float spacing = 0;
        for(int i = 0; i < data.size(); i++){
            String text = data.get(i);
            p.text(text, posX+10, posY + 10 + spacing, width - 20, height - 20);

            int lastChar = 0;
            do {
                int charCount = lastChar;
                while(p.textWidth(text.substring(lastChar, charCount)) < width && charCount < text.length()){
                    charCount++;
                }
                lastChar = charCount;

                if(charCount >= text.length()){
                    spacing += textHeight;
                    break;
                }
                spacing += textHeight;

            } while(p.textWidth(text) > width);
            if(spacing > height){
                data.remove(0);
                spacing -= textHeight;
            }
        }



    }
}
