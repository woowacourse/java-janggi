package domain.chesspiece;

import domain.direction.Direction;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.StopAtHurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;
import java.util.List;
import java.util.Objects;

public class Chariot extends UnlimitedMoveChessPiece {

    private static final List<Direction> DIRECTIONS = Direction.getAll();

    public Chariot(final ChessTeam team, final ChessPosition position) {
        super(team, position, DIRECTIONS, new StopAtHurdlePolicy());
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
    public ChessPiece from(final ChessPosition position) {
        return new Chariot(getTeam(), position);
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CHARIOT;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Chariot chariot)) {
            return false;
        }
        return Objects.equals(getPosition(), chariot.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPosition());
    }
}
