package domain.janggiPiece;

import domain.direction.Direction;
import domain.direction.JanggiDirections;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.JanggiPosition;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.util.List;
import java.util.Map;

public class Horse extends JumpJanggiPiece {
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
        super(team, directions);
    }

    public static Map<JanggiPosition, JanggiPiece> initPieces() {
        return Map.of(
                JanggiPosition.of(0, 1), new Horse(JanggiTeam.RED),
                JanggiPosition.of(0, 7), new Horse(JanggiTeam.RED),
                JanggiPosition.of(9, 1), new Horse(JanggiTeam.BLUE),
                JanggiPosition.of(9, 7), new Horse(JanggiTeam.BLUE)
        );
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
