package domain.piece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class UnlimitedStraightMovingPiece extends Piece {

    public UnlimitedStraightMovingPiece(final Team team, final Set<Movement> movements) {
        super(team, movements);
    }

    @Override
    protected final Set<Coordinate> findMovableCandidates(Coordinate departure) {
        Set<Coordinate> candidates = new HashSet<>();

        for (final var movement : movementsAt(departure)) {
            var current = departure;

            while (current.canMove(movement)) {
                var next = current.move(movement);
                candidates.add(next);
                current = next;
            }
        }
        return candidates;
    }

    @Override
    protected List<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int dx = arrival.getX() - departure.getX();
        int dy = arrival.getY() - departure.getY();

        List<Coordinate> coordinates = new ArrayList<>();
        if (dx == 0 && dy > 0) { // 아래
            for (int y = departure.getY() + 1; y < arrival.getY(); y++) {
                coordinates.add(new Coordinate(departure.getX(), y));
            }
            return coordinates;
        }
        if (dx == 0 && dy < 0) { // 위
            for (int y = departure.getY() - 1; y > arrival.getY(); y--) {
                coordinates.add(new Coordinate(departure.getX(), y));
            }
            return coordinates;
        }
        if (dx > 0 && dy == 0) { // 오른쪽
            for (int x = departure.getX() + 1; x < arrival.getX(); x++) {
                coordinates.add(new Coordinate(x, departure.getY()));
            }
            return coordinates;
        }
        if (dx < 0 && dy == 0) { // 왼쪽
            for (int x = departure.getX() - 1; x > arrival.getX(); x--) {
                coordinates.add(new Coordinate(x, departure.getY()));
            }
            return coordinates;
        }
        throw new IllegalStateException("유효하지 않은 좌표입니다.");
    }
}
