package model.piece;

import java.util.Map;
import model.Point;
import model.Team;
import model.Moving;

public class Sang extends Piece {

    private static final int SANG_SCORE = 3;
    private static final int SNG_DISTANCE = 13;

    public Sang(Team team) {
        super(team, PieceName.SANG);
        score = SANG_SCORE;
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        Moving moving = new Moving(beforePoint, targetPoint);

        return moving.isDistance(SNG_DISTANCE);
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() >= 2) {
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
