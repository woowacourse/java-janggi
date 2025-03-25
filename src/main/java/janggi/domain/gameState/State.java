package janggi.domain.gameState;

import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import janggi.domain.piece.PieceType;

public interface State {
    State movePiece(PieceType pieceType, Position source, Position destination);

    TeamColor getColor();

    boolean isFinished();
}
