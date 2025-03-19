package domain.piece;

import domain.Board;
import domain.Coordinate;
import domain.Team;
import java.util.Set;

public class Jol extends Piece {

    public Jol(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return Set.of(
                departure.pickChangedCoordinate(1, 0),
                departure.pickChangedCoordinate(-1, 0),
                departure.pickChangedCoordinate(0, team.getDirection())
        );
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
