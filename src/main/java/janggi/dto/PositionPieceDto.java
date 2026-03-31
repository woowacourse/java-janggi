package janggi.dto;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;

public record PositionPieceDto(int x, int y, String pieceName, String teamName) {
    public static PositionPieceDto from(Position position, Piece piece) {
        return new PositionPieceDto(position.getX(), position.getY(),
                piece.getName().getName(), piece.getTeam().name());
    }

    public boolean isSame(int x, int y) {
        return this.x == x && this.y == y;
    }
}
