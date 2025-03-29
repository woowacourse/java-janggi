package model.piece;

import java.util.Map;
import model.Moving;
import model.Point;
import model.Team;

public class Jang extends FixedPalacePieces {

    private static final int JANG_DISTANCE = 1;

    public Jang(Team team) {
        super(team, PieceName.JANG);
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            return piecesOnPathWithTargetOrNot.keySet()
                    .stream()
                    .findFirst()
                    .get()
                    .getTeam() != this.team;
        }
        return true;
    }

    @Override
    public void validateGungMove(Point beforePoint, Point targetPoint) {
        Moving moving = new Moving(beforePoint, targetPoint);
        Palace palace = Palace.wherePalace(beforePoint);
        if ((palace.getPoints().contains(beforePoint))) {
            if (moving.getVectorXSize() == JANG_DISTANCE && moving.getVectorYSize() == JANG_DISTANCE) {
                return;
            }
            if (moving.isDistance(JANG_DISTANCE)) {
                return;
            }
        }

        if (moving.isDistance(JANG_DISTANCE)) {
            return;
        }
        throw new IllegalArgumentException("잘못된 이동입니다.");
    }
}
