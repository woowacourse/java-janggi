package domain.strategy;

import domain.PalacePosition;
import domain.Position;
import domain.Team;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import util.CollisionValidator;

import java.util.ArrayList;
import java.util.List;

public class PalaceStrategy implements MoveStrategy {

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Piece piece = board.getPiece(currentPosition);
        Team team = piece.getTeam();
        if (!currentPosition.isInsidePalace()) {
            return candidates;
        }
        Position relativePosition = currentPosition.toRelative();
        PalacePosition palacePosition = PalacePosition.findByPosition(relativePosition);

        for (Direction palaceDirection : palacePosition.getDirections()) {
            int targetRow = currentPosition.getRows() + palaceDirection.getRowOffset();
            int targetColumn = currentPosition.getColumns() + palaceDirection.getColOffset();

            Position targetPosition = new Position(targetRow, targetColumn);
            if (CollisionValidator.canMoveToTarget(targetPosition, board, team) && isStayInPalace(targetPosition, team)) {
                candidates.add(targetPosition);
            }
        }
        return candidates;
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
