package piece;

import board.Board;
import coordinate.Coordinate;
import java.util.HashSet;
import java.util.Set;
import team.Team;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return departure.moveByCross();
    }

    @Override
    protected boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival) {
        return findPaths(departure, arrival)
                .stream()
                .noneMatch(board::hasPiece);
    }

    @Override
    protected Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int xDirection = Integer.compare(arrival.getX(), departure.getX()); // -1, 0, 1
        int yDirection = Integer.compare(arrival.getY(), departure.getY()); // -1, 0, 1

        if (xDirection != 0 && yDirection != 0) {
            throw new IllegalStateException("차는 직선으로만 이동할 수 있습니다.");
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
        return "차";
    }
}
