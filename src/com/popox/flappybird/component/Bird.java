package com.popox.flappybird.component;

import com.popox.flappybird.app.Game;
import com.popox.flappybird.util.Constant;
import com.popox.flappybird.util.GameUtil;
import com.popox.flappybird.util.MusicUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Bird, draw and move the bird
 *
 * @author Kingyu
 */
public class Bird {

    public static final int IMG_COUNT = 8; // Image count
    public static final int STATE_COUNT = 4; // State count
    private final BufferedImage[][] birdImages; // Bird image
    private final int x;
    private int y; // Bird coordinate
    private int wingState; // Wing state

    // Bird image
    private BufferedImage image; // Bird image

    // Bird state
    private int state;
    public static final int BIRD_NORMAL = 0;
    public static final int BIRD_UP = 1;
    public static final int BIRD_FALL = 2;
    public static final int BIRD_DEAD_FALL = 3;
    public static final int BIRD_DEAD = 4;

    private final Rectangle birdCollisionRect; // Bird collision rectangle
    public static final int RECT_DESCALE = 2; // Rectangle descale

    private final ScoreCounter counter; // Score counter
    private final GameOverAnimation gameOverAnimation;

    public static int BIRD_WIDTH;
    public static int BIRD_HEIGHT;

    // Constructor
    public Bird() {
        counter = ScoreCounter.getInstance(); // Get the score counter
        gameOverAnimation = new GameOverAnimation();

        // Load the bird image
        birdImages = new BufferedImage[STATE_COUNT][IMG_COUNT];
        for (int j = 0; j < STATE_COUNT; j++) {
            for (int i = 0; i < IMG_COUNT; i++) {
                birdImages[j][i] = GameUtil.loadBufferedImage(Constant.BIRDS_IMG_PATH[j][i]);
            }
        }

        assert birdImages[0][0] != null;
        BIRD_WIDTH = birdImages[0][0].getWidth();
        BIRD_HEIGHT = birdImages[0][0].getHeight();

        // Initialize the bird
        x = Constant.FRAME_WIDTH >> 2;
        y = Constant.FRAME_HEIGHT >> 1;

        // Initialize the bird state
        int rectX = x - BIRD_WIDTH / 2;
        int rectY = y - BIRD_HEIGHT / 2;
        birdCollisionRect = new Rectangle(rectX + RECT_DESCALE, rectY + RECT_DESCALE * 2, BIRD_WIDTH - RECT_DESCALE * 3,
                BIRD_WIDTH - RECT_DESCALE * 4); // Initialize the bird collision rectangle
    }

    // login to move the bird
    private void movement() {
        // Wind state, action of the bird
        wingState++;
        image = birdImages[Math.min(state, BIRD_DEAD_FALL)][wingState / 10 % IMG_COUNT];
        if (state == BIRD_FALL || state == BIRD_DEAD_FALL) {
            freeFall();
            if (birdCollisionRect.y > BOTTOM_BOUNDARY) {
                if (state == BIRD_FALL) {
                    MusicUtil.playCrash();
                }
                die();
            }
        }
    }

    // Draw the bird
    public void draw(Graphics g) {
        movement();
        int state_index = Math.min(state, BIRD_DEAD_FALL); // Get the bird state
        // Calculate the bird 
        int halfImgWidth = birdImages[state_index][0].getWidth() >> 1;
        int halfImgHeight = birdImages[state_index][0].getHeight() >> 1;
        if (velocity > 0) {
            image = birdImages[BIRD_UP][0];
        }
        g.drawImage(image, x - halfImgWidth, y - halfImgHeight, null); // x cord in 1/4 of the frame width, y cord in 1/2 of the frame height

        if (state == BIRD_DEAD) {
            gameOverAnimation.draw(g, this);
        } else if (state != BIRD_DEAD_FALL) {
            drawScore(g);
        }

    }

    public static final int ACC_FLAP = 14; // players speed on flapping
    public static final double ACC_Y = 2; // players downward acceleration
    public static final int MAX_VEL_Y = 15; // max vel along Y, max descend speed
    private int velocity = 0; // bird's velocity along Y, default same as playerFlapped
    private final int BOTTOM_BOUNDARY = Constant.FRAME_HEIGHT - GameBackground.GROUND_HEIGHT - (BIRD_HEIGHT / 2);

    private void freeFall() {
        if (velocity < MAX_VEL_Y) {
            velocity -= ACC_Y;
        }
        y = Math.min((y - velocity), BOTTOM_BOUNDARY);
        birdCollisionRect.y = birdCollisionRect.y - velocity;
    }

    private void die() {
        counter.saveScore();
        state = BIRD_DEAD;
        Game.setGameState(Game.STATE_OVER);
    }

    // Check if the bird is dead
    public boolean isDead() {
        return state == BIRD_DEAD_FALL || state == BIRD_DEAD;
    }
    // Bird flap

    public void birdFlap() {
        if (keyIsReleased()) {
            if (isDead()) {
                return;
            }
            MusicUtil.playFly(); // Play the sound effect
            state = BIRD_UP;
            if (birdCollisionRect.y > Constant.TOP_BAR_HEIGHT) {
                velocity = ACC_FLAP; // Flap the bird
                wingState = 0; // Reset the wing state
            }
            keyPressed();
        }
    }

    // Bird fall
    public void birdFall() {
        if (isDead()) {
            return;
        }
        state = BIRD_FALL;
    }

    // Bird dead fall
    public void deadBirdFall() {
        state = BIRD_DEAD_FALL;
        MusicUtil.playCrash(); // Play the sound effect 
        velocity = 0;  // Reset the velocity
    }

    //  Draw the score
    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(Constant.CURRENT_SCORE_FONT);
        String str = Long.toString(counter.getCurrentScore());
        int x = Constant.FRAME_WIDTH - GameUtil.getStringWidth(Constant.CURRENT_SCORE_FONT, str) >> 1;
        g.drawString(str, x, Constant.FRAME_HEIGHT / 10);
    }

    // Reset the bird
    public void reset() {
        state = BIRD_NORMAL; // Bird state
        y = Constant.FRAME_HEIGHT >> 1; // Bird coordinate
        velocity = 0; // Bird velocity

        int ImgHeight = birdImages[state][0].getHeight();
        birdCollisionRect.y = y - ImgHeight / 2 + RECT_DESCALE * 2; // Reset the bird collision rectangle

        counter.reset(); // Reset the score counter
    }

    private boolean keyFlag = true; // Key flag

    public void keyPressed() {
        keyFlag = false;
    }

    public void keyReleased() {
        keyFlag = true;
    }

    public boolean keyIsReleased() {
        return keyFlag;
    }

    public long getCurrentScore() {
        return counter.getCurrentScore();
    }

    public long getBestScore() {
        return counter.getBestScore();
    }

    public int getBirdX() {
        return x;
    }

    public Rectangle getBirdCollisionRect() {
        return birdCollisionRect;
    }
}
