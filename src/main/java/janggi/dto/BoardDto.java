package janggi.dto;

import janggi.domain.Board;
import janggi.domain.Piece;
import janggi.domain.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardDto {
    private final List<PieceDto> pieces;

    private BoardDto(List<PieceDto> pieces) {
        this.pieces = pieces;
    }

    public static BoardDto from(Board board) {
        List<PieceDto> result = new ArrayList<>();
        Map<Position, Piece> pieces = board.getBoard();

        for(Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            result.add(new PieceDto(
                    position.getRow(),
                    position.getColumn(),
                    piece.getPieceType().getName(),
                    piece.getTeam().name()));
        }
        return new BoardDto(result);
    }

    public List<PieceDto> getPieces() {
        return pieces;
    }
}
