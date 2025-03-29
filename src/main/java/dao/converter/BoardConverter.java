package dao.converter;

import dao.PieceEntity;
import janggiGame.Position;
import janggiGame.piece.Chariot
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

        String dynasty = "CHO";
        if (piece.hasDynasty(Dynasty.HAN)) {
            dynasty = "HAN";
        }

        return new PieceEntity(position.getRow(), position.getColumn(),
                piece.getType().name(), dynasty);
    }
}
