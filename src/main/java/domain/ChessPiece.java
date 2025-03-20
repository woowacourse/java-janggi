package domain;

import java.util.List;

interface ChessPiece {
    ChessPosition getPosition();
    List<ChessPosition> getDestinations(final ChessPiecePositions positions);
    ChessPieceType getChessPieceType();
    ChessTeam getTeam();
}
