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

public class Guard extends LimitedMoveChessPiece {
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

    public Guard(final ChessTeam team) {
        super(team, directions);
    }

    public static Map<ChessPosition, ChessPiece> initPieces() {
        return Map.of(
                new ChessPosition(0, 3), new Guard(ChessTeam.RED),
                new ChessPosition(0, 5), new Guard(ChessTeam.RED),
                new ChessPosition(9, 3), new Guard(ChessTeam.BLUE),
                new ChessPosition(9, 5), new Guard(ChessTeam.BLUE)
        );
    }

    @Override
    protected HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.GUARD;
    }
}
