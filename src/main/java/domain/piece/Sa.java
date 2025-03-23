package domain.piece;

import domain.Coordinate;
import domain.Team;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sa extends Piece {

    public Sa(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
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
    protected List<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        return List.of();
    }
}
