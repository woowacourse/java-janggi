package domain.chessPiece;

import domain.direction.Direction;
import domain.direction.Directions;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;

import java.util.List;
import java.util.Map;

public class King extends LimitedMoveChessPiece {

    private static final List<Directions> directions = List.of(
            new Directions(List.of(Direction.UP, Direction.RIGHT_UP)),
            new Directions(List.of(Direction.UP, Direction.LEFT_UP)),
            new Directions(List.of(Direction.LEFT, Direction.LEFT_UP)),
            new Directions(List.of(Direction.LEFT, Direction.LEFT_DOWN)),
            new Directions(List.of(Direction.RIGHT, Direction.RIGHT_UP)),
            new Directions(List.of(Direction.RIGHT, Direction.RIGHT_DOWN)),
            new Directions(List.of(Direction.DOWN, Direction.LEFT_DOWN)),
            new Directions(List.of(Direction.DOWN, Direction.RIGHT_DOWN))
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
