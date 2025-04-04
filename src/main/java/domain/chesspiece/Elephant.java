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

public class Elephant extends LimitedMoveChessPiece {
    private static final List<Directions> DIRECTIONS = List.of(
            new Directions(List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT_UP)),
            new Directions(List.of(Direction.UP, Direction.LEFT_UP, Direction.LEFT_UP)),
            new Directions(List.of(Direction.LEFT, Direction.LEFT_UP, Direction.LEFT_UP)),
            new Directions(List.of(Direction.LEFT, Direction.LEFT_DOWN, Direction.LEFT_DOWN)),
            new Directions(List.of(Direction.RIGHT, Direction.RIGHT_UP, Direction.RIGHT_UP)),
            new Directions(List.of(Direction.RIGHT, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN)),
            new Directions(List.of(Direction.DOWN, Direction.LEFT_DOWN, Direction.LEFT_DOWN)),
            new Directions(List.of(Direction.DOWN, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN))
    );

    public Elephant(final ChessTeam team, final ChessPosition position) {
        super(position, team, DIRECTIONS, new UnpassableHurdlePolicy());
    }

    public static List<ChessPiece> initPieces() {
        return List.of(
                new Elephant(ChessTeam.RED, new ChessPosition(0, 2)),
                new Elephant(ChessTeam.RED, new ChessPosition(0, 6)),
                new Elephant(ChessTeam.BLUE, new ChessPosition(9, 2)),
                new Elephant(ChessTeam.BLUE, new ChessPosition(9, 6))
        );
    }

    @Override
    public ChessPiece from(final ChessPosition position) {
        return new Elephant(getTeam(), position);
    }


    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.ELEPHANT;
    }

    @Override
    protected boolean canMoveInCastle(final ChessPosition position, final Directions direction) {
        return true;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Elephant elephant)) {
            return false;
        }
        return Objects.equals(getPosition(), elephant.getPosition()) && Objects.equals(getTeam(), elephant.getTeam());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getTeam());
    }
}
