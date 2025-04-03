package model.piece;

import java.util.Map;
import model.Moving;
import model.Point;
import model.Team;

public class Sang extends Piece {

    private static final int SNG_DISTANCE = 13;
    private static final int MINIMUM_PIECES_COUNT_IN_PATH = 1;

    public Sang(Team team) {
        super(team, PieceName.SANG, Score.SANG);
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        Moving moving = new Moving(beforePoint, targetPoint);

        return moving.isDistance(SNG_DISTANCE);
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() > MINIMUM_PIECES_COUNT_IN_PATH) {
            return false;
        }
        if (piecesOnPathWithTargetOrNot.size() == MINIMUM_PIECES_COUNT_IN_PATH) {
            if (!piecesOnPathWithTargetOrNot.values()
                    .stream()
                    .findFirst()
                    .get()) {
                return false;
            }
            return isEnemy(piecesOnPathWithTargetOrNot.keySet()
                    .stream()
                    .findFirst()
                    .get());
        }
        return true;
    }

}
