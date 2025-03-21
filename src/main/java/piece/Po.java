package piece;

import board.Board;
import coordinate.Coordinate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import team.Team;

public class Po extends Piece {

    public Po(Team team) {
        super(team);
    }

    @Override
    protected Set<coordinate.Coordinate> findMovableCandidates(Coordinate departure) {
        return departure.pickCrossCoordinates();
    }

    @Override
    protected boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival) {
        List<Coordinate> paths = findPaths(departure, arrival).stream()
                .filter(board::isExistence)
                .toList();

        if (paths.size() != 1) {
            return false;
        }
        if (board.findPiece(paths.getFirst()).get().isPo()) {
            return false;
        }
        if (board.isExistence(arrival) && board.findPiece(arrival).get().isPo()) {
            return false;
        }
        return true;
    }

    @Override
    protected Set<coordinate.Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int dx = arrival.getX() - departure.getX();
        int dy = arrival.getY() - departure.getY();

        Set<coordinate.Coordinate> coordinates = new HashSet<>();
        if (dx == 0 && dy > 0) { // 아래
            for (int y = departure.getY() + 1; y < arrival.getY(); y++) {
                coordinates.add(new coordinate.Coordinate(departure.getX(), y));
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
                coordinates.add(new coordinate.Coordinate(x, departure.getY()));
            }
            return coordinates;
        }
        if (dx < 0 && dy == 0) { // 왼쪽
            for (int x = departure.getX() - 1; x > arrival.getX(); x--) {
                coordinates.add(new coordinate.Coordinate(x, departure.getY()));
            }
            return coordinates;
        }
        throw new IllegalStateException("유효하지 않은 좌표입니다.");
    }
}
