package dao.converter;

import dto.PieceDto;
import java.util.ArrayList;
import java.util.List;
import model.board.Board;
import model.pieces.Piece;
import model.position.Position;

public class BoardConverter {

    public static List<PieceDto> convertToPieceDtos(Board board) {
        List<PieceDto> pieceDtos = new ArrayList<>();

        board.forEach((position, piece) -> {
            pieceDtos.add(new PieceDto(
                    position.row().value(),
                    position.column().value(),
                    piece.country(),
                    piece.pieceType()
            ));
        });
        return pieceDtos;
    }

    public static Board convertToBoard(List<PieceDto> pieceDtos) {
        Board board = new Board();
        pieceDtos.forEach(pieceDto -> board.place(Position.of(pieceDto.row(), pieceDto.column()),
                new Piece(pieceDto.country(), pieceDto.pieceType())));
        return board;
    }

    public static String toSnapshot(Board board) {
        StringBuilder sb = new StringBuilder();

        for (int row = 1; row <= 10; row++) {
            for (int col = 1; col <= 9; col++) {
                Position pos = Position.of(row, col);
                board.findPiece(pos).ifPresentOrElse(
                        piece -> sb.append(piece.country().convertLabel(piece.pieceType().label())),
                        () -> sb.append(".")
                );
            }
        }
        return sb.toString();
    }
}
