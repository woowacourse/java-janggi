package domain.piece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class UnlimitedCrossMovementPiece extends UnlimitedMovementPiece {

    public UnlimitedCrossMovementPiece(final Team team, final Set<Movement> movements) {
        super(team, movements);
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
