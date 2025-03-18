package domain;

import java.util.List;

public class Cannon implements ChessPiece{

    private final ChessTeam team;

    public Cannon(final ChessTeam team) {
        this.team = team;
    }

    @Override
    public List<Path> getAvailablePaths(final ChessPosition chessPosition) {
        return List.of();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CANNON;
    }
}
