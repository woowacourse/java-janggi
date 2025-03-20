package domain;

import static domain.Direction.DOWN;
import static domain.Direction.LEFT;
import static domain.Direction.LEFT_DOWN;
import static domain.Direction.LEFT_UP;
import static domain.Direction.RIGHT;
import static domain.Direction.RIGHT_DOWN;
import static domain.Direction.RIGHT_UP;
import static domain.Direction.UP;

import java.util.List;

public class Horse extends LimitedChessPiece {
    private static final List<Directions> directions = List.of(
            new Directions(List.of(UP, RIGHT_UP)),
            new Directions(List.of(UP, LEFT_UP)),
            new Directions(List.of(LEFT, LEFT_UP)),
            new Directions(List.of(LEFT, LEFT_DOWN)),
            new Directions(List.of(RIGHT, RIGHT_UP)),
            new Directions(List.of(RIGHT, RIGHT_DOWN)),
            new Directions(List.of(DOWN, LEFT_DOWN)),
            new Directions(List.of(DOWN, RIGHT_DOWN))
    );

    public Horse(ChessPosition chessPosition, final ChessTeam team) {
        super(chessPosition, team, directions);
    }

    public static List<Horse> initPieces() {
        return List.of(
                new Horse(new ChessPosition(0, 1), ChessTeam.RED),
                new Horse(new ChessPosition(0, 7), ChessTeam.RED),
                new Horse(new ChessPosition(9, 1), ChessTeam.BLUE),
                new Horse(new ChessPosition(9, 7), ChessTeam.BLUE)
        );
    }

    @Override
    protected List<ChessPosition> getCoordinateDestinations(final List<Path> coordinates,
                                                            final ChessPiecePositions positions) {
        return List.of(new ChessPosition(6, 5),
                new ChessPosition(6, 3),
                new ChessPosition(2, 3),
                new ChessPosition(2, 5),
                new ChessPosition(5, 6),
                new ChessPosition(3, 6),
                new ChessPosition(3, 2),
                new ChessPosition(5, 2));
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.HORSE;
    }
}
