package domain.chessPiece;

import domain.direction.Direction;
import domain.direction.Directions;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;
import java.util.List;

public class Elephant extends LimitedMoveChessPiece {
    private static final List<Directions> directions = List.of(
            new Directions(List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT_UP)),
            new Directions(List.of(Direction.UP, Direction.LEFT_UP, Direction.LEFT_UP)),
            new Directions(List.of(Direction.LEFT, Direction.LEFT_UP, Direction.LEFT_UP)),
            new Directions(List.of(Direction.LEFT, Direction.LEFT_DOWN, Direction.LEFT_DOWN)),
            new Directions(List.of(Direction.RIGHT, Direction.RIGHT_UP, Direction.RIGHT_UP)),
            new Directions(List.of(Direction.RIGHT, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN)),
            new Directions(List.of(Direction.DOWN, Direction.LEFT_DOWN, Direction.LEFT_DOWN)),
            new Directions(List.of(Direction.DOWN, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN))
    );
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public Elephant(final ChessTeam team) {
        super(team, directions);
    }

    public Elephant(final ChessPosition position, final ChessTeam team) {
        super(position, team, directions);
    }

    @Override
    public ChessPiece from(final ChessPosition position) {
        return new Elephant(position, getTeam());
    }

    public static List<ChessPiece> initPieces() {
        return List.of(
                new Elephant(new ChessPosition(0, 2), ChessTeam.RED),
                new Elephant(new ChessPosition(0, 6), ChessTeam.RED),
                new Elephant(new ChessPosition(9, 2),ChessTeam.BLUE),
                new Elephant(new ChessPosition(9, 6),ChessTeam.BLUE)
        );
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.ELEPHANT;
    }

    @Override
    protected boolean canMove(final ChessPosition position, final Directions direction) {
        return true;
    }
}
