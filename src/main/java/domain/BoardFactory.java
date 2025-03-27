package domain;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.position.Point;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class BoardFactory {
    private BoardFactory() {
    }

    public static Board create() {
        final List<Position> positions = new ArrayList<>();
        positions.addAll(generate(General::new, InitialPoint.GENERAL.greenPoints, InitialPoint.GENERAL.redPoints));
        positions.addAll(generate(Guard::new, InitialPoint.GUARD.greenPoints, InitialPoint.GUARD.redPoints));
        positions.addAll(generate(Horse::new, InitialPoint.HORSE.greenPoints, InitialPoint.HORSE.redPoints));
        positions.addAll(generate(Elephant::new, InitialPoint.ELEPHANT.greenPoints, InitialPoint.ELEPHANT.redPoints));
        positions.addAll(generate(Chariot::new, InitialPoint.CHARIOT.greenPoints, InitialPoint.CHARIOT.redPoints));
        positions.addAll(generate(Cannon::new, InitialPoint.CANNON.greenPoints, InitialPoint.CANNON.redPoints));
        positions.addAll(generate(Soldier::new, InitialPoint.SOLDIER.greenPoints, InitialPoint.SOLDIER.redPoints));
        return new Board(positions);
    }

    private static <T extends Piece> List<Position> generate(
            final Function<Team, T> pieceCreator,
            final List<Point> greenPoints,
            final List<Point> redPoints
    ) {
        final List<Position> positions = new ArrayList<>();
        positions.addAll(createPositions(pieceCreator.apply(Team.GREEN), greenPoints));
        positions.addAll(createPositions(pieceCreator.apply(Team.RED), redPoints));
        return positions;
    }

    private static List<Position> createPositions(final Piece piece, final List<Point> initialPoints) {
        final List<Position> positions = new ArrayList<>();

        for (final Point initialPoint : initialPoints) {
            final Position position = Position.newInstance(initialPoint, piece);
            positions.add(position);
        }
        return positions;
    }

    private enum InitialPoint {
        GENERAL(List.of(Point.newInstance(4, 1)), List.of(Point.newInstance(4, 8))),
        SOLDIER(List.of(Point.newInstance(0, 3), Point.newInstance(2, 3), Point.newInstance(4, 3),
                Point.newInstance(6, 3), Point.newInstance(8, 3)),
                List.of(Point.newInstance(0, 6), Point.newInstance(2, 6), Point.newInstance(4, 6),
                        Point.newInstance(6, 6), Point.newInstance(8, 6))),
        GUARD(List.of(Point.newInstance(3, 0), Point.newInstance(5, 0)),
                List.of(Point.newInstance(3, 9), Point.newInstance(5, 9))),
        ELEPHANT(List.of(Point.newInstance(1, 0), Point.newInstance(6, 0)),
                List.of(Point.newInstance(1, 9), Point.newInstance(6, 9))),
        HORSE(List.of(Point.newInstance(2, 0), Point.newInstance(7, 0)),
                List.of(Point.newInstance(2, 9), Point.newInstance(7, 9))),
        CANNON(List.of(Point.newInstance(1, 2), Point.newInstance(7, 2)),
                List.of(Point.newInstance(1, 7), Point.newInstance(7, 7))),
        CHARIOT(List.of(Point.newInstance(0, 0), Point.newInstance(8, 0)),
                List.of(Point.newInstance(0, 9), Point.newInstance(8, 9)));

        private final List<Point> greenPoints;
        private final List<Point> redPoints;

        InitialPoint(final List<Point> greenPoints, final List<Point> redPoints) {
            this.greenPoints = greenPoints;
            this.redPoints = redPoints;
        }
    }
}
