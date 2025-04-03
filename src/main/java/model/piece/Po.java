package model.piece;

import java.util.Map;
import java.util.Map.Entry;
import model.Moving;
import model.Point;
import model.Team;

public class Po extends PalaceMovablePieces {

    private static final int MINIMUM_PIECES_COUNT_IN_PATH = 1;
    private static final int PO_DISTANCE = 8;
    private static final int MAXIMUM_PIECES_COUNT_IN_PATH = 2;

    public Po(Team team) {
        super(team, PieceName.PO, Score.PO);
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() > MAXIMUM_PIECES_COUNT_IN_PATH) {
            return false;
        }
        if (piecesOnPathWithTargetOrNot.size() == MINIMUM_PIECES_COUNT_IN_PATH) {
            if (piecesOnPathWithTargetOrNot.values()
                    .stream().findFirst().get() || piecesOnPathWithTargetOrNot.keySet()
                    .stream().findFirst().get() instanceof Po) {
                return false;
            }
            return true;
        }

        if (piecesOnPathWithTargetOrNot.size() == MAXIMUM_PIECES_COUNT_IN_PATH) {
            if (piecesOnPathWithTargetOrNot
                    .keySet()
                    .stream()
                    .anyMatch(piece -> piece instanceof Po)) {
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
                    .getTeam() == getTeam()) {
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
            if (moving.isDistance(PO_DISTANCE)) {
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
