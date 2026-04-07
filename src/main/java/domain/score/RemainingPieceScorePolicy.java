package domain.score;

import domain.Side;
import domain.board.Board;
import domain.piece.Piece;

public class RemainingPieceScorePolicy implements ScorePolicy {
    @Override
    public int calculate(Board board, Side side) {
        return board.getBoard().values().stream()
                .filter(piece -> piece.isAlly(side))
                .map(Piece::getScore)
                .reduce(0, Integer::sum);
    }
}
