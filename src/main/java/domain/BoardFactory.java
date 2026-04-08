package domain;

import domain.constant.Country;
import domain.constant.InitMaSangPosition;
import domain.constant.InitPiecePosition;
import domain.constant.PieceType;
import dto.SavedPieceDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BoardFactory {
    public static Map<Position, Piece> createInitBoard(List<PieceType> choMaSang, List<PieceType> hanMaSang) {
        Map<Position, Piece> board = new HashMap<>();
        for (InitPiecePosition initPosition : InitPiecePosition.values()) {
            addInitPiece(board, initPosition);
        }

        addInitMaSang(board, choMaSang, Country.CHO);
        addInitMaSang(board, hanMaSang, Country.HAN);
        return board;
    }

    private static void addInitPiece(Map<Position, Piece> board, InitPiecePosition initPosition) {
        addPieceByCountry(board, initPosition, Country.CHO);
        addPieceByCountry(board, initPosition, Country.HAN);
    }

    private static void addPieceByCountry(Map<Position, Piece> board, InitPiecePosition initPosition, Country country) {
        for (Position position : initPosition.getPositionsByCountry(country)) {
            board.put(position, new Piece(country, initPosition.getPieceType()));
        }
    }

    private static void addInitMaSang(Map<Position, Piece> board, List<PieceType> maSangPosition, Country country) {
        List<Position> positions = InitMaSangPosition.getPositionsByCountry(country);
        for (int i = 0; i < 4; i++) {
            board.put(positions.get(i), new Piece(country, maSangPosition.get(i)));
        }
    }

    public static Map<Position, Piece> createLoadBoard(List<SavedPieceDto> savedPieces) {
        Map<Position, Piece> board = new HashMap<>();
        for (SavedPieceDto savedPieceDto : savedPieces) {
            Position position = Position.create(savedPieceDto.row(), savedPieceDto.col());
            Piece piece = new Piece(Country.getCountry(savedPieceDto.country()), PieceType.from(
                    savedPieceDto.pieceType()));

            board.put(position, piece);
        }

        return board;
    }
}
