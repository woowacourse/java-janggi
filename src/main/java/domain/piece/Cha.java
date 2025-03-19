package domain.piece;

import domain.Board;
import domain.Coordinate;
import domain.Team;
import java.util.HashSet;
import java.util.Set;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return departure.pickCrossCoordinates();
    }

    @Override
    protected boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival) {
        for (Coordinate coordinate : findPaths(departure, arrival)) {
            if (board.isExistence(coordinate)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int dx = arrival.getX() - departure.getX();
        int dy = arrival.getY() - departure.getY();

        Set<Coordinate> coordinates = new HashSet<>();
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
        throw new IllegalArgumentException("여기까지 못옴");
    }
}
