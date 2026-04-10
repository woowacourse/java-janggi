package janggi.dao.entity;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.point.Point;
import java.util.Map;

public record BoardEntity(
        int gameId,
        Map<Point, Piece> board
) {
    public static BoardEntity fromDomain(Board board) {
        return new BoardEntity(board.getGameId(), board.getPieces());
    }

    public Board toDomain() {
        return new Board(gameId, board);
    }
}
