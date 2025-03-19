package domain;

import java.util.List;

public class Guard implements ChessPiece{

    private final ChessPosition position;
    private final ChessTeam team;

    public Guard(ChessPosition position, final ChessTeam team) {
        this.position = position;
        this.team = team;
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
    public ChessPosition getPosition() {
        return position;
    }

    @Override
    public List<Path> getAvailablePaths() {
        return List.of();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.GUARD;
    }
}
