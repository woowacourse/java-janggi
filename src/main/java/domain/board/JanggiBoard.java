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

import static common.constant.JanggiConstant.*;

public class JanggiBoard {

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
        from.move(to);
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
        from.validateMovable(team);
        return from;
    }

    public Intersection findIntersection(Point point) {
        return intersections.get(point);
    }

    public boolean isGameOver() {
        return Arrays.stream(Team.values())
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

    private Map<Point, Intersection> fillEmptyIntersections() {
        return getAllPoints()
                .collect(Collectors.toMap(point -> point, Intersection::empty));
    }

    private Stream<Point> getAllPoints() {
        return range(MAX_ROW).boxed()
                .flatMap(row -> range(MAX_FILE).mapToObj(file -> new Point(row, file)));
    }

    private IntStream range(int maxRange) {
        return IntStream.range(BASE_POINT, maxRange);
    }

}
