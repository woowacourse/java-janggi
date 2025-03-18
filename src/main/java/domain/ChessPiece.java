package domain;

import java.util.List;

interface ChessPiece {

    List<Path> getAvailablePaths(final ChessPosition chessPosition);
    ChessPieceType getChessPieceType();
}
