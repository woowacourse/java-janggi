package domain.janggiPiece;

import domain.direction.Direction;
import domain.direction.JanggiDirections;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;

public class Horse extends JanggiChessPiece {
    private static final List<JanggiDirections> directions = List.of(
            new JanggiDirections(List.of(Direction.UP, Direction.RIGHT_UP)),
            new JanggiDirections(List.of(Direction.UP, Direction.LEFT_UP)),
            new JanggiDirections(List.of(Direction.LEFT, Direction.LEFT_UP)),
            new JanggiDirections(List.of(Direction.LEFT, Direction.LEFT_DOWN)),
            new JanggiDirections(List.of(Direction.RIGHT, Direction.RIGHT_UP)),
            new JanggiDirections(List.of(Direction.RIGHT, Direction.RIGHT_DOWN)),
            new JanggiDirections(List.of(Direction.DOWN, Direction.LEFT_DOWN)),
            new JanggiDirections(List.of(Direction.DOWN, Direction.RIGHT_DOWN))
    );
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public Horse(final JanggiTeam team) {
        super(team);
    }

    @Override
    public final List<Path> getCoordinatePaths(JanggiPosition startPosition) {
        List<Path> result = new ArrayList<>();
        for (JanggiDirections direction : directions) {
            if (direction.canApplyFrom(startPosition)) {
                result.add(direction.getPathFrom(startPosition));
            }
        }
        return result;
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public Piece getChessPieceType() {
        return Piece.HORSE;
    }
}
