package janggi.dto;

import janggi.constants.Color;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;

public record PositionPieceDto(int x, int y, String pieceName, String teamName) {
    public static PositionPieceDto from(Position position, Piece piece) {
        return new PositionPieceDto(position.getX(), position.getY(),
                piece.getPieceName(), piece.getTeamName());
    }

    public String teamColor() {
        if (teamName.equals("HAN")) {
            return Color.RED;
        }
        return Color.GREEN;
    }
}
