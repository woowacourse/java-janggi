package model.piece.goongsungpiece;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import model.Point;
import model.Team;
import model.janggiboard.JangSaGoongsungRule;
import model.piece.Piece;

class GoongsungConstrainedPiece extends Piece {

    private JangSaGoongsungRule jangSaGoongsungRule;

    public GoongsungConstrainedPiece(Team team) {
        super(team);
        initJangSaGoongsungRule(team);
    }

    private void initJangSaGoongsungRule(Team team) {
        if (team == Team.BLUE) {
            jangSaGoongsungRule = new JangSaGoongsungRule(Point.of(4, 1));
        }
        if (team == Team.RED) {
            jangSaGoongsungRule = new JangSaGoongsungRule(Point.of(4, 8));
        }
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        jangSaGoongsungRule.validateOutOfGoongsung(targetPoint);
        if (jangSaGoongsungRule.containsCenterPoint(beforePoint, targetPoint)) {
            return true;
        }

        List<Integer> horizontal = List.of(0, 0, -1, 1);
        List<Integer> vertical = List.of(1, -1, 0, 0);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforePoint.x() == targetPoint.x()
                        && vertical.get(i) + beforePoint.y() == targetPoint.y());
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
}
