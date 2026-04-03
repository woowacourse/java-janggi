package domain.board;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.piece.move.CannonMoveRule;
import domain.piece.move.ChariotMoveRule;
import domain.piece.move.ElephantMoveRule;
import domain.piece.move.GeneralMoveRule;
import domain.piece.move.GuardMoveRule;
import domain.piece.move.HorseMoveRule;
import domain.piece.move.MoveRule;
import domain.piece.move.SoliderMoveRule;
import domain.point.Point;
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
        for (Intersection intersection : intersectionGenerator.makeIntersection()) {
            intersections.put(intersection.getPoint(), intersection);
        }
    }

    private static Stream<Point> getAllPoints() {
        return range(MAX_ROW).boxed()
                .flatMap(row -> range(MAX_FILE).mapToObj(f -> new Point(row, f)));
    }

    private static IntStream range(int maxRange) {
        return IntStream.range(0, maxRange);
    }

    public void tryToMove(Point start, Point end) {
        Intersection from = findIntersection(start);
        Intersection to = findIntersection(end);

        validateMoveRule(from, to);
        move(to, from);
    }

    public boolean isGameRunning() {
        int generalCount = 0;
        for (Intersection intersection : intersections.values()) {
            if (intersection.isSamePiece(PieceType.GENERAL)) {
                generalCount++;
            }
        }
        if (generalCount == 2) {
            return true;
        }
        return false;
    }

    private void move(Intersection to, Intersection from) {
        to.arrive(from);
        from.leave();
    }

    private void validateMoveRule(Intersection from, Intersection to) {
        MoveRule moveRule = findMoveRule(from);
        List<Point> possiblePoints = moveRule.findPossiblePoints(from, to);
        List<Intersection> path = findPath(possiblePoints);
        moveRule.checkMoveRule(from, path);
    }

    public MoveRule findMoveRule(Intersection from) {
        return moveRules.stream()
                .filter(moveRule -> moveRule.support(from))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("선택한 좌표에 이동 가능한 기물이 없습니다."));
    }

    private List<Intersection> findPath(List<Point> possiblePoints) {
        return possiblePoints.stream()
                .map(this::findIntersection)
                .toList();
    }

    public BoardState boardStatus() {
        final List<IntersectionState> intersectionStates = new ArrayList<>();
        for (Intersection intersection : intersections.values()) {
            intersectionStates.add(intersection.toIntersectionState());
        }
        return new BoardState(intersectionStates);
    }

    public Intersection findIntersection(Point point) {
        return intersections.get(point);
    }

    private List<MoveRule> setMoveRules() {
        return List.of(
                new ChariotMoveRule(),
                new GeneralMoveRule(),
                new GuardMoveRule(),
                new ElephantMoveRule(),
                new SoliderMoveRule(),
                new CannonMoveRule(),
                new HorseMoveRule()
        );
    }

    private Map<Point, Intersection> fillEmptyIntersections() {
        return getAllPoints()
                .collect(Collectors.toMap(point -> point, Intersection::empty));
    }


}
