package domain;

import java.util.List;

import static domain.Direction.*;

public class King extends LimitedChessPiece {

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

    public King(ChessPosition position, final ChessTeam team) {
        super(position, team, directions);
    }

    public static List<King> initPieces() {
        return List.of(
                new King(new ChessPosition(1, 4), ChessTeam.RED),
                new King(new ChessPosition(8, 4), ChessTeam.BLUE)
        );
    }

    @Override
    protected List<ChessPosition> getCoordinateDestinations(List<Path> coordinates) {
        return null;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.KING;
    }
}
