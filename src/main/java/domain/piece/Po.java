package domain.piece;

import domain.Board;
import domain.Coordinate;
import domain.Team;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Po extends Piece {

    public Po(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        Set<Coordinate> coordinates = departure.pickCrossCoordinates();
        coordinates.remove(departure.pickChangedCoordinate(-1, 0));
        coordinates.remove(departure.pickChangedCoordinate(1, 0));
        coordinates.remove(departure.pickChangedCoordinate(0, -1));
        coordinates.remove(departure.pickChangedCoordinate(0, 1));
        return coordinates;
    }

    @Override
    protected boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival) {
        List<Coordinate> paths = findPaths(departure, arrival).stream()
                .filter(board::isExistence)
                .toList();

        if (paths.size() != 1) {
            return false;
        }
        if (board.findPiece(paths.getFirst()).isPo()) {
            return false;
        }
        if (board.isExistence(arrival) && board.findPiece(arrival).isPo()) {
            return false;
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
