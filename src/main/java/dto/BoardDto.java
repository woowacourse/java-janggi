package dto;

import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.Map;

public class BoardDto {

    private final Map<Position, Piece> board;

    public BoardDto(Map<Position, Piece> board) {
        this.board = board;
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
