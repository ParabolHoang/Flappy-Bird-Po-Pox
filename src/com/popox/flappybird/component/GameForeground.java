package com.popox.flappybird.component;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.popox.flappybird.util.Constant;
import com.popox.flappybird.util.GameUtil;

/**
 * a class to control the foreground of the game
 *
 * @author popox
 */
public class GameForeground {

    private final List<Cloud> clouds; // List of clouds
    private final BufferedImage[] cloudImages; // Element image
    private long time; // logic operation cycle
    public static final int CLOUD_INTERVAL = 100; //cycle new cloud

    // Constructor
    public GameForeground() {
        clouds = new ArrayList<>(); //List of clouds
        // Read the image of the cloud
        cloudImages = new BufferedImage[Constant.CLOUD_IMAGE_COUNT];
        for (int i = 0; i < Constant.CLOUD_IMAGE_COUNT; i++) {
            cloudImages[i] = GameUtil.loadBufferedImage(Constant.CLOUDS_IMG_PATH[i]);
        }
        time = System.currentTimeMillis(); // Use the current time as the initial value of time
    }

    //  Draw the foreground
    public void draw(Graphics g, Bird bird) {
        cloudBornLogic();
        for (Cloud cloud : clouds) {
            cloud.draw(g, bird);
        }
    }

    // Logic of cloud generation
    private void cloudBornLogic() {
        // 100ms error
        if (System.currentTimeMillis() - time > CLOUD_INTERVAL) {
            time = System.currentTimeMillis(); // reset time
            // If the number of clouds is less than the maximum number of clouds, add a cloud
            if (clouds.size() < Constant.MAX_CLOUD_COUNT) {
                try {
                    if (GameUtil.isInProbability(Constant.CLOUD_BORN_PERCENT, 100)) { // Add a cloud with a certain probability
                        int index = GameUtil.getRandomNumber(0, Constant.CLOUD_IMAGE_COUNT); // Use the index to select the cloud image

                        // Cloud x coordinate
                        int x = Constant.FRAME_WIDTH; // The x coordinate of the cloud is the right edge of the window
                        // Cloud y coordinate
                        int y = GameUtil.getRandomNumber(Constant.TOP_BAR_HEIGHT, Constant.FRAME_HEIGHT / 3);

                        // Create a cloud object
                        Cloud cloud = new Cloud(cloudImages[index], x, y);
                        clouds.add(cloud);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } // Add a cloud with a certain probability

            // IF the number of clouds is greater than the maximum number of clouds, remove the cloud
            for (int i = 0; i < clouds.size(); i++) {
                // Remove the cloud that is out of the window
                Cloud tempCloud = clouds.get(i);
                if (tempCloud.isOutFrame()) {
                    clouds.remove(i);
                    i--;
                }
            }
        }
    }
}
