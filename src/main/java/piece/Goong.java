package piece;

import board.Board;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import team.Team;

public class Goong extends Piece {

    public Goong(Team team) {
        super(team);
    }

    @Override
    protected Set<coordinate.Coordinate> findMovableCandidates(coordinate.Coordinate departure) {
        return Stream.of(
                        departure.pickChangedCoordinate(1, 0),
                        departure.pickChangedCoordinate(-1, 0),
                        departure.pickChangedCoordinate(0, 1),
                        departure.pickChangedCoordinate(0, -1)
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    @Override
    protected boolean canMoveConsideringObstacles(Board board, coordinate.Coordinate departure,
                                                  coordinate.Coordinate arrival) {
        return true;
    }

    @Override
    protected Set<coordinate.Coordinate> findPaths(coordinate.Coordinate departure, coordinate.Coordinate arrival) {
        return Set.of();
    }
}
