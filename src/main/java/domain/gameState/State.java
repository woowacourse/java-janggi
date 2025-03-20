package domain.gameState;

import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

public interface State {
    State movePiece(PieceType pieceType, Position source, Position destination);

    PieceColor getColor();

    boolean isFinished();

}
