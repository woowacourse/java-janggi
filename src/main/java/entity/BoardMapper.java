package entity;

import domain.board.Board;
import domain.board.BoardPoint;
import domain.pieces.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardMapper {
    public static Board toBoard(List<BoardEntity> boardEntities, Map<Long, Piece> pieces) {
        Map<BoardPoint, Piece> locations = new HashMap<>();

        for (BoardEntity boardEntity : boardEntities) {
            BoardPoint boardPoint = new BoardPoint(boardEntity.getRowIndex(), boardEntity.getColumnIndex());
            long pieceId = boardEntity.getPieceId();

            locations.put(boardPoint, pieces.get(pieceId));
        }

        return new Board(locations);
    }
}
