package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {

    private final List<Position> positions;

    public Board(final List<Position> positions) {
        this.positions = positions;
    }

    public static Board initialize() {

        List<Position> positions = new ArrayList<>();

        positions.addAll(generatePositions(General::new, 4, 1, 4, 8));
        positions.addAll(generatePositions(Guard::new, 3, 0, 5, 0, 3, 9, 5, 9));
        positions.addAll(generatePositions(Horse::new, 2, 0, 7, 0, 2, 9, 7, 9));
        positions.addAll(generatePositions(Elephant::new, 1, 0, 6, 0, 1, 9, 6, 9));
        positions.addAll(generatePositions(Chariot::new, 0, 0, 8, 0, 0, 9, 8, 9));
        positions.addAll(generatePositions(Cannon::new, 1, 2, 7, 2, 1, 7, 7, 7));
        positions.addAll(generatePositions(Soldier::new, 0, 3, 2, 3, 4, 3, 6, 3, 8, 3, 0, 6, 2, 6, 4, 6, 6, 6, 8, 6));

        return new Board(positions);
    }

    private static <T extends Piece> List<Position> generatePositions(final Function<Team, T> creator, int... args) {
        final List<Position> positions1 = new ArrayList<>();

        final List<Integer> startPoints = Stream.of(args)
                .flatMapToInt(IntStream::of)
                .boxed()
                .toList();

        final List<Integer> greenStartPoints = startPoints.subList(0, args.length / 2);
        final List<Integer> redStartPoints = startPoints.subList(args.length / 2, args.length);

        positions1.addAll(getPositions(creator, PieceFactory::createGreenTeam, greenStartPoints));
        positions1.addAll(getPositions(creator, PieceFactory::createRedTeam, redStartPoints));

        return positions1;
    }

    private static <T extends Piece> List<Position> getPositions(
            final Function<Team, T> creator,
            final Function<Function<Team, T>, T> creator2,
            final List<Integer> startPoints
    ) {
        final List<Position> positions = new ArrayList<>();

        int length = startPoints.size();

        for (int i = 0; i < length; i += 2) {

            final T piece = creator2.apply(creator);
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

    public boolean hasPieceOnPath(final Position fromPosition, final Point toPoint) {

        // 1. 목적지까지 갈 수 있는 경로 전부 확인
        //  1- 1 List<Point> 갈 수 있는 경로 (가고자 하는 사분면에 있는 point만)
        List<Point> pointOnPath = fromPosition.test(toPoint);

        final long matchCount = positions.stream()
                .filter(position -> pointOnPath.stream().anyMatch(position::isSame))
                .count();

        if (matchCount == 0) {
            return true;
        }
        // 1이상 && 포 && 두 번째 기물이 적팀 기물 && 첫 번째 기물이 포가 아니고 && 두 번째 기물도 포가 아니다
        return false;
    }
}
