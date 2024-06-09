package com.popox.flappybird.component;

import com.popox.flappybird.util.Constant;
import com.popox.flappybird.util.GameUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Game over animation
 *
 * @author popox
 *
 */
public class GameOverAnimation {

    private final BufferedImage scoreImg; // Score board
    private final BufferedImage overImg; // Game over image
    private final BufferedImage againImg; // Play again image

    public GameOverAnimation() {
        overImg = GameUtil.loadBufferedImage(Constant.OVER_IMG_PATH);
        scoreImg = GameUtil.loadBufferedImage(Constant.SCORE_IMG_PATH);
        againImg = GameUtil.loadBufferedImage(Constant.AGAIN_IMG_PATH);
    }

    private static final int SCORE_LOCATE = 5; // Score position
    private int flash = 0; // Flash score

    public void draw(Graphics g, Bird bird) {
        int x = Constant.FRAME_WIDTH - overImg.getWidth() >> 1;
        int y = Constant.FRAME_HEIGHT / 4;
        g.drawImage(overImg, x, y, null);

        // Draw the score board
        x = Constant.FRAME_WIDTH - scoreImg.getWidth() >> 1;
        y = Constant.FRAME_HEIGHT / 3;
        g.drawImage(scoreImg, x, y, null);

        // Draw the current score
        g.setColor(Color.white);
        g.setFont(Constant.SCORE_FONT);
        x = (Constant.FRAME_WIDTH - scoreImg.getWidth() / 2 >> 1) + SCORE_LOCATE;// 位置补偿
        y += scoreImg.getHeight() >> 1;
        String str = Long.toString(bird.getCurrentScore());
        x -= GameUtil.getStringWidth(Constant.SCORE_FONT, str) >> 1;
        y += GameUtil.getStringHeight(Constant.SCORE_FONT, str);
        g.drawString(str, x, y);

        // Draw the best score
        if (bird.getBestScore() > 0) {
            str = Long.toString(bird.getBestScore());
            x = (Constant.FRAME_WIDTH + scoreImg.getWidth() / 2 >> 1) - SCORE_LOCATE;// 位置补偿
            x -= GameUtil.getStringWidth(Constant.SCORE_FONT, str) >> 1;
            g.drawString(str, x, y);
        }

        // Draw the play again image
        final int COUNT = 30; // Flash cycle
        if (flash++ > COUNT) {
            GameUtil.drawImage(againImg, Constant.FRAME_WIDTH - againImg.getWidth() >> 1, Constant.FRAME_HEIGHT / 5 * 3, g);
        }
        if (flash == COUNT * 2) // Reset the flash
        {
            flash = 0;
        }
    }
}
