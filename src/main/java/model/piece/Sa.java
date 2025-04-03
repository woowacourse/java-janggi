package model.piece;

import java.util.Map;
import model.Moving;
import model.Point;
import model.Team;

public class Sa extends FixedPalacePieces {

    private static final int SA_DISTANCE = 1;

    public Sa(Team team) {
        super(team,PieceName.SA,Score.SA);
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() == 1) {
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
        Palace palace = Palace.wherePalace(beforePoint);
        if ((palace.getPoints().contains(beforePoint))) {
            if (moving.getVectorXSize() == SA_DISTANCE && moving.getVectorYSize() == SA_DISTANCE) {
                return;
            }
            if (moving.isDistance(SA_DISTANCE)) {
                return;
            }
        }

        if (moving.isDistance(SA_DISTANCE)) {
            return;
        }
        throw new IllegalArgumentException("잘못된 이동입니다.");
    }
}
