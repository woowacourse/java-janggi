package janggi.dao.entity;

import janggi.domain.game.Game;
import janggi.domain.piece.Piece;
import janggi.domain.point.Point;
import java.util.Map.Entry;

public record MoveEntity(
        Integer id,
        String pieceType,
        String side,
        int x,
        int y
) {

    public static MoveEntity of(Game game, Point point) {
        return new MoveEntity(
                null,
                game.getPieceType(point).name(),
                game.getSideAt(point).name(),
                point.x(),
                point.y());
    }

    public static MoveEntity from(Entry<Point, Piece> move) {
        return new MoveEntity(
                null,
                move.getValue().getPieceType().name(),
                move.getValue().getSide().name(),
                move.getKey().x(),
                move.getKey().y());
    }
}
