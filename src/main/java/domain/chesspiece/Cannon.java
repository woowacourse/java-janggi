package domain.chesspiece;

import domain.direction.Direction;
import domain.hurdlePolicy.CannonHurdlePolicy;
import domain.hurdlePolicy.HurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;
import java.util.List;
import java.util.Objects;

public class Cannon extends UnlimitedMoveChessPiece {
    private static final List<Direction> DIRECTIONS = Direction.getAll();

    public Cannon(final ChessTeam team, final ChessPosition position) {
        super(team, position, DIRECTIONS, new CannonHurdlePolicy());
    }

    public static List<ChessPiece> initPieces() {
        return List.of(
                new Cannon(ChessTeam.RED,new ChessPosition(2, 1)),
                new Cannon(ChessTeam.RED, new ChessPosition(2, 7)),
                new Cannon(ChessTeam.BLUE, new ChessPosition(7, 1)),
                new Cannon(ChessTeam.BLUE, new ChessPosition(7, 7))
        );
    }

    @Override
    public ChessPiece from(final ChessPosition position) {
        return new Cannon(this.getTeam(), position);
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CANNON;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Cannon cannon)) {
            return false;
        }
        return Objects.equals(getPosition(), cannon.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPosition());
    }
}
