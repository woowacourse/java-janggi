package janggi.dto;

import janggi.domain.piece.Piece;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.util.PieceMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record PiecesDto(
        List<List<String>> pieces
) {

    public static PiecesDto from(Map<Position, Piece> pieces) {
        List<List<String>> allPieces = new ArrayList<>();
        for (int row = Row.MIN_ROW; row <= Row.MAX_ROW; row++) {
            List<String> piecesByRow = new ArrayList<>();
            for (int column = Column.MIN_COLUMN; column < Column.MAX_COLUMN; column++) {
                Position position = Position.from(row, column);
                if (pieces.containsKey(position)) {
                    Piece piece = pieces.get(position);
                    piecesByRow.add(PieceMapper.from(piece));
                }
            }
        }
        return new PiecesDto(allPieces);
    }

}
