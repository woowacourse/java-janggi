package domain.board;

import domain.intersection.Intersection;
import domain.move.rule.MoveRuleManager;
import domain.move.path.Path;
import domain.piece.Team;
import domain.point.Point;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JanggiBoard {

    private static final int MAX_ROW = 10;
    private static final int MAX_FILE = 9;

    private final Map<Point, Intersection> intersections;
    private final MoveRuleManager moveRuleManager;

    public JanggiBoard(IntersectionGenerator intersectionGenerator) {
        this.intersections = fillEmptyIntersections();
        this.moveRuleManager = new MoveRuleManager();
        for (Intersection intersection : intersectionGenerator.makeIntersection()) {
            intersections.put(intersection.getPoint(), intersection);
        }
    }

    public void tryToMove(Point start, Point end, Team currentTeam) {
        Intersection from = findMyIntersection(start, currentTeam);
        Intersection to = findIntersection(end);
        inspectPath(from, to);
        move(from, to);
    }

    private void move(Intersection from, Intersection to) {
        to.arrive(from);
        from.leave();
    }

    private void inspectPath(Intersection from, Intersection to) {
        Path path = buildPath(moveRuleManager.findPathOfPoints(from, to));
        moveRuleManager.inspectPathByMoveRule(from, path);
    }

    private Path buildPath(List<Point> possiblePoints) {
        List<Intersection> intersectionOfPath = possiblePoints.stream()
                .map(this::findIntersection)
                .toList();
        return new Path(intersectionOfPath);
    }

    public Intersection findMyIntersection(Point point, Team team) {
        Intersection from = findIntersection(point);
        validateIsEmptyIntersection(from);
        validateTryToMoveOpponentPiece(from, team);
        return from;
    }

    public Intersection findIntersection(Point point) {
        return intersections.get(point);
    }

    private void validateTryToMoveOpponentPiece(Intersection from, Team currentTeam) {
        if (!from.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("상대방 기물은 이동시킬 수 없습니다.");
        }
    }

    private void validateIsEmptyIntersection(Intersection from) {
        if (!from.hasPiece()) {
            throw new IllegalArgumentException("기물이 없어 움직일 수 없습니다.");
        }
    }

    private Map<Point, Intersection> fillEmptyIntersections() {
        return getAllPoints()
                .collect(Collectors.toMap(point -> point, Intersection::empty));
    }

    private Stream<Point> getAllPoints() {
        return range(MAX_ROW).boxed()
                .flatMap(row -> range(MAX_FILE).mapToObj(file -> new Point(row, file)));
    }

    private IntStream range(int maxRange) {
        return IntStream.range(0, maxRange);
    }

}
