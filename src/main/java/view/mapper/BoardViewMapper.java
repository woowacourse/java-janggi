package view.mapper;

import domain.board.Board;
import domain.pieces.Piece;
import domain.pieces.Side;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import view.dto.PieceDto;

public class BoardViewMapper {
    public List<List<PieceDto>> map(Board board) {
        List<List<PieceDto>> result = new ArrayList<>();
        Map<Position, Piece> pieces = board.pieces();
        for (int row = 0; row <= 9; row++) {
            List<PieceDto> rowList = new ArrayList<>();
            for (int column = 0; column <= 8; column++) {
                Piece piece = pieces.get(new Position(row, column));
                rowList.add(toPieceDto(piece));
            }
            result.add(rowList);
        }
        return result;
    }

    private PieceDto toPieceDto(Piece piece) {
        if (piece.isEmpty()) {
            return new PieceDto(piece.getType(), null);
        }
        if (piece.getSide().isCho()) {
            return new PieceDto(piece.getType(), Side.CHO);
        }
        return new PieceDto(piece.getType(), Side.HAN);
    }
}
