package view;

import board.Board;
import java.util.Map;
import pieces.Piece;
import position.Position;
import view.dto.PieceDto;

public class BoardViewMapper {
    public PieceDto[][] map(Board board) {
        PieceDto[][] result = new PieceDto[10][9];
        Map<Position, Piece> pieces = board.pieces();

        for (int row = 0; row <= 9; row++) {
            for (int column = 0; column <= 8; column++) {
                Piece piece = pieces.get(new Position(row, column));
                result[row][column] = toPieceDto(piece);
            }
        }
        return result;
    }

    private PieceDto toPieceDto(Piece piece) {
        if (piece.isEmpty()) {
            return new PieceDto(piece.getType(), null);
        }
        if (piece.isCho()) {
            return new PieceDto(piece.getType(), pieces.Side.CHO);
        }
        return new PieceDto(piece.getType(), pieces.Side.HAN);
    }
}
