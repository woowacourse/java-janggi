package dto;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;

public record PieceNameDto(String displayName, boolean isCho) {

    private static final Map<PieceType, String> CHO_PIECE_NAMES = Map.ofEntries(
            Map.entry(PieceType.GENERAL, "楚"),
            Map.entry(PieceType.GUARD, "士"),
            Map.entry(PieceType.HORSE, "馬"),
            Map.entry(PieceType.ELEPHANT, "象"),
            Map.entry(PieceType.SOLDIER, "卒"),
            Map.entry(PieceType.CANNON, "砲"),
            Map.entry(PieceType.CHARIOT, "車")
    );

    private static final Map<PieceType, String> HAN_PIECE_NAMES = Map.ofEntries(
            Map.entry(PieceType.GENERAL, "楚"),
            Map.entry(PieceType.GUARD, "士"),
            Map.entry(PieceType.HORSE, "馬"),
            Map.entry(PieceType.ELEPHANT, "象"),
            Map.entry(PieceType.SOLDIER, "兵"),
            Map.entry(PieceType.CANNON, "炮"),
            Map.entry(PieceType.CHARIOT, "車")
    );

    public static PieceNameDto from(final Piece piece) {
        PieceType pieceType = piece.getPieceType();
        if (piece.isChoPiece()) {
            return new PieceNameDto(CHO_PIECE_NAMES.get(pieceType), true);
        }
        return new PieceNameDto(HAN_PIECE_NAMES.get(pieceType), false);
    }
}
