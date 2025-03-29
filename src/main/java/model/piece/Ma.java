package model.piece;

import java.util.Map;
import model.Moving;
import model.Point;
import model.Team;

public class Ma extends Piece {

    private static final int MA_DISTANCE = 5;
    private static final int MA_SCORE = 5;

    public Ma(Team team) {
        super(team, PieceName.MA);
        score = MA_SCORE;
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        Moving moving = new Moving(beforePoint, targetPoint);

        return moving.isDistance(MA_DISTANCE);
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() == 2) {

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
