package domain;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.Soldier;
import domain.position.Point;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public final class BoardFactory {
    private BoardFactory() {
    }

    public static Board create() {
        final List<Position> positions = new ArrayList<>();

        positions.addAll(generatePositions(
                General::new,
                Score.GENERAL,
                InitialPoint.GENERAL.greenPoints,
                InitialPoint.GENERAL.redPoints
        ));
        positions.addAll(generatePositions(
                Guard::new,
                Score.GUARD,
                InitialPoint.GUARD.greenPoints,
                InitialPoint.GUARD.redPoints
        ));
        positions.addAll(generatePositions(
                Horse::new, Score.HORSE,
                InitialPoint.HORSE.greenPoints,
                InitialPoint.HORSE.redPoints
        ));
        positions.addAll(generatePositions(
                Elephant::new,
                Score.ELEPHANT,
                InitialPoint.ELEPHANT.greenPoints,
                InitialPoint.ELEPHANT.redPoints
        ));
        positions.addAll(generatePositions(
                Chariot::new,
                Score.CHARIOT,
                InitialPoint.CHARIOT.greenPoints,
                InitialPoint.CHARIOT.redPoints
        ));
        positions.addAll(generatePositions(
                Cannon::new,
                Score.CANNON,
                InitialPoint.CANNON.greenPoints,
                InitialPoint.CANNON.redPoints
        ));
        positions.addAll(generatePositions(
                Soldier::new,
                Score.SOLDIER,
                InitialPoint.SOLDIER.greenPoints,
                InitialPoint.SOLDIER.redPoints
        ));

        return new Board(positions);
    }

    private static <T extends Piece> List<Position> generatePositions(
            final BiFunction<Team, Score, T> creator,
            final Score score,
            final List<Point> greenPoints,
            final List<Point> redPoints
    ) {
        final List<Position> positions = new ArrayList<>();

        positions.addAll(getPositions(creator, PieceFactory::createGreenTeam, score, greenPoints));
        positions.addAll(getPositions(creator, PieceFactory::createRedTeam, score, redPoints));

        return positions;
    }

    private static <T extends Piece> List<Position> getPositions(
            final BiFunction<Team, Score, T> creator,
            final BiFunction<BiFunction<Team, Score, T>, Score, T> creator2,
            final Score score,
            final List<Point> startPoints
    ) {
        final List<Position> positions = new ArrayList<>();

        for (final Point startPoint : startPoints) {
            final T piece = creator2.apply(creator, score);
            final Position position = new Position(startPoint, piece);
            positions.add(position);
        }
        return positions;
    }

    private enum InitialPoint {
        GENERAL(List.of(Point.of(4, 1)), List.of(Point.of(4, 8))),
        SOLDIER(List.of(Point.of(0, 3), Point.of(2, 3), Point.of(4, 3), Point.of(6, 3), Point.of(8, 3)),
                List.of(Point.of(0, 6), Point.of(2, 6), Point.of(4, 6), Point.of(6, 6), Point.of(8, 6))),
        GUARD(List.of(Point.of(3, 0), Point.of(5, 0)), List.of(Point.of(3, 9), Point.of(5, 9))),
        ELEPHANT(List.of(Point.of(1, 0), Point.of(6, 0)), List.of(Point.of(1, 9), Point.of(6, 9))),
        HORSE(List.of(Point.of(2, 0), Point.of(7, 0)), List.of(Point.of(2, 9), Point.of(7, 9))),
        CANNON(List.of(Point.of(1, 2), Point.of(7, 2)), List.of(Point.of(1, 7), Point.of(7, 7))),
        CHARIOT(List.of(Point.of(0, 0), Point.of(8, 0)), List.of(Point.of(0, 9), Point.of(8, 9)));

        private final List<Point> greenPoints;
        private final List<Point> redPoints;

        InitialPoint(final List<Point> greenPoints, final List<Point> redPoints) {
            this.greenPoints = greenPoints;
            this.redPoints = redPoints;
        }
    }
}
