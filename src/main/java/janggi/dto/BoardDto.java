package janggi.dto;

import janggi.domain.Board;
import janggi.domain.Position;

import janggi.domain.Team;
import java.util.HashMap;
import java.util.Map;

public class BoardDto {
    private final Map<CoordinateDto, PieceDto> pieces;

    private BoardDto(Map<CoordinateDto, PieceDto> pieces) {
        this.pieces = pieces;
    }

    public static BoardDto from(Board board) {
        Map<CoordinateDto, PieceDto> result = new HashMap<>();
        var domainBoard = board.getBoard();

        for (var entry : domainBoard.entrySet()) {
            Position pos = entry.getKey();
            String name = entry.getValue().getName();
            Team team = entry.getValue().getTeam();

            result.put(new CoordinateDto(pos.getRowValue(), pos.getColumnValue()), new PieceDto(name, team));
        }
        return new BoardDto(result);
    }

    public Map<CoordinateDto, PieceDto> getPieces() {
        return pieces;
    }

    public record CoordinateDto(int row, int column) {
        public static CoordinateDto from(Position position) {
            return new CoordinateDto(position.getRowValue(), position.getColumnValue());
        }
    }
}
