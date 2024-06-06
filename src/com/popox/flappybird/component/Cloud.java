package com.popox.flappybird.component;

import com.popox.flappybird.util.Constant;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Cloud {

    private final int speed;
    private int x;
    private final int y;

    private final BufferedImage img;

    private final int scaleImageWidth;
    private final int scaleImageHeight;

    // Constructor
    public Cloud(BufferedImage img, int x, int y) {
        super();
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = Constant.GAME_SPEED * 2; // Speed of cloud
        // Randomly scale the cloud image
        double scale = 1 + Math.random(); // Random scale
        // Scale the cloud image
        scaleImageWidth = (int) (scale * img.getWidth());
        scaleImageHeight = (int) (scale * img.getWidth());
    }

    // Draw the cloud
    public void draw(Graphics g, Bird bird) {
        int speed = this.speed;
        if (bird.isDead()) {
            speed = 1;
        }
        x -= speed;
        g.drawImage(img, x, y, scaleImageWidth, scaleImageHeight, null);
    }

    /**
     * Determine whether the cloud is out of the frame
     *
     * @return true if the cloud is out of the frame, otherwise false
     */
    public boolean isOutFrame() {
        return x < -1 * scaleImageWidth;
    }

}
