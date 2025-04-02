package service.converter;

import dao.PieceInfo;
import janggiGame.Position;
import janggiGame.piece.Piece;
import janggiGame.piece.character.Dynasty;
import java.util.List;
import java.util.Map;

public class BoardConverter {
    public static List<PieceInfo> convertToPieceInfos(final Map<Position, Piece> survivedPieces) {
        return survivedPieces.entrySet().stream()
                .map(BoardConverter::convertToPieceInfo)
                .toList();
    }

    private static PieceInfo convertToPieceInfo(Map.Entry<Position, Piece> survivedPieces) {
        Position position = survivedPieces.getKey();
        Piece piece = survivedPieces.getValue();

        String dynasty = Dynasty.CHO.name();
        if (piece.hasDynasty(Dynasty.HAN)) {
            dynasty = Dynasty.HAN.name();
        }

        return new PieceInfo(position.getRow(), position.getColumn(),
                piece.getType().name(), dynasty);
    }
}
