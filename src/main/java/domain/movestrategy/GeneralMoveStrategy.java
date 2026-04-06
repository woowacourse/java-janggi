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
                .filter(to -> !isDirectlyFacingEnemyGeneral(from, to, board))
                .filter(to -> !isAlly(from, to, board))
                .toList();
    }


    private boolean isDirectlyFacingEnemyGeneral(final Position from, final Position to, final Board board) {
        final Position enemyGeneral = board.findGeneral(board.getPiece(from).opponentTeam());

        if (to.column() != enemyGeneral.column()) {
            return false;
        }

        final int rowBetweenGeneralStart = Math.min(to.row(), enemyGeneral.row()) + 1;
        final int rowBetweenGeneralEnd = Math.max(to.row(), enemyGeneral.row());

        for (int row = rowBetweenGeneralStart; row < rowBetweenGeneralEnd; row++) {
            final Position position = Position.of(row, to.column());

            if (!position.equals(from) && board.hasPiece(position)) {
                return false;
            }
        }

        return true;
    }
}
