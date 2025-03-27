package piece;

import board.Board;
import coordinate.Coordinate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import team.Team;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return Stream.concat(
                        departure.moveByCross().stream(),
                        departure.moveByDiagonalInCastle().stream()
                )
                .collect(Collectors.toSet());
    }

    @Override
    protected boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival) {
        return findPaths(departure, arrival)
                .stream()
                .noneMatch(board::hasPiece);
    }

    @Override
    protected Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int xDirection = Integer.compare(arrival.getX(), departure.getX());
        int yDirection = Integer.compare(arrival.getY(), departure.getY());

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
