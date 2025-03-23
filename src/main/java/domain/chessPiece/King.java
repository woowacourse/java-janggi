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

public class King extends LimitedMoveChessPiece {

    private static final List<Directions> directions = List.of(
            new Directions(List.of(UP, RIGHT_UP)),
            new Directions(List.of(UP, LEFT_UP)),
            new Directions(List.of(LEFT, LEFT_UP)),
            new Directions(List.of(LEFT, LEFT_DOWN)),
            new Directions(List.of(RIGHT, RIGHT_UP)),
            new Directions(List.of(RIGHT, RIGHT_DOWN)),
            new Directions(List.of(DOWN, LEFT_DOWN)),
            new Directions(List.of(DOWN, RIGHT_DOWN))
    );
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public King(final ChessTeam team) {
        super(team, directions);
    }

    public static Map<ChessPosition, ChessPiece> initPieces() {
        return Map.of(
                new ChessPosition(1, 4), new King(ChessTeam.RED),
                new ChessPosition(8, 4), new King(ChessTeam.BLUE)
        );
    }

    @Override
    protected HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.KING;
    }
}
