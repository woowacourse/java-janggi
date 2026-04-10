package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;
import domain.player.Team;

import java.util.ArrayList;
import java.util.List;

public class GeneralMoveStrategy implements MoveStrategy {

    private static final List<Delta> ORTHOGONAL_DIRECTIONS = List.of(
            Delta.UP, Delta.RIGHT, Delta.DOWN, Delta.LEFT
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Board board) {
        final Piece fromPiece = board.getPiece(from);
        final Team team = fromPiece.getTeam();

        final List<Delta> deltas = new ArrayList<>(ORTHOGONAL_DIRECTIONS);
        deltas.addAll(board.getPalaceDeltas(from));

        return deltas.stream()
                .map(from::move)
                .filter(board::inBoard)
                .filter(to -> board.inAllyPalace(to, team))
                .filter(to -> !isDirectlyFacingEnemyGeneral(from, to, board))
                .filter(to -> !isAlly(from, to, board))
                .toList();
    }

    @Override
    public List<Position> calculatePalaceMovablePositions(final Position from, final Board board) {
        return List.of();
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
