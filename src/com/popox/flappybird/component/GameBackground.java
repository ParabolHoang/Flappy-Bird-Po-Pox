package com.popox.flappybird.component;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.popox.flappybird.util.Constant;
import com.popox.flappybird.util.GameUtil;

/**
 * THe background of the game
 *
 * @author Po Pox
 *
 */
public class GameBackground {

    private static final BufferedImage BackgroundImg;// background image

    private final int speed; // Background layer speed
    private int layerX; // Background layer x coordinate

    public static final int GROUND_HEIGHT;

    static {
        BackgroundImg = GameUtil.loadBufferedImage(Constant.BG_IMG_PATH);
        assert BackgroundImg != null;
        GROUND_HEIGHT = BackgroundImg.getHeight() / 2;
    }

    // constructor
    public GameBackground() {
        this.speed = Constant.GAME_SPEED;
        this.layerX = 0;
    }

    // Draw the background
    public void draw(Graphics g, Bird bird) {
        // Set the background color
        g.setColor(Constant.BG_COLOR);
        g.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

        // Draw the background image
        int imgWidth = BackgroundImg.getWidth();
        int imgHeight = BackgroundImg.getHeight();

        int count = Constant.FRAME_WIDTH / imgWidth + 2; // Number of background images
        for (int i = 0; i < count; i++) {
            g.drawImage(BackgroundImg, imgWidth * i - layerX, Constant.FRAME_HEIGHT - imgHeight, null);
        }

        if (bird.isDead()) {  // If the bird is dead, stop the background layer
            return;
        }
        movement();
    }

    // Login to move the layer
    private void movement() {
        layerX += speed;
        if (layerX > BackgroundImg.getWidth()) {
            layerX = 0;
        }
    }
}
