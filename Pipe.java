import java.awt.*;
public class Pipe {
    int x;
    int y;
    int width;
    int height;
    Image img;

    boolean passed;

    Pipe(int boardWidth, Image img){
        this.x = boardWidth;
        this.y = 0;
        // pipe width and height scale 1/6 of image
        this.width = 64;
        this.height = 512;

        this.img = img;
        passed = false;
    }

}
