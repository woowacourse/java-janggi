package model.piece;

import java.util.Map;
import model.Path;
import model.Point;
import model.Team;

public class Ma extends Piece {

    public Ma(Team team) {
        super(team, PieceName.MA);
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        int vectorX = getVectorX(beforePoint, targetPoint);
        int vectorY = getVectorY(beforePoint, targetPoint);

        return Math.pow(vectorX, 2) + Math.pow(vectorY, 2) == 5;
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() == 2) {
            return false;
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
        return true;
    }
}
