package domain;

import java.util.List;

public class Elephant implements ChessPiece{

    private final ChessTeam team;

    public Elephant(final ChessTeam team) {
        this.team = team;
    }

    @Override
    public List<Path> getAvailablePaths(final ChessPosition chessPosition) {
        return List.of();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.ELEPHANT;
    }
}
