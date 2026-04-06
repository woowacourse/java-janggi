package domain.game;

import domain.board.Board;
import domain.pieces.Piece;

public class GameScoreCalculator {

    private static final double SECOND_PLAYER_BONUS = 1.5;

    public GameScore calculate(Board board) {
        Score choScore = new Score(0);
        Score hanScore = new Score(0);

        for (Piece piece : board.pieces().values()) {
            if (piece.isEmpty()) {
                continue;
            }
            if (piece.isCho()) {
                choScore = choScore.add(piece.getType().score());
                continue;
            }
            hanScore = hanScore.add(piece.getType().score());
        }
        hanScore = hanScore.add(new Score(SECOND_PLAYER_BONUS));
        return new GameScore(choScore, hanScore);
    }
}
