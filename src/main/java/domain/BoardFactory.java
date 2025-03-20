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
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class BoardFactory {
    private BoardFactory() {
    }

    public static Board create() {
        final List<Position> positions = new ArrayList<>();

        positions.addAll(generatePositions(General::new, Score.GENERAL, 4, 1, 4, 8));
        positions.addAll(generatePositions(Guard::new, Score.GUARD, 3, 0, 5, 0, 3, 9, 5, 9));
        positions.addAll(generatePositions(Horse::new, Score.HORSE, 2, 0, 7, 0, 2, 9, 7, 9));
        positions.addAll(generatePositions(Elephant::new, Score.ELEPHANT, 1, 0, 6, 0, 1, 9, 6, 9));
        positions.addAll(generatePositions(Chariot::new, Score.CHARIOT, 0, 0, 8, 0, 0, 9, 8, 9));
        positions.addAll(generatePositions(Cannon::new, Score.CANNON, 1, 2, 7, 2, 1, 7, 7, 7));
        positions.addAll(generatePositions(
                Soldier::new, Score.SOLDIER, 0, 3, 2, 3, 4, 3, 6, 3, 8, 3, 0, 6, 2, 6, 4, 6, 6, 6, 8, 6
        ));

        return new Board(positions);
    }

    private static <T extends Piece> List<Position> generatePositions(
            final BiFunction<Team, Score, T> creator,
            final Score score,
            final int... args
    ) {
        final List<Position> positions = new ArrayList<>();

        final List<Integer> startPoints = Stream.of(args)
                .flatMapToInt(IntStream::of)
                .boxed()
                .toList();

        final List<Integer> greenStartPoints = startPoints.subList(0, args.length / 2);
        final List<Integer> redStartPoints = startPoints.subList(args.length / 2, args.length);

        positions.addAll(getPositions(creator, PieceFactory::createGreenTeam, score, greenStartPoints));
        positions.addAll(getPositions(creator, PieceFactory::createRedTeam, score, redStartPoints));

        return positions;
    }

    private static <T extends Piece> List<Position> getPositions(
            final BiFunction<Team, Score, T> creator,
            final BiFunction<BiFunction<Team, Score, T>, Score, T> creator2,
            final Score score,
            final List<Integer> startPoints
    ) {
        final List<Position> positions = new ArrayList<>();

        final int length = startPoints.size();

        for (int i = 0; i < length; i += 2) {

            final T piece = creator2.apply(creator, score);
            final Point point = Point.of(startPoints.get(i), startPoints.get(i + 1));
            final Position position = new Position(point, piece);
            positions.add(position);
        }
        return positions;
    }
}
