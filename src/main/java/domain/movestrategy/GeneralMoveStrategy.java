package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Position;
import java.util.List;

public class GeneralMoveStrategy implements MoveStrategy {

    private static final List<Delta> ALL_DIRECTIONS = List.of(
            Delta.UP, Delta.RIGHT_UP, Delta.RIGHT, Delta.RIGHT_DOWN,
            Delta.DOWN, Delta.LEFT_DOWN, Delta.LEFT, Delta.LEFT_UP
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Board board) {
        return ALL_DIRECTIONS.stream()
                .map(from::move)
                .filter(board::inBoard)
                .filter(to -> !isFacing(from, to, board))
                .filter(to -> !isAlly(from, to, board))
                .toList();
    }


    private boolean isFacing(final Position from, final Position to, final Board board) {
        Position enemyGeneral = board.findGeneral(board.getPiece(from).opponentTeam());

        if (to.column() != enemyGeneral.column()) {
            return false;
        }

        int start = Math.min(to.row(), enemyGeneral.row()) + 1;
        int end = Math.max(to.row(), enemyGeneral.row());

        for (int row = start; row < end; row++) {
            Position pos = Position.of(row, to.column());

            if (!pos.equals(from) && board.hasPiece(pos)) {
                return false;
            }
        }

        return true;
    }
}
