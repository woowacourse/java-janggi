package domain.janggiPiece;

import domain.direction.Direction;
import domain.direction.JanggiDirections;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public static Map<JanggiPosition, JanggiChessPiece> initPieces() {
        return Map.of(
                JanggiPositionFactory.of(0, 1), new Horse(JanggiTeam.RED),
                JanggiPositionFactory.of(0, 7), new Horse(JanggiTeam.RED),
                JanggiPositionFactory.of(9, 1), new Horse(JanggiTeam.BLUE),
                JanggiPositionFactory.of(9, 7), new Horse(JanggiTeam.BLUE)
        );
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
    public JanggiPieceType getChessPieceType() {
        return JanggiPieceType.HORSE;
    }
}
