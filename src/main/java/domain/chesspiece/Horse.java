package domain.chesspiece;

import domain.direction.Direction;
import domain.direction.Directions;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;
import java.util.List;
import java.util.Objects;

public class Horse extends LimitedMoveChessPiece {
        private static final List<Directions> DIRECTIONS = List.of(
            new Directions(List.of(Direction.UP, Direction.RIGHT_UP)),
            new Directions(List.of(Direction.UP, Direction.LEFT_UP)),
            new Directions(List.of(Direction.LEFT, Direction.LEFT_UP)),
            new Directions(List.of(Direction.LEFT, Direction.LEFT_DOWN)),
            new Directions(List.of(Direction.RIGHT, Direction.RIGHT_UP)),
            new Directions(List.of(Direction.RIGHT, Direction.RIGHT_DOWN)),
            new Directions(List.of(Direction.DOWN, Direction.LEFT_DOWN)),
            new Directions(List.of(Direction.DOWN, Direction.RIGHT_DOWN))
    );

    public Horse(final ChessTeam team, final ChessPosition position) {
        super(position, team, DIRECTIONS, new UnpassableHurdlePolicy());
    }

    public static List<ChessPiece> initPieces() {
        return List.of(
                new Horse(ChessTeam.RED, new ChessPosition(0, 1)),
                new Horse(ChessTeam.RED, new ChessPosition(0, 7)),
                new Horse(ChessTeam.BLUE, new ChessPosition(9, 1)),
                new Horse(ChessTeam.BLUE, new ChessPosition(9, 7))
        );
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.HORSE;
    }

    @Override
    public ChessPiece from(final ChessPosition position) {
        return new Horse(getTeam(), position);
    }

    @Override
    protected boolean canMoveInCastle(final ChessPosition position, final Directions direction) {
        return true;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Horse horse)) {
            return false;
        }
        return Objects.equals(getPosition(), horse.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPosition());
    }
}
