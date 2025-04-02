package dao.converter;

import dao.PieceEntity;
import janggiGame.Position;
import janggiGame.piece.Piece;
import janggiGame.piece.character.Dynasty;
import java.util.List;
import java.util.Map;

public class BoardConverter {
    public static List<PieceEntity> convertToPieceEntities(final Map<Position, Piece> survivedPieces) {
        return survivedPieces.entrySet().stream()
                .map(BoardConverter::convertToPieceEntity)
                .toList();
    }

    private static PieceEntity convertToPieceEntity(Map.Entry<Position, Piece> survivedPieces) {
        Position position = survivedPieces.getKey();
        Piece piece = survivedPieces.getValue();

        String dynasty = Dynasty.CHO.name();
        if (piece.hasDynasty(Dynasty.HAN)) {
            dynasty = Dynasty.HAN.name();
        }

        return new PieceEntity(position.getRow(), position.getColumn(),
                piece.getType().name(), dynasty);
    }
}
