package dto;

import game.Board;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import piece.Piece;
import position.Position;

public record PieceDto(
        String column,
        String row,
        String country,
        String pieceType
) {

    public static List<PieceDto> toDtoFromBoard(Board board) {
        List<PieceDto> list = new ArrayList<>();
        for (Map.Entry<Position, Piece> entry : board.getBoard().entrySet()) {
            Position pos = entry.getKey();
            Piece piece = entry.getValue();

            list.add(new PieceDto(
                    pos.column().name(),
                    String.valueOf(pos.row().ordinal() + 1),
                    piece.getCountry().name(),
                    piece.getPieceType().name()
            ));
        }
        return list;
    }
}
