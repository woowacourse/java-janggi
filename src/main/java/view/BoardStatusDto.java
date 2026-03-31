package view;

import domain.piece.Piece;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record BoardStatusDto(
        List<PieceDto> board) {

    public static BoardStatusDto from(Map<Position, Piece> board) {
        List<PieceDto> dtos = new ArrayList<>();
        for (Position position : board.keySet()) {
            dtos.add(PieceDto.toDto(position, board.get(position)));
        }
        return new BoardStatusDto(dtos);
    }
}
