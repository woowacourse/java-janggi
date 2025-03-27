package domain.janggiPiece;

import domain.direction.Direction;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.JanggiPosition;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.util.List;
import java.util.Map;

public class Pawn extends StepJanggiPiece {

    private static final Map<JanggiTeam, List<Direction>> DIRECTIONS = Map.of(
            JanggiTeam.RED, List.of(
                    Direction.LEFT,
                    Direction.RIGHT,
                    Direction.DOWN,
                    Direction.LEFT_DOWN,
                    Direction.RIGHT_DOWN
            ),
            JanggiTeam.BLUE, List.of(
                    Direction.LEFT,
                    Direction.RIGHT,
                    Direction.UP,
                    Direction.LEFT_UP,
                    Direction.RIGHT_UP
            )
    );
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public Pawn(JanggiTeam janggiTeam) {
        super(janggiTeam, DIRECTIONS.get(janggiTeam));
    }

    public static Map<JanggiPosition, JanggiPiece> initPieces() {
        return Map.of(
                JanggiPosition.of(3, 0), new Pawn(JanggiTeam.RED),
                JanggiPosition.of(3, 2), new Pawn(JanggiTeam.RED),
                JanggiPosition.of(3, 4), new Pawn(JanggiTeam.RED),
                JanggiPosition.of(3, 6), new Pawn(JanggiTeam.RED),
                JanggiPosition.of(3, 8), new Pawn(JanggiTeam.RED),
                JanggiPosition.of(6, 0), new Pawn(JanggiTeam.BLUE),
                JanggiPosition.of(6, 2), new Pawn(JanggiTeam.BLUE),
                JanggiPosition.of(6, 4), new Pawn(JanggiTeam.BLUE),
                JanggiPosition.of(6, 6), new Pawn(JanggiTeam.BLUE),
                JanggiPosition.of(6, 8), new Pawn(JanggiTeam.BLUE)
        );
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public JanggiPieceType getChessPieceType() {
        return JanggiPieceType.PAWN;
    }
}
