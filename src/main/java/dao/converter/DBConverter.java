package dao.converter;

import dao.PieceEntity;
import janggiGame.Board;
import janggiGame.Position;
import janggiGame.piece.Advisor;
import janggiGame.piece.Cannon;
import janggiGame.piece.Chariot;
import janggiGame.piece.Elephant;
import janggiGame.piece.Horse;
import janggiGame.piece.King;
import janggiGame.piece.Pawn;
import janggiGame.piece.Piece;
import janggiGame.piece.character.Dynasty;
import janggiGame.piece.character.PieceType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DBConverter {
    public static Board convertToBoard(List<PieceEntity> pieceEntities) {
        Map<Position, Piece> survivedPieces = convertToPieceEntities(pieceEntities);
        return new Board(survivedPieces);
    }

    private static Map<Position, Piece> convertToPieceEntities(List<PieceEntity> pieceEntities) {
        return pieceEntities.stream()
                .collect(Collectors.toMap(
                        pieceEntity -> Position.of(pieceEntity.row(), pieceEntity.column()),
                        pieceEntity -> DBConverter.convertToPiece(pieceEntity.type(), pieceEntity.dynasty())
                ));
    }

    private static Piece convertToPiece(String type, String dynastyName) {
        Dynasty dynasty = Dynasty.valueOf(dynastyName);

        return switch (PieceType.valueOf(type)) {
            case PieceType.HORSE -> new Horse(dynasty);
            case PieceType.PAWN -> new Pawn(dynasty);
            case PieceType.CANNON -> new Cannon(dynasty);
            case PieceType.ADVISOR -> new Advisor(dynasty);
            case PieceType.CHARIOT -> new Chariot(dynasty);
            case PieceType.ELEPHANT -> new Elephant(dynasty);
            case PieceType.KING -> new King(dynasty);
            default -> throw new IllegalArgumentException("존재하지 않는 기물의 종류 입니다: " + type);
        };
    }
}
