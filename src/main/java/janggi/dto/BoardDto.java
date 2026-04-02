package janggi.dto;

import janggi.domain.Board;
import java.util.List;

public class BoardDto {
    private final List<PieceDto> pieces;

    private BoardDto(List<PieceDto> pieces) {
        this.pieces = pieces;
    }

    public static BoardDto from(Board board) {
        List<PieceDto> pieces = board.getBoard().entrySet().stream()
                .map(entry -> new PieceDto(
                        entry.getKey().getRow(),
                        entry.getKey().getColumn(),
                        entry.getValue().getPieceType().getName(),
                        entry.getValue().getTeam().name()))
                .toList();

        return new BoardDto(pieces);
    }

    public List<PieceDto> getPieces() {
        return pieces;
    }
}
