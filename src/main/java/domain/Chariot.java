package domain;

import java.util.List;

import static domain.Direction.*;
import static domain.Direction.RIGHT;

public class Chariot extends UnlimitedMoveChessPiece {

    private static final List<Direction> directions = List.of(UP, DOWN, LEFT, RIGHT);

    public Chariot(ChessPosition chessPosition, final ChessTeam team) {
        super(chessPosition, team, directions);
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

    @Override
    protected List<ChessPosition> getCoordinateDestinations(List<Path> coordinates) {
        return null;
    }
}
