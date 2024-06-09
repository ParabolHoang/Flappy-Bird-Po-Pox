package com.popox.flappybird.app;

import com.popox.flappybird.component.*;
import static com.popox.flappybird.util.Constant.*;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Main class of the game
 *
 * @author Po Pox
 */
public class Game extends Frame {

    private static final long serialVersionUID = 1L;

    private static int gameState; // Game status
    public static final int GAME_READY = 0; // Game not started
    public static final int GAME_START = 1; // Game started
    public static final int STATE_OVER = 2; // Game over

    private GameBackground background; // Game background object
    private GameForeground foreground; // Game foreground object
    private Bird bird; // Bird object
    private GameElementLayer gameElement; // Game element object
    private WelcomeAnimation welcomeAnimation; // Game element when the game not started

    // Constructor
    public Game() {
        initFrame(); // Initialize the game window
        setVisible(true); // Set the window visible
        initGame(); // Initialize the game elements
    }

    // Initialize the game window
    public void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT); // Set the window size
        setTitle(GAME_TITLE); // Set the window title
        setLocation(FRAME_X, FRAME_Y); // Set the window location
        setResizable(false); // Set the window not resizable

        // Add a window listener to the window
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // Exit the program when the window is closing
            }
        });
        addKeyListener(new BirdKeyListener()); // Add a key listener to the window
    }

    // Key listener inside the game
    class BirdKeyListener implements KeyListener {

        // When a key pressed, call the method base on key
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            switch (gameState) {
                case GAME_READY:
                    if (keycode == KeyEvent.VK_SPACE) {
                        // When the game not started, press space to start the game, the bird will flap once, and effected by gravity
                        bird.birdFlap();
                        bird.birdFall();
                        setGameState(GAME_START); // Set the game status to GAME_START
                    }
                    break;
                case GAME_START:
                    if (keycode == KeyEvent.VK_SPACE) {
                        // When the game started, press space to make the bird flap
                        bird.birdFlap();
                        bird.birdFall();
                    }
                    break;
                case STATE_OVER:
                    if (keycode == KeyEvent.VK_SPACE) {
                        //  When the game over, press space to restart the game
                        resetGame();
                    }
                    break;
            }
        }

        // Reset the game
        public void resetGame() {
            setGameState(GAME_READY);
            gameElement.reset();
            bird.reset();
        }

        // When a key released, call the method base on key
        public void keyReleased(KeyEvent e) {
            int keycode = e.getKeyChar();
            if (keycode == KeyEvent.VK_SPACE) {
                bird.keyReleased();
            }
        }

        public void keyTyped(KeyEvent e) {
        }
    }

    // Initialize the game elements
    public void initGame() {
        background = new GameBackground();
        gameElement = new GameElementLayer();
        foreground = new GameForeground();
        welcomeAnimation = new WelcomeAnimation();
        bird = new Bird();
        setGameState(GAME_READY);

        // Start a new thread to repaint the window
        new Thread(() -> {
            while (true) {
                repaint(); // call repaint() for JWM called the update() method
                try {
                    Thread.sleep(FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // The project code contains two threads: a system thread and a custom thread that called repaint()
    // Thread system: create the contain on the screen, and listen to the keyboard 
    private final BufferedImage bufImg = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

    public void update(Graphics g) {
        Graphics bufG = bufImg.getGraphics(); // Take the image drawing pen
        // Draw the game elements on the image
        background.draw(bufG, bird); // The filter layer
        foreground.draw(bufG, bird); // The foreground layer
        if (gameState == GAME_READY) { // Game not started
            welcomeAnimation.draw(bufG);
        } else { // When the game is over
            gameElement.draw(bufG, bird); // The game element layer
        }
        bird.draw(bufG);
        g.drawImage(bufImg, 0, 0, null); // Draw the image on the window screen
    }

    public static void setGameState(int gameState) {
        Game.gameState = gameState;
    }

}
