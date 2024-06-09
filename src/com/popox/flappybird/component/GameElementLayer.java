package com.popox.flappybird.component;

import com.popox.flappybird.util.Constant;
import com.popox.flappybird.util.GameUtil;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Game element layer, control the game elements
 *
 * @author Po Pox
 */
public class GameElementLayer {

    private final List<Pipe> pipes; // List of pipes

    // Constructor
    public GameElementLayer() {
        pipes = new ArrayList<>();
    }

    //  Draw the game elements
    public void draw(Graphics g, Bird bird) {
        // Draw the pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            if (pipe.isVisible()) {
                pipe.draw(g, bird);
            } else {
                Pipe remove = pipes.remove(i);
                PipePool.giveBack(remove);
                i--;
            }
        }
        // Determine whether the bird collides with the pipe
        isCollideBird(bird);
        pipeBornLogic(bird);
    }

    /**
     * If the container is empty, add a pair of pipes. If the last pair of pipes
     * is completely in the game window, add a pair of pipes. Pipes are divided
     * into normal pipes, hover pipes, moving normal pipes, and moving hover
     * pipes. Each couple pipes are divided into top and bottom pipes. Its took
     * `1/4` of the screen height. Pipe's height is between `1/8` and `5/8` of
     * the screen height.
     */
    public static final int VERTICAL_INTERVAL = Constant.FRAME_HEIGHT / 5;
    public static final int HORIZONTAL_INTERVAL = Constant.FRAME_HEIGHT >> 2;
    public static final int MIN_HEIGHT = Constant.FRAME_HEIGHT >> 3;
    public static final int MAX_HEIGHT = ((Constant.FRAME_HEIGHT) >> 3) * 5;

    public void pipeBornLogic(Bird bird) {
        if (bird.isDead()) {
            // No longer add pipes after the bird dies
            return;
        }
        if (pipes.size() == 0) {
            // Of the container is empty, add a pair of pipes
            int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); // Randomly generate pipe height

            Pipe top = PipePool.get("Pipe");
            top.setAttribute(Constant.FRAME_WIDTH, -Constant.TOP_PIPE_LENGTHENING,
                    topHeight + Constant.TOP_PIPE_LENGTHENING, Pipe.TYPE_TOP_NORMAL, true);

            Pipe bottom = PipePool.get("Pipe");
            bottom.setAttribute(Constant.FRAME_WIDTH, topHeight + VERTICAL_INTERVAL,
                    Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL, Pipe.TYPE_BOTTOM_NORMAL, true);

            pipes.add(top);
            pipes.add(bottom);
        } else {

            // Add a pair of pipes if the last pair of pipes is completely in the game window
            Pipe lastPipe = pipes.get(pipes.size() - 1); // Get the last pair of pipes
            int currentDistance = lastPipe.getX() - bird.getBirdX() + Bird.BIRD_WIDTH / 2; // Distance between the last pair of pipes and the bird
            final int SCORE_DISTANCE = Pipe.PIPE_WIDTH * 2 + HORIZONTAL_INTERVAL; // Distance between the bird and the pipe to score
            if (lastPipe.isInFrame()) {
                if (pipes.size() >= PipePool.FULL_PIPE - 2
                        && currentDistance <= SCORE_DISTANCE + Pipe.PIPE_WIDTH * 3 / 2) {
                    ScoreCounter.getInstance().score(bird);
                }
                try {
                    int currentScore = (int) ScoreCounter.getInstance().getCurrentScore() + 1; // Get the current score
                    // Generate normal pipes, hover pipes, moving normal pipes, and moving hover pipes
                    if (GameUtil.isInProbability(currentScore, 20)) {
                        if (GameUtil.isInProbability(1, 4)) // Generate moving normal pipes and moving hover pipes
                        {
                            addMovingHoverPipe(lastPipe);
                        } else {
                            addMovingNormalPipe(lastPipe);
                        }
                    } else {
                        if (GameUtil.isInProbability(1, 2)) // Generate normal pipes and hover pipes
                        {
                            addNormalPipe(lastPipe);
                        } else {
                            addHoverPipe(lastPipe);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Add normal pipes
     *
     * @param lastPipe for getting the x coordinate of the last pair of pipes
     */
    public void addNormalPipe(Pipe lastPipe) {
        int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); // Randomly generate pipe height
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // The x coordinate of the new pipe = the x coordinate of the last pair of pipes + the pipe interval

        Pipe top = PipePool.get("Pipe"); // Get the pipe object from the object pool

        // Set the attributes of the top pipe
        top.setAttribute(x, -Constant.TOP_PIPE_LENGTHENING, topHeight + Constant.TOP_PIPE_LENGTHENING,
                Pipe.TYPE_TOP_NORMAL, true);

        Pipe bottom = PipePool.get("Pipe");
        bottom.setAttribute(x, topHeight + VERTICAL_INTERVAL, Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL,
                Pipe.TYPE_BOTTOM_NORMAL, true);

        pipes.add(top);
        pipes.add(bottom);
    }

    /**
     * Add hover pipes
     *
     * @param lastPipe for getting the x coordinate of the last pair of pipes
     */
    public void addHoverPipe(Pipe lastPipe) {

        // Randomly generate pipe height, [1/4,1/6] of the screen height
        int topHoverHeight = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 6, Constant.FRAME_HEIGHT / 4);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // The x coordinate of the new pipe = the x coordinate of the last pair of pipes + the pipe interval
        int y = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 12, Constant.FRAME_HEIGHT / 6); // Random y coordinate of the pipe, [1/6,1/12] of the window

        int type = Pipe.TYPE_HOVER_NORMAL;

        // Generate the top hover pipe
        Pipe topHover = PipePool.get("Pipe");
        topHover.setAttribute(x, y, topHoverHeight, type, true);

        // Generate the bottom hover pipe
        int bottomHoverHeight = Constant.FRAME_HEIGHT - 2 * y - topHoverHeight - VERTICAL_INTERVAL;
        Pipe bottomHover = PipePool.get("Pipe");
        bottomHover.setAttribute(x, y + topHoverHeight + VERTICAL_INTERVAL, bottomHoverHeight, type, true);

        pipes.add(topHover);
        pipes.add(bottomHover);

    }

    /**
     * Add moving hover pipes
     *
     * @param lastPipe for getting the x coordinate of the last pair of pipes
     */
    public void addMovingHoverPipe(Pipe lastPipe) {

        // Randomly generate pipe height, [1/4,1/6] of the screen height
        int topHoverHeight = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 6, Constant.FRAME_HEIGHT / 4);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // The x coordinate of the new pipe = the x coordinate of the last pair of pipes + the pipe interval
        int y = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 12, Constant.FRAME_HEIGHT / 6); // Random y coordinate of the pipe, [1/6,1/12] of the window

        int type = Pipe.TYPE_HOVER_HARD;

        // Generate the top hover pipe
        Pipe topHover = PipePool.get("MovingPipe");
        topHover.setAttribute(x, y, topHoverHeight, type, true);

        // Generate the bottom hover pipe
        int bottomHoverHeight = Constant.FRAME_HEIGHT - 2 * y - topHoverHeight - VERTICAL_INTERVAL;
        Pipe bottomHover = PipePool.get("MovingPipe");
        bottomHover.setAttribute(x, y + topHoverHeight + VERTICAL_INTERVAL, bottomHoverHeight, type, true);

        pipes.add(topHover);
        pipes.add(bottomHover);

    }

    /**
     * Add moving normal pipes
     *
     * @param lastPipe for getting the x coordinate of the last pair of pipes
     */
    public void addMovingNormalPipe(Pipe lastPipe) {
        int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); // Randomly generate pipe height
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // The x coordinate of the new pipe = the x coordinate of the last pair of pipes + the pipe interval

        Pipe top = PipePool.get("MovingPipe");
        top.setAttribute(x, -Constant.TOP_PIPE_LENGTHENING, topHeight + Constant.TOP_PIPE_LENGTHENING,
                Pipe.TYPE_TOP_HARD, true);

        Pipe bottom = PipePool.get("MovingPipe");
        bottom.setAttribute(x, topHeight + VERTICAL_INTERVAL, Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL,
                Pipe.TYPE_BOTTOM_HARD, true);

        pipes.add(top);
        pipes.add(bottom);
    }

    /**
     * Determine whether the bird collides with the pipe
     *
     * @param bird
     */
    public void isCollideBird(Bird bird) {
        // Determine whether the bird is dead
        if (bird.isDead()) {
            return;
        }
        // Determine whether the bird collides with the pipe
        for (Pipe pipe : pipes) {
            // If the bird collides with the pipe, the bird dies
            if (pipe.getPipeRect().intersects(bird.getBirdCollisionRect())) {
                bird.deadBirdFall();
                return;
            }
        }
    }

    // Reset the game elements
    public void reset() {
        for (Pipe pipe : pipes) {
            PipePool.giveBack(pipe);
        }
        pipes.clear();
    }
}
