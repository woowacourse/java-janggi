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
            Direction.RIGHT,
            Direction.LEFT_UP,
            Direction.LEFT_DOWN,
            Direction.RIGHT_UP,
            Direction.RIGHT_DOWN
    );

    private final HurdlePolicy hurdlePolicy = new StopAtHurdlePolicy();

    public Chariot(final ChessTeam team) {
        super(team, directions);
    }

    public Chariot(final ChessTeam team, final ChessPosition position) {
        super(team, position, directions);
    }

    @Override
    public ChessPiece from(final ChessPosition position) {
        return new Chariot(getTeam(), position);
    }


    public static List<ChessPiece> initPieces() {
        return List.of(
                new Chariot(ChessTeam.RED, new ChessPosition(0, 0)),
                new Chariot(ChessTeam.RED, new ChessPosition(0, 8)),
                new Chariot(ChessTeam.BLUE, new ChessPosition(9, 0)),
                new Chariot(ChessTeam.BLUE, new ChessPosition(9, 8))
        );
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CHARIOT;
    }
}
