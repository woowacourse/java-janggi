package janggi.view.mapper;

import static janggi.domain.piece.PieceType.CANNON;
import static janggi.domain.piece.PieceType.CHARIOT;
import static janggi.domain.piece.PieceType.ELEPHANT;
import static janggi.domain.piece.PieceType.GENERAL;
import static janggi.domain.piece.PieceType.GUARD;
import static janggi.domain.piece.PieceType.HORSE;
import static janggi.domain.piece.PieceType.SOLDIER;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.EnumMap;
import java.util.Map;

public class PieceMapper {

    private static final Map<PieceType, String> pieceMap = new EnumMap<>(PieceType.class);

    static {
        pieceMap.put(CANNON, "포");
        pieceMap.put(CHARIOT, "차");
        pieceMap.put(ELEPHANT, "상");
        pieceMap.put(GENERAL, "장");
        pieceMap.put(SOLDIER, "졸");
        pieceMap.put(GUARD, "사");
        pieceMap.put(HORSE, "마");
    }

    private PieceMapper() {
    }

    public static String from(Piece piece) {
        return pieceMap.get(piece.pieceType());
    }

}
