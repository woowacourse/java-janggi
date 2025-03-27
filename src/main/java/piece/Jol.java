package piece;

import static coordinate.Direction.LEFT;
import static coordinate.Direction.RIGHT;
import static coordinate.Direction.UP;

import board.Board;
import coordinate.Coordinate;
import coordinate.MoveVector;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import team.Team;

public class Jol extends Piece {

    private static final List<MoveVector> MOVABLE_VECTORS = List.of(
            UP,
            RIGHT,
            LEFT
    );

    public Jol() {
        super(Team.CHO);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return Stream.concat(
                        MOVABLE_VECTORS.stream().map(departure::moveBy),
                        departure.moveByDiagonalOneInCastle().stream()
                )
                .filter(Objects::nonNull)
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

    @Override
    public String getName() {
        return "졸";
    }
}
