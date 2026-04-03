package domain.board;

import domain.intersection.Intersection;
import domain.intersection.palace.NormalIntersection;
import domain.move.rule.MoveRuleManager;
import domain.move.path.Path;
import domain.piece.Team;
import domain.point.Point;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static common.constant.JanggiConstant.*;

public class JanggiBoard {

    private final Map<Point, Intersection> intersections;
    private final MoveRuleManager moveRuleManager;

    public JanggiBoard(IntersectionGenerator intersectionGenerator) {
        this.intersections = createIntersections(intersectionGenerator);
        this.moveRuleManager = new MoveRuleManager();
    }

    public void tryToMove(Point start, Point end, Team currentTeam) {
        Intersection origin = findOriginIntersection(start, currentTeam);
        Intersection destination = findIntersection(end);
        inspectPath(origin, destination);
        origin.move(destination);
    }

    private void inspectPath(Intersection origin, Intersection destination) {
        Path path = buildPath(moveRuleManager.findPathOfPoints(origin, destination))
                .addOrigin(origin);
        moveRuleManager.inspectPathByMoveRule(path);
    }

    private Path buildPath(List<Point> possiblePoints) {
        List<Intersection> intersectionOfPath = possiblePoints.stream()
                .map(this::findIntersection)
                .toList();
        return new Path(intersectionOfPath);
    }

    public Intersection findOriginIntersection(Point point, Team team) {
        Intersection origin = findIntersection(point);
        origin.validateMovable(team);
        return origin;
    }

    public Intersection findIntersection(Point point) {
        return intersections.get(point);
    }

    public boolean isGameOver() {
        return Arrays.stream(Team.values())
                .filter(team -> team != Team.NONE)
                .anyMatch(this::isGeneralDead);
    }

    public Team getWinner() {
        if (isGeneralDead(Team.HAN)) {
            return Team.CHO;
        }
        return Team.HAN;
    }

    public boolean isGeneralDead(Team team) {
        return intersections.values().stream()
                .filter(Intersection::hasPiece)
                .filter(intersection -> intersection.isSameTeam(team))
                .noneMatch(Intersection::hasGeneral);
    }

    public Map<Point, Intersection> getJanggiBoard() {
        return Collections.unmodifiableMap(intersections);
    }

    private Map<Point, Intersection> createIntersections(IntersectionGenerator intersectionGenerator) {
        Map<Point, Intersection> intersections = fillEmptyIntersections();
        for (Intersection intersection : intersectionGenerator.makeIntersection()) {
            intersections.put(intersection.getPoint(), intersection);
        }
        return intersections;
    }

    private Map<Point, Intersection> fillEmptyIntersections() {
        return getAllPoints()
                .collect(Collectors.toMap(point -> point, NormalIntersection::empty));
    }

    private Stream<Point> getAllPoints() {
        return range(MAX_ROW).boxed()
                .flatMap(row -> range(MAX_FILE).mapToObj(file -> new Point(row, file)));
    }

    private IntStream range(int maxRange) {
        return IntStream.range(BASE_POINT, maxRange);
    }

}
