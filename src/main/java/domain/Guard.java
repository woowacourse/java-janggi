package domain;

import java.util.List;

import static domain.Direction.*;

public class Guard extends LimitedChessPiece {
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

    public Guard(ChessPosition position, final ChessTeam team) {
        super(position, team, directions);
    }

    public static List<Guard> initPieces() {
        return List.of(
                new Guard(new ChessPosition(0, 3), ChessTeam.RED),
                new Guard(new ChessPosition(0, 5), ChessTeam.RED),
                new Guard(new ChessPosition(9, 3), ChessTeam.BLUE),
                new Guard(new ChessPosition(9, 5), ChessTeam.BLUE)
        );
    }

    @Override
    protected List<ChessPosition> getCoordinateDestinations(final List<Path> coordinates,
                                                            final ChessPiecePositions positions) {
        return List.of();
    }


    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.GUARD;
    }
}
