package domain.chesspiece;

import domain.direction.Direction;
import domain.direction.Directions;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Pawn extends LimitedMoveChessPiece {

    private static final Map<ChessTeam, List<Directions>> DIRECTIONS = Map.of(
            ChessTeam.RED, List.of(
                    new Directions(List.of(Direction.LEFT)),
                    new Directions(List.of(Direction.RIGHT)),
                    new Directions(List.of(Direction.DOWN)),
                    new Directions(List.of(Direction.LEFT_DOWN)),
                    new Directions(List.of(Direction.RIGHT_DOWN))
            ),
            ChessTeam.BLUE, List.of(
                    new Directions(List.of(Direction.LEFT)),
                    new Directions(List.of(Direction.RIGHT)),
                    new Directions(List.of(Direction.UP)),
                    new Directions(List.of(Direction.LEFT_UP)),
                    new Directions(List.of(Direction.RIGHT_UP))
            )
    );

    public Pawn(final ChessTeam team, final ChessPosition position) {
        super(position, team, DIRECTIONS.get(team), new UnpassableHurdlePolicy());
    }

    public static List<ChessPiece> initPieces() {
        return List.of(
                new Pawn(ChessTeam.RED, new ChessPosition(3, 0)),
                new Pawn(ChessTeam.RED, new ChessPosition(3, 2)),
                new Pawn(ChessTeam.RED, new ChessPosition(3, 4)),
                new Pawn(ChessTeam.RED, new ChessPosition(3, 6)),
                new Pawn(ChessTeam.RED, new ChessPosition(3, 8)),
                new Pawn(ChessTeam.BLUE, new ChessPosition(6, 0)),
                new Pawn(ChessTeam.BLUE, new ChessPosition(6, 2)),
                new Pawn(ChessTeam.BLUE, new ChessPosition(6, 4)),
                new Pawn(ChessTeam.BLUE, new ChessPosition(6, 6)),
                new Pawn(ChessTeam.BLUE, new ChessPosition(6, 8))
        );
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.PAWN;
    }

    @Override
    public ChessPiece from(final ChessPosition position) {
        return new Pawn(getTeam(), position);
    }

    @Override
    protected boolean canMoveInCastle(final ChessPosition position, final Directions directions) {
        return position.canCastleMove(directions.getFirstDirection());
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Pawn pawn)) {
            return false;
        }
        return Objects.equals(getPosition(), pawn.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPosition());
    }
}
