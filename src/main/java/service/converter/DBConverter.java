package service.converter;

import dao.PieceInfo;
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
    public static Board convertToBoard(List<PieceInfo> information) {
        Map<Position, Piece> survivedPieces = convertToPieces(information);

        return new Board(survivedPieces);
    }

    private static Map<Position, Piece> convertToPieces(List<PieceInfo> information) {
        return information.stream()
                .collect(Collectors.toMap(
                        pieceInfo -> Position.of(pieceInfo.row(), pieceInfo.column()),
                        pieceInfo -> DBConverter.convertToPiece(pieceInfo.type(), pieceInfo.dynasty())
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
