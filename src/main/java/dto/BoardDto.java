package dto;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.piece.Piece;
import java.util.Map;

public class BoardDto {
    private final Map<BoardLocation, Piece> pieces;

    public BoardDto(Map<BoardLocation, Piece> pieces) {
        this.pieces = pieces;
    }

    public Board toBoard() {
        return new Board(pieces);
    }
}
