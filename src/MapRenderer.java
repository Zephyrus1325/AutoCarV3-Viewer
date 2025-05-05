import processing.core.PApplet;
import processing.serial.*;

public class MapRenderer {
    PApplet p;
    Serial serial;

    final int size = 6; // size in pixels of each block
    final int chunkSize = 32; // size in units of each chunk
    int posX, posY;

    private int[] buffer = new int[150];
    private int index = 0;

    int[][] chunk = new int[9][chunkSize*chunkSize];

    MapRenderer(int posX, int posY, PApplet p){
        this.posX = posX;
        this.posY = posY;
        this.p = p;
        serial = new Serial(p, "COM8", 115200);
    }

    void update(){
        readSerial();
        render();
    }

    void render(){
        for(int i = 0; i < 9; i++){
            int x0 = ((i % 3) * chunkSize * size) + posX;
            int y0 = ((i / 3) * chunkSize * size) + posY;
            p.strokeWeight(3);
            p.rect(x0,y0,chunkSize * size,chunkSize * size);

            p.strokeWeight(1);
            for(int j = 0; j < chunkSize*chunkSize; j++){
                if(chunk[i][j] > 5){
                    p.fill(0);
                } else {
                    p.fill(255);
                }
                p.rect(x0 + size * (j % chunkSize), y0 + size * (j / chunkSize), size, size);
            }
        }
    }

    void readSerial(){
        final int SYNC0 = 0;
        final int SYNC1 = 1;
        final int SUBDIVISION = 2;
        final int ID = 3;

        final int DATA = 4;
        final int length = 128;

        while(serial.available() > 0){
            int reading = serial.read();
            buffer[index] = reading;
            index++;
            if(buffer[SYNC0] == 0xAA && buffer[SYNC1] == 0x00) {
                if(index > 131) {
                    int chunkStart = buffer[SUBDIVISION] * chunkSize * chunkSize / 8;
                    //System.out.println(buffer[SUBDIVISION]);
                    for (int i = 0; i < 128; i++) {
                        if (chunkStart < 1024) {
                            chunk[buffer[ID]][chunkStart + i] = buffer[DATA + i];
                        }
                        //System.out.printf("%02x ", buffer[i]);
                    }
                    //System.out.println();
                    index = 0;
                }
            } else {
                if(index > SYNC1){
                    index = 0;
                }
            }
            if(index > 135){
                index = 0;
            }
        }

    }
}
