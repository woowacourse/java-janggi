package domain;

import java.util.List;

import static domain.Direction.*;
import static domain.Direction.RIGHT;

public class Cannon extends UnlimitedMoveChessPiece {
    private static final List<Direction> directions = List.of(UP, DOWN, LEFT, RIGHT);

    public Cannon(ChessPosition position, final ChessTeam team) {
        super(position, team, directions);
    }

    public static List<Cannon> initPieces() {
        return List.of(
                new Cannon(new ChessPosition(2, 1), ChessTeam.RED),
                new Cannon(new ChessPosition(2, 7), ChessTeam.RED),
                new Cannon(new ChessPosition(7, 1), ChessTeam.BLUE),
                new Cannon(new ChessPosition(7, 7), ChessTeam.BLUE)
        );
    }

    @Override
    protected List<ChessPosition> getCoordinateDestinations(final List<Path> coordinates,
                                                            final ChessPiecePositions positions) {
        return  List.of(new ChessPosition(3, 4), new ChessPosition(4, 4),
                new ChessPosition(5, 4), new ChessPosition(6, 4));
    }


    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CANNON;
    }

}
