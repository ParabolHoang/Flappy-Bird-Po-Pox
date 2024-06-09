package com.popox.flappybird.util;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Extension Layer
 *
 * @author Po Pox
 */
public class GameUtil {

    private GameUtil() {
    } // Prevent instantiation

    /**
     * Load the image resource
     *
     * @param imgPath image path
     * @return image resource
     */
    public static BufferedImage loadBufferedImage(String imgPath) {
        try {
            return ImageIO.read(new FileInputStream(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Determine whether the event occurs according to the probability
     *
     * @param numerator numerator, not less than 0
     * @param denominator denominator, not less than 0
     * @return true: event occurs, false: event does not occur
     */
    public static boolean isInProbability(int numerator, int denominator) throws Exception {
        // Illegal parameter
        if (numerator <= 0 || denominator <= 0) {
            throw new Exception("An ivalid parameter was passed");
        }
        // The probability is 100%
        if (numerator >= denominator) {
            return true;
        }

        return getRandomNumber(1, denominator + 1) <= numerator;
    }

    /**
     * Get a random number in the specified range
     *
     * @param min Minimum value of the interval, inclusive
     * @param max Maximum value of the interval, exclusive
     * @return Random number
     */
    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * Get the width of the string
     */
    public static int getStringWidth(Font font, String str) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        return (int) (font.getStringBounds(str, frc).getWidth());
    }

    public static int getStringHeight(Font font, String str) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        return (int) (font.getStringBounds(str, frc).getHeight());
    }

    /**
     *
     * @param image:image-resource
     * @param x：x-coordinate
     * @param y：y-coordinate
     * @param g：Graphics
     */
    public static void drawImage(BufferedImage image, int x, int y, Graphics g) {
        g.drawImage(image, x, y, null);
    }

}
