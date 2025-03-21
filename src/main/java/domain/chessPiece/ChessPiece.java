package domain.chessPiece;

import domain.position.ChessPiecePositions;
import domain.type.ChessPieceType;
import domain.position.ChessPosition;
import domain.type.ChessTeam;

import java.util.List;

public interface ChessPiece {
    ChessPosition getPosition();
    List<ChessPosition> getDestinations(final ChessPiecePositions positions);
    ChessPieceType getChessPieceType();
    ChessTeam getTeam();
}
