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
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return departure.moveByCross();
    }

    @Override
    protected boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival) {
        List<Coordinate> obstacles = findPaths(departure, arrival).stream()
                .filter(board::isExistence)
                .toList();

        if (obstacles.size() != 1) {
            return false;
        }
        if (isPo(board.findPiece(obstacles.get(0)).get())) {
            return false;
        }
        if (board.isExistence(arrival) && isPo(board.findPiece(arrival).get())) {
            return false;
        }
        return true;
    }

    @Override
    protected Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int xDirection = Integer.compare(arrival.getX(), departure.getX()); // -1, 0, 1
        int yDirection = Integer.compare(arrival.getY(), departure.getY()); // -1, 0, 1

        if (xDirection != 0 && yDirection != 0) {
            throw new IllegalStateException("포는 직선으로만 이동할 수 있습니다.");
        }

        Set<Coordinate> coordinates = new HashSet<>();
        int x = departure.getX() + xDirection;
        int y = departure.getY() + yDirection;

        while (x != arrival.getX() || y != arrival.getY()) {
            coordinates.add(new Coordinate(x, y));
            x += xDirection;
            y += yDirection;
        }

        return coordinates;
    }

    @Override
    public String getName() {
        return "포";
    }

    private boolean isPo(Piece piece) {
        return piece.getClass() == this.getClass();
    }
}
