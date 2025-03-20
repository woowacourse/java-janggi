package domain;

import java.util.List;

interface ChessPiece {
    ChessPosition getPosition();
    List<ChessPosition> getDestinations();
    ChessPieceType getChessPieceType();
}
