package model.piece;

import java.util.Map;
import model.Path;
import model.Point;
import model.Team;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team,PieceName.CHA);
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        int vectorX = getVectorX(beforePoint, targetPoint);
        int vectorY = getVectorY(beforePoint, targetPoint);
        return (vectorX == 0) ^ (vectorY == 0);
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
