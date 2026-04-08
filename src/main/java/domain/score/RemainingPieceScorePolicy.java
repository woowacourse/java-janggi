package domain.score;

import domain.Side;
import domain.board.Board;
import domain.piece.Piece;

public class RemainingPieceScorePolicy implements ScorePolicy {
    private static final double HAN_KOMI = 1.5;

    @Override
    public double calculate(Board board, Side side) {
        int pieceScore = board.getBoard().values().stream()
                .filter(piece -> piece.isAlly(side))
                .map(Piece::getScore)
                .reduce(0, Integer::sum);

        if (side == Side.HAN) {
            return pieceScore + HAN_KOMI;
        }
        return pieceScore;
    }
}
