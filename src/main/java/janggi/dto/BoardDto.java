package janggi.dto;

import janggi.domain.piece.Piece;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.util.PieceMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record BoardDto(
        List<List<String>> board
) {

    public static BoardDto from(Map<Position, Piece> board) {
        List<List<String>> allPieces = new ArrayList<>();
        for (int row = Row.MIN_ROW; row <= Row.MAX_ROW; row++) {
            List<String> piecesByRow = new ArrayList<>();
            for (int column = Column.MIN_COLUMN; column <= Column.MAX_COLUMN; column++) {
                Position position = Position.from(row, column);
                if (board.containsKey(position)) {
                    Piece piece = board.get(position);
                    piecesByRow.add(PieceMapper.from(piece));
                    continue;
                }
                piecesByRow.add("  ");
            }
            allPieces.add(piecesByRow);
        }
        return new BoardDto(allPieces);
    }

}
