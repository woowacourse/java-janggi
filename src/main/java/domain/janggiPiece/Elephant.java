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

public class Elephant extends JumpJanggiPiece {
    private static final List<JanggiDirections> directions = List.of(
            new JanggiDirections(List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT_UP)),
            new JanggiDirections(List.of(Direction.UP, Direction.LEFT_UP, Direction.LEFT_UP)),
            new JanggiDirections(List.of(Direction.LEFT, Direction.LEFT_UP, Direction.LEFT_UP)),
            new JanggiDirections(List.of(Direction.LEFT, Direction.LEFT_DOWN, Direction.LEFT_DOWN)),
            new JanggiDirections(List.of(Direction.RIGHT, Direction.RIGHT_UP, Direction.RIGHT_UP)),
            new JanggiDirections(List.of(Direction.RIGHT, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN)),
            new JanggiDirections(List.of(Direction.DOWN, Direction.LEFT_DOWN, Direction.LEFT_DOWN)),
            new JanggiDirections(List.of(Direction.DOWN, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN))
    );
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public Elephant(final JanggiTeam team) {
        super(team, directions);
    }

    public static Map<JanggiPosition, JanggiPiece> initPieces() {
        return Map.of(
                JanggiPosition.of(0, 2), new Elephant(JanggiTeam.RED),
                JanggiPosition.of(0, 6), new Elephant(JanggiTeam.RED),
                JanggiPosition.of(9, 2), new Elephant(JanggiTeam.BLUE),
                JanggiPosition.of(9, 6), new Elephant(JanggiTeam.BLUE)
        );
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public JanggiPieceType getChessPieceType() {
        return JanggiPieceType.ELEPHANT;
    }
}
