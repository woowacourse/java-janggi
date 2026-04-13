package janggi.dao.entity;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record BoardEntity(
        Integer gameId,
        List<MoveEntity> moveEntities
) {

    public static BoardEntity of(Integer gameId, Map<Point, Piece> board) {
        return new BoardEntity(gameId, board.entrySet()
                .stream()
                .map(MoveEntity::from)
                .toList());
    }

    public Board toDomain() {
        Map<Point, Piece> board = new HashMap<>();
        for (MoveEntity moveEntity : moveEntities) {
            Side side = Side.valueOf(moveEntity.side());
            Piece piece = PieceType.valueOf(moveEntity.pieceType()).createPiece(side);
            Point point = new Point(moveEntity.x(), moveEntity.y());

            board.put(point, piece);
        }
        return new Board(gameId, board);
    }
}
