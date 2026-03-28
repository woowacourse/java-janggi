package domain;

import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;

public class BoardStatus {
    private final Map<Position, Piece> boardStatus;

    private BoardStatus(Map<Position, Piece> boardStatus) {
        this.boardStatus = boardStatus;
    }

    public static BoardStatus from(Map<Position, Piece> boardStatus) {
        return new BoardStatus(boardStatus);
    }

    public Map<Position, Piece> getBoardStatus() {
        return boardStatus;
    }
}
