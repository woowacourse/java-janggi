package domain.piece;

import domain.Board;
import domain.Coordinate;
import domain.Team;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Goong extends Piece {

    public Goong(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        List<Optional<Coordinate>> coordinates = List.of(
                departure.pickChangedCoordinate(1, 0),
                departure.pickChangedCoordinate(-1, 0),
                departure.pickChangedCoordinate(0, 1),
                departure.pickChangedCoordinate(0, -1)
        );

        return coordinates.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    @Override
    protected boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival) {
        return true;
    }

    @Override
    protected Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        return Set.of();
    }
}
