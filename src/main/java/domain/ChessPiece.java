package domain;

import java.util.List;

interface ChessPiece {
    ChessPosition getPosition();
    List<Path> getAvailablePaths(final ChessPiecePositions chessPiecePositions);
    ChessPieceType getChessPieceType();
}
