package domain;

import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class BoardStatus {
    private final Map<Position, Piece> boardStatus;

    private BoardStatus(Map<Position, Piece> boardStatus) {
        this.boardStatus = boardStatus;
    }

    public static BoardStatus from(Map<Position, Piece> boardStatus) {
        return new BoardStatus(boardStatus);
    }

    public int getCountOfJumpablePieces(List<Position> movablePath) {
        int jumpedPieces = 0;
        for (Position position : movablePath) {
            Piece pieceToCheck = boardStatus.get(position);
            if (pieceToCheck == null) {
                continue;
            }
            if (!pieceToCheck.jumpable()) {
                throw new IllegalArgumentException(PieceExceptionMessage.CANT_JUMP_OVER_PO.getMessage());
            }
            jumpedPieces += 1;
        }
        return jumpedPieces;
    }

    public boolean isPieceAt(Position position) {
        Piece piece = boardStatus.get(position);
        if (piece != null) {
            throw new IllegalArgumentException(PieceExceptionMessage.BLOCKED_BY_PIECE.getMessage());
        }
        return true;
    }

    public Map<Position, Piece> getBoardStatus() {
        return boardStatus;
    }
}
