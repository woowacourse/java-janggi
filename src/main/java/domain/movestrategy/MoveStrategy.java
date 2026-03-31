package domain.movestrategy;

import domain.board.Board;
import domain.piece.Position;
import java.util.List;

public interface MoveStrategy {

    List<Position> calculateMovablePositions(Position from, Board board);

    default boolean isAlly(Position from, Position to, Board board) {
        if (!board.hasPiece(to)) {
            return false;
        }
        return board.getPiece(from).isSameTeam(board.getPiece(to));
    }
}
