package model.piece;

import java.util.Map;
import model.Moving;
import model.Point;
import model.Team;

public class Cha extends PalaceMovablePieces {

    public Cha(Team team) {
        super(team, PieceName.CHA, Score.CHA);
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
                    .getTeam() != getTeam();
        }
        return false;
    }

    @Override
    public void validateGungCross(Point beforePoint, Point targetPoint) {
        Moving moving = new Moving(beforePoint, targetPoint);

        if (Palace.ALL_PALACE.getPoints().contains(targetPoint)) {
            if (moving.isDistanceLessThanOrEqualTo(8)) {
                return;
            }
            throw new IllegalArgumentException("잘못 된 이동입니다.");
        }
        if (!moving.isUpDownMoving()) {
            throw new IllegalArgumentException("잘못 된 이동입니다.");
        }
    }

    @Override
    public void validateMovement(Point beforePoint, Point targetPoint) {
        Moving moving = new Moving(beforePoint, targetPoint);

        if (!moving.isUpDownMoving()) {
            throw new IllegalArgumentException("잘못 된 이동입니다.");
        }
    }
}
