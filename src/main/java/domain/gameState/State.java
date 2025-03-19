package domain.gameState;

import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;

public interface State {
    State movePiece(Piece piece, Position source, Position destination);

    PieceColor getColor();
}
