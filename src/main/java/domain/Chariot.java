package domain;

import java.util.List;

import static domain.Direction.*;
import static domain.Direction.RIGHT;

public class Chariot extends UnlimitedMoveChessPiece {

    private static final List<Direction> directions = List.of(UP, DOWN, LEFT, RIGHT);

    public Chariot(ChessPosition chessPosition, final ChessTeam team) {
        super(chessPosition, team, directions);
    }

    @Override
    protected List<ChessPosition> getCoordinateDestinations(final List<Path> coordinates,
                                                            final ChessPiecePositions positions) {
        return List.of(
                new ChessPosition(2, 4),
                new ChessPosition(3, 4),
                new ChessPosition(4, 4),
                new ChessPosition(5, 4),
                new ChessPosition(6,4),
                new ChessPosition(7, 3),
                new ChessPosition(7,5),
                new ChessPosition(7,8));
    }

    public static List<Chariot> initPieces() {
        return List.of(
                new Chariot(new ChessPosition(0, 0), ChessTeam.RED),
                new Chariot(new ChessPosition(0, 8), ChessTeam.RED),
                new Chariot(new ChessPosition(9, 0), ChessTeam.BLUE),
                new Chariot(new ChessPosition(9, 8), ChessTeam.BLUE)
        );
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CHARIOT;
    }

}
