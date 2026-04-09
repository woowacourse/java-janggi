package janggi.model;

import janggi.model.board.Board;

public class ScorePolicy {

    private static final float DUM = 1.5f;

    public float getScoreOf(Board board, Team team) {
        float totalScore = board.getMaterialScoreOf(team);

        if (team == Team.HAN) {
            totalScore += DUM;
        }

        return totalScore;
    }
}
