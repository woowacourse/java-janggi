package janggi.game;

import janggi.piece.Movable;
import janggi.point.Point;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Point, Movable> runningPieces;
    private Team turn;

    public Board(Map<Point, Movable> runningPieces, Team startTeam) {
        this.runningPieces = runningPieces;
        this.turn = startTeam;
    }

    public static Board init(Team startTeam) {
        Map<Point, Movable> runningPieces = InitialPieces.getAllPieces();

        return new Board(runningPieces, startTeam);
    }

    public void reverseTurn() {
        this.turn = turn.reverse();
    }

    public void move(Point startPoint, Point targetPoint) {
        Movable movingPiece = findPieceByPoint(startPoint);
        validateMovable(startPoint, targetPoint);

        runningPieces.remove(startPoint);
        runningPieces.remove(targetPoint);
        runningPieces.put(targetPoint, movingPiece);
    }

    private void validateMovable(Point startPoint, Point targetPoint) {
        Movable movingPiece = findPieceByPoint(startPoint);

        if (movingPiece.getName().equals("포")) {
            checkMovableWithHurdle(startPoint, targetPoint);
            return;
        }
        checkMovableWithoutHurdle(startPoint, targetPoint);
    }

    private void checkMovableWithoutHurdle(Point startPoint, Point targetPoint) {
        Movable movingPiece = findPieceByPoint(startPoint);

        if (turn != movingPiece.getTeam()) {
            throw new IllegalArgumentException(turn.getText() + "의 기물만 이동할 수 있습니다.");
        }
        if (!movingPiece.isInMovingRange(startPoint, targetPoint)
            || !findRouteHurdles(movingPiece.findRoute(startPoint, targetPoint)).isEmpty()
            || hasTargetPointHurdles(startPoint, targetPoint)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
    }

    private void checkMovableWithHurdle(Point startPoint, Point targetPoint) {
        Movable movingPiece = findPieceByPoint(startPoint);
        List<Point> route = movingPiece.findRoute(startPoint, targetPoint);

        if (turn != movingPiece.getTeam()) {
            throw new IllegalArgumentException(turn.getText() + "의 기물만 이동할 수 있습니다.");
        }
        if (!movingPiece.isInMovingRange(startPoint, targetPoint)
            || hasTargetPointHurdles(startPoint, targetPoint)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        if (countJumperForPo(route) != 1) {
            throw new IllegalArgumentException("포는 포를 제외한 하나의 기물만 필요합니다.");
        }
        if (runningPieces.containsKey(targetPoint)
            && findPieceByPoint(targetPoint).getName().equals("포")) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    private boolean hasTargetPointHurdles(Point startPoint, Point targetPoint) {
        if (runningPieces.containsKey(targetPoint)) {
            Team startPieceTeam = findPieceByPoint(startPoint).getTeam();
            Team targetPieceTeam = findPieceByPoint(targetPoint).getTeam();

            return startPieceTeam == targetPieceTeam;
        }
        return false;
    }

    private int countJumperForPo(List<Point> route) {
        return (int) findRouteHurdles(route).stream()
            .filter(point -> !findPieceByPoint(point).getName().equals("포"))
            .count();
    }

    private Movable findPieceByPoint(Point point) {
        if (runningPieces.containsKey(point)) {
            return runningPieces.get(point);
        }
        throw new IllegalArgumentException("해당 좌표에 기물이 존재하지 않습니다.");
    }

    private List<Point> findRouteHurdles(List<Point> route) {
        return route.stream()
            .filter(runningPieces::containsKey)
            .toList();
    }

    public Map<Point, Movable> getRunningPieces() {
        return Collections.unmodifiableMap(runningPieces);
    }

    public Team getTurn() {
        return turn;
    }
}
