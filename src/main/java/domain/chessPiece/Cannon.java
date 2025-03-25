package domain.chessPiece;

import domain.direction.Direction;
import domain.hurdlePolicy.CannonHurdlePolicy;
import domain.hurdlePolicy.HurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;

import java.util.List;
import java.util.Map;

public class Cannon extends UnlimitedMoveChessPiece {
    private static final List<Direction> directions = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    );
    private final HurdlePolicy hurdlePolicy = new CannonHurdlePolicy();

    public Cannon(final ChessTeam team) {
        super(team, directions);
    }

    public static Map<ChessPosition, ChessPiece> initPieces() {
        return Map.of(
                new ChessPosition(2, 1), new Cannon(ChessTeam.RED),
                new ChessPosition(2, 7), new Cannon(ChessTeam.RED),
                new ChessPosition(7, 1), new Cannon(ChessTeam.BLUE),
                new ChessPosition(7, 7), new Cannon(ChessTeam.BLUE)
        );
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CANNON;
    }
}
