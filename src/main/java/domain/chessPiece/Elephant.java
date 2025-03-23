package domain.chessPiece;

import domain.direction.Directions;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;

import java.util.List;
import java.util.Map;

import static domain.direction.Direction.*;

public class Elephant extends LimitedMoveChessPiece {
    private static final List<Directions> directions = List.of(
            new Directions(List.of(UP, RIGHT_UP, RIGHT_UP)),
            new Directions(List.of(UP, LEFT_UP, LEFT_UP)),
            new Directions(List.of(LEFT, LEFT_UP, LEFT_UP)),
            new Directions(List.of(LEFT, LEFT_DOWN, LEFT_DOWN)),
            new Directions(List.of(RIGHT, RIGHT_UP, RIGHT_UP)),
            new Directions(List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN)),
            new Directions(List.of(DOWN, LEFT_DOWN, LEFT_DOWN)),
            new Directions(List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN))
    );
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public Elephant(final ChessTeam team) {
        super(team, directions);
    }

    public static Map<ChessPosition, ChessPiece> initPieces() {
        return Map.of(
                new ChessPosition(0, 2), new Elephant(ChessTeam.RED),
                new ChessPosition(0, 6), new Elephant(ChessTeam.RED),
                new ChessPosition(9, 2), new Elephant(ChessTeam.BLUE),
                new ChessPosition(9, 6), new Elephant(ChessTeam.BLUE)
        );
    }

    @Override
    protected HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.ELEPHANT;
    }
}
