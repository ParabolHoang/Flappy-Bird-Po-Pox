package com.popox.flappybird.util;

import java.awt.Color;
import java.awt.Font;

/**
 * constant clayer
 *
 * @author Po Pox Can be used to store all the constants in the game
 */
public class Constant {
    // Resolution

    public static final int FRAME_WIDTH = 420;
    public static final int FRAME_HEIGHT = 640;

    // Game title
    public static final String GAME_TITLE = "Flappy Bird - Po Pox";

    // Game window location
    public static final int FRAME_X = 600;
    public static final int FRAME_Y = 100;

    // Game image path
    public static final String BG_IMG_PATH = "resources/img/background.png"; // Background image

    // Bird image path
    public static final String[][] BIRDS_IMG_PATH = {
        {"resources/img/0.png", "resources/img/1.png", "resources/img/2.png", "resources/img/3.png",
            "resources/img/4.png", "resources/img/5.png", "resources/img/6.png", "resources/img/7.png"},
        {"resources/img/up.png", "resources/img/up.png", "resources/img/up.png", "resources/img/up.png",
            "resources/img/up.png", "resources/img/up.png", "resources/img/up.png", "resources/img/up.png"},
        {"resources/img/down_0.png", "resources/img/down_1.png", "resources/img/down_2.png",
            "resources/img/down_3.png", "resources/img/down_4.png", "resources/img/down_5.png",
            "resources/img/down_6.png", "resources/img/down_7.png"},
        {"resources/img/dead.png", "resources/img/dead.png", "resources/img/dead.png", "resources/img/dead.png",
            "resources/img/dead.png", "resources/img/dead.png", "resources/img/dead.png",
            "resources/img/dead.png",}};

    // Cloud image path
    public static final String[] CLOUDS_IMG_PATH = {"resources/img/cloud_0.png", "resources/img/cloud_1.png"};

    // Pipe image path
    public static final String[] PIPE_IMG_PATH = {"resources/img/pipe.png", "resources/img/pipe_top.png",
        "resources/img/pipe_bottom.png"};

    public static final String TITLE_IMG_PATH = "resources/img/title-1.png";
    public static final String NOTICE_IMG_PATH = "resources/img/start-1.png";
    public static final String SCORE_IMG_PATH = "resources/img/score.png";
    public static final String OVER_IMG_PATH = "resources/img/over-1.png";
    public static final String AGAIN_IMG_PATH = "resources/img/again-3.png";

    public static final String SCORE_FILE_PATH = "resources/score"; // Score file path

    // Game speed
    public static final int GAME_SPEED = 4;

    // Background color
    public static final Color BG_COLOR = new Color(0x4bc4cf);

    // Game status
    public static final int FPS = 1000 / 30;

    // Title height
    public static final int TOP_BAR_HEIGHT = 20;

    // Ground height
    public static final int GROUND_HEIGHT = 35;

    // Pipe width and height
    public static final int TOP_PIPE_LENGTHENING = 100;

    public static final int CLOUD_BORN_PERCENT = 6; // Probability of cloud generation
    public static final int CLOUD_IMAGE_COUNT = 2; // Number of cloud images
    public static final int MAX_CLOUD_COUNT = 7; // Maximum number of clouds

    public static final Font CURRENT_SCORE_FONT = new Font("华文琥珀", Font.BOLD, 32);// Current score font
    public static final Font SCORE_FONT = new Font("华文琥珀", Font.BOLD, 24);// Score font

}
