package dao.converter;

import dto.PieceDto;
import java.util.List;
import java.util.Map;
import model.board.Board;
import model.pieces.Piece;
import model.position.Position;

public class BoardConverter {

    public static List<PieceDto> convertToPieceDtos(Board board) {
        return board.getPieceStream()
                .map(BoardConverter::convertToPieceDto)
                .toList();
    }

    private static PieceDto convertToPieceDto(Map.Entry<Position, Piece> pieceByPosition) {
        Position position = pieceByPosition.getKey();
        Piece piece = pieceByPosition.getValue();
        return new PieceDto(position.row().value(), position.column().value(), piece.country(), piece.pieceType());
    }

    public static Board convertToBoard(List<PieceDto> pieceDtos) {
        Board board = new Board();
        for (PieceDto pieceDto : pieceDtos) {
            board.place(Position.of(pieceDto.row(), pieceDto.column()),
                    new Piece(pieceDto.country(), pieceDto.pieceType()));
        }
        return board;
    }
}
