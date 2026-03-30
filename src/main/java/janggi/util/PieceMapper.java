package janggi.util;

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

    public static final String CANNON_DISPLAY_NAME = "포";
    public static final String CHARIOT_DISPLAY_NAME = "차";
    public static final String ELEPHANT_DISPLAY_NAME = "상";
    public static final String GENERAL_DISPLAY_NAME = "장";
    public static final String GUARD_DISPLAY_NAME = "사";
    public static final String HORSE_DISPLAY_NAME = "마";
    public static final String SOLDIER_DISPLAY_NAME = "졸";

    private static final Map<PieceType, String> pieceMap = new EnumMap<>(PieceType.class);

    static {
        pieceMap.put(CANNON, CANNON_DISPLAY_NAME);
        pieceMap.put(CHARIOT, CHARIOT_DISPLAY_NAME);
        pieceMap.put(ELEPHANT, ELEPHANT_DISPLAY_NAME);
        pieceMap.put(GENERAL, GENERAL_DISPLAY_NAME);
        pieceMap.put(GUARD, GUARD_DISPLAY_NAME);
        pieceMap.put(HORSE, HORSE_DISPLAY_NAME);
        pieceMap.put(SOLDIER, SOLDIER_DISPLAY_NAME);
    }

    private PieceMapper() {
    }

    public static String from(Piece piece) {
        return pieceMap.get(piece.pieceType());
    }

}
