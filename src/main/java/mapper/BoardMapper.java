package mapper;

import domain.coordinate.Position;
import domain.piece.Piece;
import dto.BoardDto;

import java.util.Map;

public class BoardMapper {

    public static BoardDto toDto(Map<Position, Piece> board) {
        return new BoardDto(board);
    }
}
