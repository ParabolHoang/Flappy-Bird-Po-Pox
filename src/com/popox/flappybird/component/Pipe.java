package com.popox.flappybird.component;

import com.popox.flappybird.util.Constant;
import com.popox.flappybird.util.GameUtil;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Pipe, draw and move the pipe
 *
 * @author popox
 */
public class Pipe {

    static BufferedImage[] imgs; // Pipe image, static for loading image once

    static {// Load the image
        final int PIPE_IMAGE_COUNT = 3;
        imgs = new BufferedImage[PIPE_IMAGE_COUNT];
        for (int i = 0; i < PIPE_IMAGE_COUNT; i++) {
            imgs[i] = GameUtil.loadBufferedImage(Constant.PIPE_IMG_PATH[i]);
        }
    }

    // Pipe width and height
    public static final int PIPE_WIDTH = imgs[0].getWidth();
    public static final int PIPE_HEIGHT = imgs[0].getHeight();
    public static final int PIPE_HEAD_WIDTH = imgs[1].getWidth();
    public static final int PIPE_HEAD_HEIGHT = imgs[1].getHeight();

    public int x, y; // Pipe coordinate
    public int width, height; // Pipe width and height

    public boolean visible; // Pipe visibility
    // Pipe type
    public int type;
    public static final int TYPE_TOP_NORMAL = 0;
    public static final int TYPE_TOP_HARD = 1;
    public static final int TYPE_BOTTOM_NORMAL = 2;
    public static final int TYPE_BOTTOM_HARD = 3;
    public static final int TYPE_HOVER_NORMAL = 4;
    public static final int TYPE_HOVER_HARD = 5;

    // Pipe speed
    public int speed;

    Rectangle pipeRect; // Pipe collision rectangle

    // Constructor
    public Pipe() {
        this.speed = Constant.GAME_SPEED;
        this.width = PIPE_WIDTH;

        pipeRect = new Rectangle();
        pipeRect.width = PIPE_WIDTH;
    }

    /**
     * Set the attributes of the pipe
     *
     * @param x:xcordinate
     * @param y：ycordinate
     * @param height:pipe-height
     * @param type:pipe-type
     * @param visible：pipe-visible
     */
    public void setAttribute(int x, int y, int height, int type, boolean visible) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.type = type;
        this.visible = visible;
        setRectangle(this.x, this.y, this.height);
    }

    /**
     * Set the pipe's collision rectangle
     */
    public void setRectangle(int x, int y, int height) {
        pipeRect.x = x;
        pipeRect.y = y;
        pipeRect.height = height;
    }

    // Get the pipe's visibility
    public boolean isVisible() {
        return visible;
    }

    // Draw the pipe
    public void draw(Graphics g, Bird bird) {
        switch (type) {
            case TYPE_TOP_NORMAL:
                drawTopNormal(g);
                break;
            case TYPE_BOTTOM_NORMAL:
                drawBottomNormal(g);
                break;
            case TYPE_HOVER_NORMAL:
                drawHoverNormal(g);
                break;
        }

        // If the bird is dead, stop the pipe
        if (bird.isDead()) {
            return;
        }
        movement();
    }

    // Draw the pipe from top to bottom
    public void drawTopNormal(Graphics g) {
        // Number of splices
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1; // Number of splices
        // Draw the body of the pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + i * PIPE_HEIGHT, null);
        }
        // Draw the top of the pipe
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1),
                height - Constant.TOP_PIPE_LENGTHENING - PIPE_HEAD_HEIGHT, null); // 
    }

    // Draw the pipe from bottom to top
    public void drawBottomNormal(Graphics g) {
        // Number of splices
        int count = (height - PIPE_HEAD_HEIGHT - Constant.GROUND_HEIGHT) / PIPE_HEIGHT + 1;
        // Draw the body of the pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, Constant.FRAME_HEIGHT - PIPE_HEIGHT - Constant.GROUND_HEIGHT - i * PIPE_HEIGHT,
                    null);
        }
        // Draw the bottom of the pipe
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), Constant.FRAME_HEIGHT - height, null);
    }

    // Draw the hover pipe
    public void drawHoverNormal(Graphics g) {
        // Number of splices
        int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        // Draw the top of the pipe
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), y, null);
        // Draw the body of the pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
        }
        // Draw the bottom of the pipe
        int y = this.y + height - PIPE_HEAD_HEIGHT;
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1), y, null);
    }

    /**
     * Pipe movement
     */
    public void movement() {
        x -= speed;
        pipeRect.x -= speed;
        if (x < -1 * PIPE_HEAD_WIDTH) {// If the pipe is completely out of the window, set the pipe to invisible
            visible = false;
        }
    }

    /**
     * Determine whether the pipe is completely in the window
     *
     * @return true if the pipe is completely in the window, otherwise false
     */
    public boolean isInFrame() {
        return x + width < Constant.FRAME_WIDTH;
    }

    // Get the pipe's x coordinate
    public int getX() {
        return x;
    }

    // Get the pipe's collision rectangle
    public Rectangle getPipeRect() {
        return pipeRect;
    }

}
