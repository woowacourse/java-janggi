package ui.dto;

import domain.piece.Piece;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardStatusDto {
    private final List<PieceDto> board;

    public BoardStatusDto(List<PieceDto> board) {
        this.board = board;
    }

    public static BoardStatusDto from(Map<Position, Piece> board) {
        List<PieceDto> dtos = new ArrayList<>();
        for (Position position : board.keySet()) {
            dtos.add(PieceDto.toDto(position, board.get(position)));
        }
        return new BoardStatusDto(dtos);
    }

    public List<PieceDto> getBoard() {
        return board;
    }
}
