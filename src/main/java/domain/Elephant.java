package domain;

import java.util.List;

public class Elephant implements ChessPiece{

    private final ChessPosition position;
    private final ChessTeam team;

    public Elephant(ChessPosition position, final ChessTeam team) {
        this.position = position;
        this.team = team;
    }

    public static List<Elephant> initPieces() {
        return List.of(
                new Elephant(new ChessPosition(0, 2), ChessTeam.RED),
                new Elephant(new ChessPosition(0, 6), ChessTeam.RED),
                new Elephant(new ChessPosition(9, 2), ChessTeam.BLUE),
                new Elephant(new ChessPosition(9, 6), ChessTeam.BLUE)
        );
    }

    @Override
    public ChessPosition getPosition() {
        return position;
    }

    @Override
    public List<Path> getAvailablePaths() {
        return List.of();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.ELEPHANT;
    }
}
