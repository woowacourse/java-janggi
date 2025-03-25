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
                createGreenPoints(Point.of(4, 1)),
                createRedPoints(Point.of(4, 8))
        ));
        positions.addAll(generatePositions(
                Guard::new,
                Score.GUARD,
                createGreenPoints(Point.of(3, 0), Point.of(5, 0)),
                createRedPoints(Point.of(3, 9), Point.of(5, 9))
        ));
        positions.addAll(generatePositions(
                Horse::new, Score.HORSE,
                createGreenPoints(Point.of(2, 0), Point.of(7, 0)),
                createRedPoints(Point.of(2, 9), Point.of(7, 9))
        ));
        positions.addAll(generatePositions(
                Elephant::new,
                Score.ELEPHANT,
                createGreenPoints(Point.of(1, 0), Point.of(6, 0)),
                createRedPoints(Point.of(1, 9), Point.of(6, 9))
        ));
        positions.addAll(generatePositions(
                Chariot::new,
                Score.CHARIOT,
                createGreenPoints(Point.of(0, 0), Point.of(8, 0)),
                createRedPoints(Point.of(0, 9), Point.of(8, 9))
        ));
        positions.addAll(generatePositions(
                Cannon::new,
                Score.CANNON,
                createGreenPoints(Point.of(1, 2), Point.of(7, 2)),
                createRedPoints(Point.of(1, 7), Point.of(7, 7))
        ));
        positions.addAll(generatePositions(
                Soldier::new,
                Score.SOLDIER,
                createGreenPoints(
                        Point.of(0, 3),
                        Point.of(2, 3),
                        Point.of(4, 3),
                        Point.of(6, 3),
                        Point.of(8, 3)
                ),
                createRedPoints(
                        Point.of(0, 6),
                        Point.of(2, 6),
                        Point.of(4, 6),
                        Point.of(6, 6),
                        Point.of(8, 6)
                )
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

    private static List<Point> createRedPoints(final Point... args) {
        return List.of(args);
    }

    private static List<Point> createGreenPoints(final Point... args) {
        return List.of(args);
    }
}
