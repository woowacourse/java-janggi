package domain.strategy;

import domain.PalacePosition;
import domain.Position;
import domain.Team;
import domain.piece.PieceProvider;
import util.CollisionValidator;

import java.util.ArrayList;
import java.util.List;

public class PalaceStrategy implements MoveStrategy {

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        if (!currentPosition.isInsidePalace()) {
            return new ArrayList<>();
        }

        Team team = board.getPiece(currentPosition).getTeam();
        return findDirections(currentPosition).stream()
                .map(direction -> calculateTarget(currentPosition, direction))
                .filter(targetPosition -> isCorrectMove(targetPosition, board, team))
                .toList();
    }

    private List<Direction> findDirections(Position currentPosition) {
        Position relative = currentPosition.toRelative();
        PalacePosition palacePosition = PalacePosition.findByPosition(relative);
        return palacePosition.getDirections();
    }

    private Position calculateTarget(Position currentPosition, Direction direction) {
        int targetRow = currentPosition.getRows() + direction.getRowOffset();
        int targetColumn = currentPosition.getColumns() + direction.getColOffset();
        return new Position(targetRow, targetColumn);
    }

    private boolean isCorrectMove(Position targetPosition, PieceProvider board, Team team) {
        return CollisionValidator.canMoveToTarget(targetPosition, board, team)
                && isStayInPalace(targetPosition, team);
    }

    private boolean isStayInPalace(Position targetPosition, Team team) {
        if (team == Team.HAN) {
            return targetPosition.isInsideHanPalace();
        }
        if (team == Team.CHO) {
            return targetPosition.isInsideChoPalace();
        }
        return false;
    }
}
