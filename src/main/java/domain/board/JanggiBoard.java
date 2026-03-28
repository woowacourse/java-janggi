package domain.board;

import domain.intersection.Intersection;
import domain.move.rule.MoveRuleManager;
import domain.move.path.Path;
import domain.point.Point;

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

    public void tryToMove(Point start, Point end) {
        Intersection from = findIntersection(start);
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

    public Intersection findIntersection(Point point) {
        return intersections.get(point);
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
