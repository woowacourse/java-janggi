package domain;

import java.util.List;

import static domain.Direction.*;

public class Pawn extends LimitedChessPiece {
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

    public Pawn(ChessPosition position, final ChessTeam chessTeam) {
        super(position, chessTeam, directions);
    }

    public static List<Pawn> initPieces() {
        return List.of(
                new Pawn(new ChessPosition(3, 0), ChessTeam.RED),
                new Pawn(new ChessPosition(3, 2), ChessTeam.RED),
                new Pawn(new ChessPosition(6, 0), ChessTeam.BLUE),
                new Pawn(new ChessPosition(6, 2), ChessTeam.BLUE)
        );
    }

    @Override
    protected List<ChessPosition> getCoordinateDestinations(List<Path> coordinates) {
        return null;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.PAWN;
    }
}
