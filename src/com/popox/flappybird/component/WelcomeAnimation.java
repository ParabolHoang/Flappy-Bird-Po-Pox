package com.popox.flappybird.component;

import com.popox.flappybird.util.Constant;
import com.popox.flappybird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Welcome animation
 *
 * @author Po Pox
 *
 */
public class WelcomeAnimation {

    private final BufferedImage titleImg;
    private final BufferedImage noticeImg;

    private int flashCount = 0; // Flash count

    public WelcomeAnimation() {
        titleImg = GameUtil.loadBufferedImage(Constant.TITLE_IMG_PATH);
        noticeImg = GameUtil.loadBufferedImage(Constant.NOTICE_IMG_PATH);
    }

    public void draw(Graphics g) {
        int x = (Constant.FRAME_WIDTH - titleImg.getWidth()) >> 1;
        int y = Constant.FRAME_HEIGHT / 3;
        g.drawImage(titleImg, x, y, null);

        // Flash the notice
        final int CYCLE = 30; // Flash cycle
        if (flashCount++ > CYCLE) {
            GameUtil.drawImage(noticeImg, Constant.FRAME_WIDTH - noticeImg.getWidth() >> 1, Constant.FRAME_HEIGHT / 5 * 3, g);
        }
        if (flashCount == CYCLE * 2) {
            flashCount = 0;
        }
    }

}
