package domain.piece;

import static domain.direction.Direction.EAST;
import static domain.direction.Direction.NORTH;
import static domain.direction.Direction.SOUTH;
import static domain.direction.Direction.WEST;

import domain.direction.Direction;
import domain.pathgenerator.ListPathGenerator;
import domain.player.Team;
import domain.strategy.BlockedMovementStrategy;
import java.util.List;

public class Jol extends Piece {

    private static final List<List<Direction>> choPaths = List.of(
            List.of(NORTH),
            List.of(EAST),
            List.of(WEST));

    private static final List<List<Direction>> hanPaths = List.of(
            List.of(SOUTH),
            List.of(EAST),
            List.of(WEST));

    public Jol(Team team) {
        super(team, PieceType.JOL, new BlockedMovementStrategy(),
                new ListPathGenerator(getPaths(team)));
    }

    private static List<List<Direction>> getPaths(Team team) {
        if (team.isCho()) {
            return choPaths;
        }
        return hanPaths;
    }

}
