package com.popox.flappybird.component;

import com.popox.flappybird.util.Constant;
import com.popox.flappybird.util.MusicUtil;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * ScoreCounter, count the score
 *
 * @author Po Pox
 *
 */
public class ScoreCounter {

    private static class ScoreCounterHolder {

        private static final ScoreCounter scoreCounter = new ScoreCounter();
    }

    public static ScoreCounter getInstance() {
        return ScoreCounterHolder.scoreCounter;
    }

    private long score = 0; // score
    private long bestScore; // best score

    private ScoreCounter() {
        bestScore = -1;
        try {
            loadBestScore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load the best score
    private void loadBestScore() throws Exception {
        File file = new File(Constant.SCORE_FILE_PATH);
        if (file.exists()) {
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            bestScore = dis.readLong();
            dis.close();
        }
    }

    public void saveScore() {
        bestScore = Math.max(bestScore, getCurrentScore());
        try {
            File file = new File(Constant.SCORE_FILE_PATH);
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
            dos.writeLong(bestScore);
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void score(Bird bird) {
        if (!bird.isDead()) {
            MusicUtil.playScore();
            score += 1;
        }
    }

    public long getBestScore() {
        return bestScore;
    }

    public long getCurrentScore() {
        return score;
    }

    public void reset() {
        score = 0;
    }

}
