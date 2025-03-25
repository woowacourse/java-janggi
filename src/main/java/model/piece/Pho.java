package model.piece;

import java.util.Map;
import java.util.Map.Entry;
import model.Point;
import model.Team;

public class Pho extends Piece {

    public Pho(Team team) {
        super(team,PieceName.PHO);
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        int vectorX = getVectorX(beforePoint, targetPoint);
        int vectorY = getVectorY(beforePoint, targetPoint);
        return (vectorX == 0) ^ (vectorY == 0);
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() >= 3) {
            return false;
        }
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            if (piecesOnPathWithTargetOrNot.values()
                    .stream().findFirst().get() || piecesOnPathWithTargetOrNot.keySet()
                    .stream().findFirst().get() instanceof Pho) {
                return false;
            }
            return true;
        }

        if (piecesOnPathWithTargetOrNot.size() == 2) {
            if (piecesOnPathWithTargetOrNot
                    .keySet()
                    .stream()
                    .anyMatch(piece -> piece instanceof Pho)) {
                return false;
            }

            if (piecesOnPathWithTargetOrNot.values()
                    .stream()
                    .noneMatch(isTargetPoint -> isTargetPoint)) {
                return false;
            }

            if (piecesOnPathWithTargetOrNot.entrySet().stream()
                    .filter(Entry::getValue)
                    .findFirst()
                    .get()
                    .getKey()
                    .getTeam() == this.team) {
                return false;
            }
            return true;
        }
        return true;
    }
}
