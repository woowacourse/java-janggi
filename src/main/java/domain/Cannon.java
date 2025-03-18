package domain;

import java.util.List;

public class Cannon implements ChessPiece{
    @Override
    public List<Path> getAvailablePaths(final ChessPosition chessPosition) {
        return List.of();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return null;
    }
}
