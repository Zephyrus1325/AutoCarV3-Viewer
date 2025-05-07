import processing.core.PApplet;
import processing.core.PConstants;
import processing.serial.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MapRenderer {
    PApplet p;
    Serial serial;

    int width = 3; // Width in number of chunks (must be odd)
    float size = 6; // size in pixels of each block
    int chunkSize = 32; // size in units of each chunk
    int posX, posY;     // Rendering position X, Y
    int offsetX = 0;    // Map offset in X
    int offsetY = 0;    // Map offset in Y

    ArrayList<Chunk> chunk = new ArrayList<>();

    private int[] buffer = new int[150];
    private int index = 0;

    private Button zoomIn;
    private Button zoomOut;
    private Button leftButton;
    private Button rightButton;
    private Button upButton;
    private Button downButton;

    MapRenderer(int posX, int posY, PApplet p){
        this.posX = posX;
        this.posY = posY;
        this.p = p;
        serial = new Serial(p, "COM1", 115200);
        zoomIn = new Button(860, 100, 50,50, p);
        zoomOut = new Button(860, 650, 50,50, p);
        leftButton = new Button(10, 375, 50,50, p);
        rightButton = new Button(860, 375, 50,50, p);
        upButton = new Button(450, 100, 50,50, p);
        downButton = new Button(450, 650, 50,50, p);
    }

    void update(){
        readSerial();
        render();
        updateButtons();

    }

    void render(){
        int x0 = offsetX - width/2;
        int y0 = offsetY - width/2;
        for(int y = y0; y <= offsetY + width/2; y++){
            for(int x = x0; x <= offsetX + width/2; x++){
                int index = findChunk(x, y);

                if(index != -1) {
                    int px = posX + (int)(x * size * chunkSize) - (int)(size * chunkSize /2) - (int)(offsetX * size * chunkSize);
                    int py = posY + (int)(y * size * chunkSize) - (int)(size * chunkSize /2) - (int)(offsetY * size * chunkSize);

                    chunk.get(index).render(px, py, size);

                }
            }
        }
    }

    void readSerial(){
        final int SYNC0 = 0;
        final int SYNC1 = 1;
        final int SUBDIVISION = 2;
        final int CHUNKX = 3;
        final int CHUNKY = 5;

        final int DATA = 6;
        final int length = 128;

        while(serial.available() > 0){
            int reading = serial.read();
            buffer[index] = reading;
            index++;
            if(buffer[SYNC0] == 0xAA && buffer[SYNC1] == 0x00) {
                if(index > 131) {
                    int chunkStart = buffer[SUBDIVISION] * chunkSize * chunkSize / 8;
                    int chunkX = buffer[CHUNKX] + buffer[CHUNKX] << 8;
                    int chunkY = buffer[CHUNKY] + buffer[CHUNKY] << 8;
                    int chunkIndex = findChunk(buffer[CHUNKX], buffer[CHUNKY]);
                    if(index != -1) {
                        for (int i = 0; i < 128; i++) {
                            if (chunkStart < 1024) {
                                chunk.get(chunkIndex).setData(chunkStart + i, buffer[DATA + i]);
                            }
                        }
                    } else {
                        chunk.add(new Chunk(chunkX, chunkY, p));
                        chunkIndex = chunk.size()-1;
                        for (int i = 0; i < 128; i++) {
                            if (chunkStart < 1024) {
                                chunk.get(chunkIndex).setData(chunkStart + i, buffer[DATA + i]);
                            }
                        }
                    }
                    index = 0;
                }
            } else {
                if(index > SYNC1){
                    index = 0;
                }
            }
        }

    }

    void updateButtons(){
        zoomIn.update();
        zoomOut.update();
        leftButton.update();
        rightButton.update();
        upButton.update();
        downButton.update();
        if(zoomIn.isPressed()){removeWidth();}
        if(zoomOut.isPressed()){addWidth();}
        if(leftButton.isPressed()){subX();}
        if(rightButton.isPressed()){addX();}
        if(upButton.isPressed()){subY();}
        if(downButton.isPressed()){addY();}

    }
    int findChunk(int x, int y){
        for(int i = 0; i < chunk.size(); i++){
            if(chunk.get(i).posX == x && chunk.get(i).posY == y){
                return i;
            }
        }
        return -1;
    }


    void addWidth(){width += 2; size /= 2.0f;}

    void removeWidth(){width -= 2; size *= 2.0f;}

    void addX(){offsetX++;}
    void addY(){offsetY++;}
    void subX(){offsetX--;}
    void subY(){offsetY--;}

}
