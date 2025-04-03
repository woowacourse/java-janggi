package model.piece;

import java.util.Map;
import model.Moving;
import model.Point;
import model.Team;

public class Jang extends FixedPalacePieces {

    private static final int JANG_DISTANCE = 1;
    private static final int MINIMUM_PIECES_COUNT_IN_PATH = 1;

    public Jang(Team team) {
        super(team, PieceName.JANG, Score.JANG);
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() == MINIMUM_PIECES_COUNT_IN_PATH) {
            return piecesOnPathWithTargetOrNot.keySet()
                    .stream()
                    .findFirst()
                    .get()
                    .getTeam() != getTeam();
        }
        return true;
    }

    @Override
    public void validateGungMove(Point beforePoint, Point targetPoint) {
        Moving moving = new Moving(beforePoint, targetPoint);

        if (isValidGungMove(moving, beforePoint)) {
            return;
        }

        throw new IllegalArgumentException("잘못된 이동입니다.");
    }

    private boolean isValidGungMove(Moving moving, Point beforePoint) {
        if (Palace.wherePalace(beforePoint).getPoints().contains(beforePoint)) {
            return moving.isDistance(JANG_DISTANCE) ||
                    (moving.getVectorXSize() == JANG_DISTANCE && moving.getVectorYSize() == JANG_DISTANCE);
        }
        return moving.isDistance(JANG_DISTANCE);
    }
}
