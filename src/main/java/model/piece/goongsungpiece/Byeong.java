package model.piece.goongsungpiece;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import model.Path;
import model.Point;
import model.Team;
import model.piece.Piece;
import model.piece.PieceInfo;

public class Byeong extends Piece {

    public Byeong(Team team) {
        super(team);
        pieceInfo = PieceInfo.BYEONG;
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        Point blueGoongsungCenterPoint = Point.of(4, 1);
        Point redGoongsungCenterPoint = Point.of(4, 8);

        int moveForward = 1;

        if (team == Team.RED) {
            if (useEnemyGoongsung(beforePoint, targetPoint, blueGoongsungCenterPoint)) {
                return true;
            }
            moveForward = -1;
        }

        if (useEnemyGoongsung(beforePoint, targetPoint, redGoongsungCenterPoint)) {
            return true;
        }

        List<Integer> horizontal = List.of(0, -1, 1);
        List<Integer> vertical = List.of(moveForward, 0, 0);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforePoint.x() == targetPoint.x()
                        && vertical.get(i) + beforePoint.y() == targetPoint.y());
    }

    private boolean useEnemyGoongsung(Point beforePoint, Point targetPoint, Point redGoongsungCenterPoint) {
        if (isInsideGoongsung(redGoongsungCenterPoint, beforePoint) &&
                containsCenterPoint(calculatePath(beforePoint, targetPoint), redGoongsungCenterPoint) &&
                isForward(team, beforePoint, targetPoint)) {
            return true;
        }
        return false;
    }

    private boolean isInsideGoongsung(Point goongsungCenterPoint, Point point) {
        return Math.abs(goongsungCenterPoint.x() - point.x()) <= 1 &&
                Math.abs(goongsungCenterPoint.y() - point.y()) <= 1;
    }

    private boolean containsCenterPoint(Path path, Point enemyGoongsungPoint) {
        return path.getPath().stream()
                .anyMatch(point -> point.equals(enemyGoongsungPoint));
    }

    private boolean isForward(Team team, Point beforePoint, Point targetPoint) {
        if (team == Team.RED) {
            return beforePoint.y() > targetPoint.y();
        }
        return beforePoint.y() < targetPoint.y();
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
