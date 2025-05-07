import processing.core.PApplet;

public class Chunk {
    PApplet p;

    int posX;
    int posY;

    final int chunkSize = 32; // size in units of the chunk

    int[] data = new int[chunkSize*chunkSize];

    public Chunk(int posX, int posY, PApplet p){
        this.posX = posX;
        this.posY = posY;
        this.p = p;
    }

    void render(int offsetX, int offsetY, float size){
        p.strokeWeight(3);
        p.rect(offsetX, offsetY,chunkSize * size,chunkSize * size);

        p.strokeWeight(1);
        for(int j = 0; j < chunkSize*chunkSize; j++){
            if(this.data[j] > 5){
                p.fill(0);
            } else {
                p.fill(255);
            }
            p.rect(offsetX + size * (j % chunkSize), offsetY + size * (j / chunkSize), size, size);
        }
    }

    void setData(int address, int value){
        this.data[address] = value;
    }

    int getChunkSize(){return chunkSize;}
}
