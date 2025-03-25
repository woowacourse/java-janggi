package model.piece.goongsungpiece;

import java.util.Map;
import model.Point;
import model.Team;
import model.piece.Piece;
import model.piece.PieceName;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team);
        pieceName = PieceName.CHA;
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        boolean isStraightMove = beforePoint.x() == targetPoint.x() || beforePoint.y() == targetPoint.y();
        boolean isSamePoint = beforePoint.equals(targetPoint);

        return isStraightMove && !isSamePoint;
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.isEmpty()) {
            return true;
        }
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            if (!piecesOnPathWithTargetOrNot.values()
                    .stream()
                    .findFirst()
                    .get()) {
                return false;
            }
            return piecesOnPathWithTargetOrNot.keySet()
                    .stream()
                    .findFirst()
                    .get()
                    .getTeam() != this.team;
        }
        return false;
    }
}
