package domain.board;

import domain.board.dto.Moved;
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

    public Moved tryToMove(Point start, Point end, Team currentTeam) {
        Intersection origin = findOriginIntersection(start, currentTeam);
        Intersection destination = findIntersection(end);
        inspectPath(origin, destination);
        origin.move(destination);
        return new Moved(origin, destination);
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
        return isGeneralDead() || hasNotEnoughPieceScore();
    }

    public Team getWinner() {
        if (isGeneralDead(Team.HAN)) {
            return Team.CHO;
        }
        return Team.HAN;
    }

    public boolean isGeneralDead() {
        return Arrays.stream(Team.values())
                .filter(team -> team != Team.NONE)
                .anyMatch(this::isGeneralDead);
    }

    public boolean isGeneralDead(Team team) {
        return intersections.values().stream()
                .filter(Intersection::hasPiece)
                .filter(intersection -> intersection.isSameTeam(team))
                .noneMatch(Intersection::hasGeneral);
    }

    public boolean hasNotEnoughPieceScore() {
        return calculateTeamScore(Team.HAN) < 30 && calculateTeamScore(Team.CHO) < 30;
    }

    public int calculateTeamScore(Team team) {
        return intersections.values().stream()
                .filter(intersection -> intersection.isSameTeam(team))
                .filter(Intersection::hasPiece)
                .map(Intersection::getScore)
                .reduce(0, Integer::sum);
    }

    public Map<Point, Intersection> getJanggiBoard() {
        return Collections.unmodifiableMap(intersections);
    }

    private static Map<Point, Intersection> createIntersections(IntersectionGenerator intersectionGenerator) {
        Map<Point, Intersection> intersections = fillEmptyIntersections();
        for (Intersection intersection : intersectionGenerator.makeIntersection()) {
            intersections.put(intersection.getPoint(), intersection);
        }
        return intersections;
    }

    private static Map<Point, Intersection> fillEmptyIntersections() {
        return getAllPoints()
                .collect(Collectors.toMap(point -> point, NormalIntersection::empty));
    }

    private static Stream<Point> getAllPoints() {
        return range(MAX_ROW).boxed()
                .flatMap(row -> range(MAX_FILE).mapToObj(file -> new Point(row, file)));
    }

    private static IntStream range(int maxRange) {
        return IntStream.range(BASE_POINT, maxRange);
    }

    public List<Intersection> getListIntersection() {
        return intersections.values()
                .stream()
                .toList();
    }

}
