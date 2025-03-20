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

public class Board {

    private final List<Position> positions;

    public Board(final List<Position> positions) {
        this.positions = positions;
    }

    public static Board initialize() {

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
        final List<Position> positions1 = new ArrayList<>();

        final List<Integer> startPoints = Stream.of(args)
                .flatMapToInt(IntStream::of)
                .boxed()
                .toList();

        final List<Integer> greenStartPoints = startPoints.subList(0, args.length / 2);
        final List<Integer> redStartPoints = startPoints.subList(args.length / 2, args.length);

        positions1.addAll(getPositions(creator, PieceFactory::createGreenTeam, score, greenStartPoints));
        positions1.addAll(getPositions(creator, PieceFactory::createRedTeam, score, redStartPoints));

        return positions1;
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

    public int countPieces() {
        return positions.size();
    }

    public Position findPositionBy(final Point point) {
        return positions.stream()
                .filter(p -> p.isSame(point))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 말이 없습니다."));
    }

    public boolean canMoveOnPath(final Position fromPosition, final Point toPoint) {

        final List<Point> pointOnPath = fromPosition.test(toPoint);

        final long matchCount = positions.stream()
                .filter(position -> pointOnPath.stream().anyMatch(position::isSame))
                .count();

        if (matchCount == 0) {
            return true;
        }
        final Cannon cannon = PieceFactory.createCannon();
        if (matchCount == 1 && fromPosition.isSamePiece(cannon)) {
            final Position middlePosition = positions.stream()
                    .filter(position -> pointOnPath.stream().anyMatch(position::isSame))
                    .findFirst()
                    .orElseThrow();
            if (middlePosition.isSamePiece(cannon)) {
                return false;
            }

            if (hasPieceAt(toPoint) && findPositionBy(toPoint).isSamePiece(cannon)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean hasPieceAt(final Point point) {
        return positions.stream()
                .anyMatch(position -> position.isSame(point));
    }

    public void moveForEnd(final Position prevPosition, final Point newPoint, final Runnable runner) {
        if (!hasPieceAt(newPoint)) {
            positions.remove(prevPosition);
            positions.add(prevPosition.getNextPosition(newPoint));
            return;
        }

        if (isAnotherTeam(prevPosition, newPoint)) {
            captureOtherTeamPiece(newPoint, prevPosition, runner);
            return;
        }
        throw new IllegalArgumentException("해당 위치에 같은 팀 말이 있습니다.");
    }

    private boolean isAnotherTeam(final Position prevPosition, final Point newPoint) {
        return prevPosition.isGreenTeam() != findPositionBy(newPoint).isGreenTeam();
    }

    private void captureOtherTeamPiece(final Point newPoint, final Position prevPosition, final Runnable runner) {
        positions.remove(findPositionBy(newPoint));
        positions.remove(prevPosition);
        positions.add(prevPosition.getNextPosition(newPoint));
        runner.run();
    }

    public boolean hasOnlyOneGeneral() {
        final General general = PieceFactory.createGeneral();
        final long count = positions.stream()
                .filter(position -> position.isSamePiece(general))
                .count();

        return count == 1;
    }

    public Team determineWinTeam() {
        if (!hasOnlyOneGeneral()) {
            throw new IllegalStateException("궁이 1개일 때만 호출이 가능합니다.");
        }

        final General general = PieceFactory.createGeneral();
        final Position position = positions.stream()
                .filter(p -> p.isSamePiece(general))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("궁이 존재하지 않습니다."));

        if (position.isGreenTeam()) {
            return Team.GREEN;
        }
        return Team.RED;
    }
}
