package domain.chessPiece;

import domain.direction.Direction;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.StopAtHurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;

import java.util.List;
import java.util.Map;

public class Chariot extends UnlimitedMoveChessPiece {

    private static final List<Direction> directions = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    );
    private final HurdlePolicy hurdlePolicy = new StopAtHurdlePolicy();

    public Chariot(final ChessTeam team) {
        super(team, directions);
    }

    public static Map<ChessPosition, ChessPiece> initPieces() {
        return Map.of(
                new ChessPosition(0, 0), new Chariot(ChessTeam.RED),
                new ChessPosition(0, 8), new Chariot(ChessTeam.RED),
                new ChessPosition(9, 0), new Chariot(ChessTeam.BLUE),
                new ChessPosition(9, 8), new Chariot(ChessTeam.BLUE)
        );
    }

    @Override
    protected HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CHARIOT;
    }
}
