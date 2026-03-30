package janggi.dto;

import janggi.domain.Board;
import janggi.domain.Piece;
import janggi.domain.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardDto {
    private final Map<List<Integer>, String> pieces;

    private BoardDto(Map<List<Integer>, String> pieces) {
        this.pieces = pieces;
    }

    public static BoardDto from(Board board) {
        Map<List<Integer>, String> result = new HashMap<>();
        Map<Position, Piece> pieces = board.getBoard();

        for (Position position : pieces.keySet()) {
            result.put(position.getPosition(), pieces.get(position).getPieceType().getName());
        }
        return new BoardDto(result);
    }

    public Map<List<Integer>, String> getPieces() {
        return pieces;
    }
}
