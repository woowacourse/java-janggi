package model.piece;

import java.util.Map;
import java.util.Map.Entry;
import model.Moving;
import model.Point;
import model.Team;

public class Pho extends PalaceMovablePieces {

    private static final int PO_SCORE = 7;

    public Pho(Team team) {
        super(team, PieceName.PO);
        score = PO_SCORE;
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
        return false;
    }

    @Override
    public void validateGungCross(Point beforePoint, Point targetPoint) {
        Moving moving = new Moving(beforePoint, targetPoint);
        Palace palace = Palace.wherePalace(beforePoint);
        if (palace.getPoints().contains(targetPoint)) {
            if (moving.isDistance(8)) {
                return;
            }
        }
        if (!moving.isUpDownMoving()) {
            throw new IllegalArgumentException("잘못된 이동입니다.");
        }
    }

    @Override
    public void validateMovement(Point beforePoint, Point targetPoint) {
        Moving moving = new Moving(beforePoint, targetPoint);
        if (!moving.isUpDownMoving()) {
            throw new IllegalArgumentException("잘못된 이동입니다.");
        }
    }
}
