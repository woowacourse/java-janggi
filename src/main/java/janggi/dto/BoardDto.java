package janggi.dto;

import janggi.domain.Board;
import janggi.domain.Position;

import java.util.HashMap;
import java.util.Map;

public class BoardDto {
    private final Map<CoordinateDto, String> pieces;

    private BoardDto(Map<CoordinateDto, String> pieces) {
        this.pieces = pieces;
    }

    public static BoardDto from(Board board) {
        Map<CoordinateDto, String> result = new HashMap<>();
        var domainBoard = board.getBoard();

        for (var entry : domainBoard.entrySet()) {
            Position pos = entry.getKey();
            String name = entry.getValue().getName();

            result.put(new CoordinateDto(pos.getRowValue(), pos.getColumnValue()), name);
        }
        return new BoardDto(result);
    }

    public Map<CoordinateDto, String> getPieces() {
        return pieces;
    }

    public record CoordinateDto(int row, int column) {
        public static CoordinateDto from(Position position) {
            return new CoordinateDto(position.getRowValue(), position.getColumnValue());
        }
    }
}
