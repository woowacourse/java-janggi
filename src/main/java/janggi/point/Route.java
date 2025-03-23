package janggi.point;

import janggi.game.Board;
import janggi.game.Team;
import janggi.piece.Movable;
import java.util.List;
import java.util.Set;

public class Route {

    private static final int NEEDED_HURDLE_NUMBER = 1;

    private final List<Point> path;
    private final Point targetPoint;

    public Route(List<Point> path, Point targetPoint) {
        this.path = path;
        this.targetPoint = targetPoint;
    }

    public void validateRoute(Point startPoint, Board board) {
        Movable movingPiece = board.findPieceByPoint(startPoint);

        if (movingPiece.getName().equals("포")) {
            checkRouteWithHurdle(startPoint, board);
            return;
        }
        checkRouteWithoutHurdles(startPoint, board);
    }

    private void checkRouteWithHurdle(Point startPoint, Board board) {
        if (hasTargetPointHurdles(startPoint, board)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        if (countJumperForPo(board) != NEEDED_HURDLE_NUMBER) {
            throw new IllegalArgumentException("포는 포를 제외한 하나의 기물만 필요합니다.");
        }
        if (board.getRunningPieces().containsKey(targetPoint)
            && board.findPieceByPoint(targetPoint).getName().equals("포")) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    private void checkRouteWithoutHurdles(Point startPoint, Board board) {
        if (!findRouteHurdles(board.getRunningPieces().keySet()).isEmpty()
            || hasTargetPointHurdles(startPoint, board)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
    }

    private boolean hasTargetPointHurdles(Point startPoint, Board board) {
        if (board.getRunningPieces().containsKey(targetPoint)) {
            Team startPieceTeam = board.findPieceByPoint(startPoint).getTeam();
            Team targetPieceTeam = board.findPieceByPoint(targetPoint).getTeam();

            return startPieceTeam == targetPieceTeam;
        }
        return false;
    }

    private int countJumperForPo(Board board) {
        return (int) findRouteHurdles(board.getRunningPieces().keySet()).stream()
            .filter(point -> !board.findPieceByPoint(point).getName().equals("포"))
            .count();
    }

    private List<Point> findRouteHurdles(Set<Point> pointsWithPiece) {
        return path.stream()
            .filter(pointsWithPiece::contains)
            .toList();
    }

    public List<Point> getPath() {
        return path;
    }

    public Point getTargetPoint() {
        return targetPoint;
    }
}
