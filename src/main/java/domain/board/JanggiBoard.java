package domain.board;

import domain.intersection.Intersection;
import domain.move.CannonMoveRule;
import domain.move.ChariotMoveRule;
import domain.move.ElephantMoveRule;
import domain.move.GeneralMoveRule;
import domain.move.GuardMoveRule;
import domain.move.HorseMoveRule;
import domain.move.MoveRule;
import domain.move.SoldierMoveRule;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JanggiBoard {
    private static final int MAX_ROW = 10;
    private static final int MAX_FILE = 9;

    private final Map<Point, Intersection> intersections;
    private final List<MoveRule> moveRules;

    public JanggiBoard(IntersectionGenerator intersectionGenerator) {
        this.intersections = fillEmptyIntersections();
        this.moveRules = setMoveRules();
        for (Intersection intersection : intersectionGenerator.makeIntersections()) {
            intersections.put(intersection.getPoint(), intersection);
        }
    }

    private Map<Point, Intersection> fillEmptyIntersections() {
        return getAllPoints()
                .collect(Collectors.toMap(point -> point, Intersection::empty));
    }

    private Stream<Point> getAllPoints() {
        return range(MAX_ROW).boxed()
                .flatMap(row -> range(MAX_FILE).mapToObj(f -> new Point(row, f)));
    }

    private IntStream range(int maxRange) {
        return IntStream.range(0, maxRange);
    }

    private List<MoveRule> setMoveRules() {
        return List.of(
                new ChariotMoveRule(),
                new GeneralMoveRule(),
                new GuardMoveRule(),
                new ElephantMoveRule(),
                new SoldierMoveRule(),
                new CannonMoveRule(),
                new HorseMoveRule()
        );
    }

    public void tryToMove(Point start, Point end) {
        Intersection from = findIntersection(start);
        Intersection to = findIntersection(end);

        validateMoveRule(from, to);
        move(to, from);
    }

    public Intersection findIntersection(Point point) {
        return intersections.get(point);
    }

    private void validateMoveRule(Intersection from, Intersection to) {
        MoveRule moveRule = findMoveRule(from);
        List<Point> possiblePoints = moveRule.findPossiblePoints(from, to);
        List<Intersection> path = findPath(possiblePoints);
        moveRule.validateMoveRule(from, path);
    }

    private void move(Intersection to, Intersection from) {
        to.arrive(from);
        from.leave();
    }

    public MoveRule findMoveRule(Intersection from) {
        return moveRules.stream()
                .filter(moveRule -> moveRule.support(from))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("선택한 좌표에 이동 가능한 기물이 없습니다."));
    }

    public BoardState boardState() {
        List<IntersectionState> intersectionStates = new ArrayList<>();
        for (Intersection intersection : intersections.values()) {
            intersectionStates.add(intersection.toIntersectionState());
        }
        return new BoardState(intersectionStates);
    }

    public boolean isSameTeamAt(Point point, Team turn) {
        Intersection intersection = intersections.get(point);
        return intersection.isSameTeam(turn);
    }

    public boolean isGameRunning() {
        int generalCount = (int) intersections.values().stream()
                .filter(intersection -> intersection.isSamePiece(PieceType.GENERAL))
                .count();
        return generalCount == 2;
    }

    private List<Intersection> findPath(List<Point> possiblePoints) {
        return possiblePoints.stream()
                .map(this::findIntersection)
                .toList();
    }
}
