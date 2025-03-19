package domain.piece;

import domain.Coordinate;
import domain.Team;
import java.util.HashSet;
import java.util.Set;

public class Ma extends Piece {

    public Ma(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return Set.of(
                departure.change(-2, -1),
                departure.change(-2, 1),
                departure.change(-1, -2),
                departure.change(-1, 2),
                departure.change(1, -2),
                departure.change(1, 2),
                departure.change(2, -1),
                departure.change(2, 1)
        );
    }

    @Override
    public Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int fromX = departure.getX();
        int toX = arrival.getX();
        int fromY = departure.getY();
        int toY = arrival.getY();

        Set<Coordinate> path = new HashSet<>();
        if (Math.abs(toX - fromX) == 2) {
            if (toX - fromX > 0) {
                path.add(departure.change(1, 0));
                return path;
            }
            path.add(departure.change(-1, 0));
            return path;
        }
        if (Math.abs(toY - fromY) == 2) {
            if (toY - fromY > 0) {
                path.add(departure.change(0, 1));
                return path;
            }
            path.add(departure.change(0, -1));
            return path;
        }
        throw new IllegalArgumentException("여기까지 못옴");
    }
}
