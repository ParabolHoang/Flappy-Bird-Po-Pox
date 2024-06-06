package com.popox.flappybird.component;

import java.awt.Graphics;

import com.popox.flappybird.util.Constant;

/**
 * Moving pipe extends Pipe
 *
 * @author popox
 */
public class MovingPipe extends Pipe {

    private int dealtY; // Pipe's y coordinate offset
    public static final int MAX_DELTA = 50; // Maximum offset
    private int direction;
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;

    // Constructor
    public MovingPipe() {
        super();
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
        super.setAttribute(x, y, height, type, visible);
        dealtY = 0;
        direction = DIR_DOWN;
        if (type == TYPE_TOP_HARD) {
            direction = DIR_UP;
        }
    }

    // Draw the pipe
    public void draw(Graphics g, Bird bird) {
        switch (type) {
            case TYPE_HOVER_HARD:
                drawHoverHard(g);
                break;
            case TYPE_TOP_HARD:
                drawTopHard(g);
                break;
            case TYPE_BOTTOM_HARD:
                drawBottomHard(g);
                break;

        }
        // If the bird is dead, stop the pipe
        if (bird.isDead()) {
            return;
        }
        movement();
    }

    // Draw the hover pipe
    private void drawHoverHard(Graphics g) {
        // Number of splices
        int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        // Draw the top of the pipe
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), y + dealtY, null);
        // Draw the body of the pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + dealtY + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
        }
        // Draw the bottom of the pipe
        int y = this.y + height - PIPE_HEAD_HEIGHT;
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1), y + dealtY, null);
    }

    // Draw the pipe from top to bottom
    private void drawTopHard(Graphics g) {
        // Number of splices
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1; // 取整+1
        // Draw the body of the pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + dealtY + i * PIPE_HEIGHT, null);
        }
        // Draw the top of the pipe
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1),
                height - Constant.TOP_PIPE_LENGTHENING - PIPE_HEAD_HEIGHT + dealtY, null);
    }

    // Draw the pipe from bottom to top
    private void drawBottomHard(Graphics g) {
        //  Number of splices
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        // Draw the body of the pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, Constant.FRAME_HEIGHT - PIPE_HEIGHT - i * PIPE_HEIGHT + dealtY, null);
        }
        // Draw the bottom of the pipe
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), Constant.FRAME_HEIGHT - height + dealtY, null);
    }

    /**
     * Pipe movement
     */
    private void movement() {
        // Pipe movement
        x -= speed;
        pipeRect.x -= speed;
        if (x < -1 * PIPE_HEAD_WIDTH) {// If the pipe is out of the frame, set the pipe to invisible
            visible = false;
        }

        // Pipe y coordinate offset
        if (direction == DIR_DOWN) {
            dealtY++;
            if (dealtY > MAX_DELTA) {
                direction = DIR_UP;
            }
        } else {
            dealtY--;
            if (dealtY <= 0) {
                direction = DIR_DOWN;
            }
        }
        pipeRect.y = this.y + dealtY;
    }

}
