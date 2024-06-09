package com.popox.flappybird.component;

import com.popox.flappybird.util.Constant;
import java.util.ArrayList;
import java.util.List;

/**
 * Pipe pool For creating and recycling pipes, use the object pool pattern to
 * alreay created pipes. Use the object pool pattern to manage the pipe objects.
 *
 * @author popox
 *
 */
public class PipePool {

    private static final List<Pipe> pool = new ArrayList<>(); // List of objects in the pool
    private static final List<MovingPipe> movingPool = new ArrayList<>(); // List of moving objects in the pool
    public static final int MAX_PIPE_COUNT = 30; // Maximum number of pipes
    public static final int FULL_PIPE = (Constant.FRAME_WIDTH
            / (Pipe.PIPE_HEAD_WIDTH + GameElementLayer.HORIZONTAL_INTERVAL) + 2) * 2;

    static {
        for (int i = 0; i < PipePool.FULL_PIPE; i++) {
            pool.add(new Pipe());
        }
        for (int i = 0; i < PipePool.FULL_PIPE; i++) {
            movingPool.add(new MovingPipe());
        }
    }

    /**
     * Get the object from the pool
     *
     * @return Pipe object
     */
    public static Pipe get(String className) {
        if ("Pipe".equals(className)) {
            int size = pool.size();
            if (size > 0) {
                return pool.remove(size - 1); // Remove and return the last one
            } else {
                return new Pipe(); // Empty object pool, return a new object
            }
        } else {
            int size = movingPool.size();
            if (size > 0) {
                return movingPool.remove(size - 1); // Remove and return the last one
            } else {
                return new MovingPipe(); // Empty object pool, return a new object
            }
        }
    }

    /**
     * Return the object to the pool
     */
    public static void giveBack(Pipe pipe) {
        // Check the type of the pipe
        if (pipe.getClass() == Pipe.class) {
            if (pool.size() < MAX_PIPE_COUNT) {
                pool.add(pipe);
            }
        } else {
            if (movingPool.size() < MAX_PIPE_COUNT) {
                movingPool.add((MovingPipe) pipe);
            }
        }
    }
}
