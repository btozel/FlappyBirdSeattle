import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth;
    int boardHeight;

    // Declaration of images
    Image backGroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Bird object
    Bird bird;

    // X, Y coordinates of the bird
    int birdX, birdY;
    // Width and height of the bird
    int birdWidth, birdHeight;


//    // Pipes
//    int pipeX;
//    int pipeY;
//    int pipeWidth;
//    int pipeHeight;

    // Game Logic
    boolean gameOver = false;
    Timer gameLoop;

    Timer placePipesTimer;

    // We only need velocity for Y direction because the bird is only moving in Y direction, X location is not changing
    // The bird is moving upwards at a rate of -6 pixels per frame
    int velocityY = 0;

    // Because of the gravity every frame the bird is going to slow down by one pixel
    int gravity = 1;

    // Move pipes to the left at a rate of -4 pixels per frame
    int velocityX = -4;

    ArrayList<Pipe> pipes;

    Random random = new Random();

    FlappyBird(int width, int height){
        this.boardWidth = width;
        this.boardHeight = height;

        this.birdX = boardWidth / 8;
        this.birdY = boardHeight / 2;

        birdWidth = 35;
        birdHeight = 41;

//        // pipe X will be the right side of the window (boardWith)
//        pipeX = boardWidth;
//        // pipe Y will be the top of the window (0)
//        pipeY = 0;
//        // pipe width and height scale 1/6
//        pipeWidth = 64;
//        pipeHeight = 512;

        pipes = new ArrayList<Pipe>();

        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });

        placePipesTimer.start();

        setPreferredSize(new Dimension(boardWidth, boardHeight));

        setFocusable(true);
        addKeyListener(this);

        setBackground(Color.blue);

        // Initialize images
        backGroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdX, birdY, birdWidth, birdHeight, birdImg);

        // game timer (60 FPS --> 1 Second (1000 ms) / 60)
        gameLoop = new Timer(2000/60, this);
        gameLoop.start();

    }

    private void placePipes(){
        Pipe topPipe = new Pipe(boardWidth, topPipeImg);

        int randomPipeY = (int)(topPipe.y - topPipe.height / 4 - Math.random()*(topPipe.height/2));
        topPipe.y = randomPipeY;

        pipes.add(topPipe);

        int oppeningSpace = boardHeight / 4;

        Pipe bottomPipe = new Pipe(boardWidth,bottomPipeImg);
        bottomPipe.y = topPipe.y + + bottomPipe.height + oppeningSpace;

        pipes.add(bottomPipe);
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g){
        // background
        g.drawImage(backGroundImg, 0, 0, boardWidth, boardHeight, null);

        // bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, birdHeight, null);

        // pipes
        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
    }

    private void move(){
        // BIRD
        velocityY += gravity;
        // Update bird's Y position
        bird.y = bird.y + velocityY;
        // Bird shouldn't move outside the window, top of the window is 0 because left top corner is (0,0)
        // So bird's Y location shouldn't be less than 0
        bird.y = Math.max(bird.y, 0);

        if(bird.y > boardHeight){
            gameOver = true;
        }

        // PIPES
        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x = pipe.x += velocityX;

            if(collision(bird, pipe)){
                gameOver = true;
            }
        }
    }

    public boolean collision(Bird bird, Pipe pipe){
        return  bird.x < pipe.x + bird.width &&
                bird.x + bird.width > pipe.x &&
                bird.y + 17 < pipe.y + pipe.height &&
                bird.y + birdHeight > pipe.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -10;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}



    @Override
    public void keyReleased(KeyEvent e) {}
}
