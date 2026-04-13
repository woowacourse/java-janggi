package domain.dto;

import domain.Position;
import domain.Team;
import domain.board.JanggiBoard;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class BoardMapper {

    public static BoardDto from(JanggiBoard janggiBoard) {
        List<List<PieceDto>> rows = new ArrayList<>();
        for (int row = 0; row < 10; row++) {
            rows.add(createRowDto(janggiBoard, row));
        }
        return new BoardDto(rows);
    }

    private static List<PieceDto> createRowDto(JanggiBoard board, int row) {
        List<PieceDto> rowPieces = new ArrayList<>();
        for (int column = 0; column < 9; column++) {
            Piece piece = board.getPiece(new Position(row, column));
            rowPieces.add(new PieceDto(format(piece)));
        }
        return rowPieces;
    }

    private static String format(Piece piece) {
        PieceType pieceType = piece.getPieceType();
        String pieceTypeName = pieceType.getName();

        if (piece.getTeam() == Team.HAN) {
            return pieceTypeName.toLowerCase();
        }
        return pieceTypeName.toUpperCase();
    }
}
