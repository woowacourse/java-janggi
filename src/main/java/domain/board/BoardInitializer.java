package domain.board;

import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardInitializer {
    private static final int CHO_BASE_ROW = 0;
    private static final int CHO_GENERAL_ROW = CHO_BASE_ROW + 1;
    private static final int CHO_CANNON_ROW = CHO_BASE_ROW + 2;
    private static final int CHO_SOLDIER_ROW = CHO_BASE_ROW + 3;

    private static final int HAN_BASE_ROW = 9;
    private static final int HAN_GENERAL_ROW = HAN_BASE_ROW - 1;
    private static final int HAN_CANNON_ROW = HAN_BASE_ROW - 2;
    private static final int HAN_SOLDIER_ROW = HAN_BASE_ROW - 3;

    private static final int GENERAL_COLUMN = 4;
    private static final List<Integer> GUARD_COLUMNS = List.of(3, 5);
    private static final List<Integer> CHARIOT_COLUMNS = List.of(0, 8);
    private static final List<Integer> CANNON_COLUMNS = List.of(1, 7);
    private static final List<Integer> HORSE_AND_ELEPHANT_COLUMNS = List.of(1, 2, 6, 7);
    private static final List<Integer> SOLDIER_COLUMNS = List.of(0, 2, 4, 6, 8);

    public static Map<Position, Piece> init(BoardSetting boardSetting) {
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.putAll(createGeneral());
        pieces.putAll(createGuard());
        pieces.putAll(createChariot());
        pieces.putAll(createCannon());
        pieces.putAll(createHorseAndElephant(boardSetting));
        pieces.putAll(createSoldier());

        return pieces;
    }

    private static Map<Position, Piece> createGeneral() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(GENERAL_COLUMN, CHO_GENERAL_ROW), Piece.of(Camp.CHO, PieceType.GENERAL));
        pieces.put(new Position(GENERAL_COLUMN, HAN_GENERAL_ROW), Piece.of(Camp.HAN, PieceType.GENERAL));

        return pieces;
    }

    private static Map<Position, Piece> createGuard() {
        Map<Position, Piece> pieces = new HashMap<>();
        for (Integer column : GUARD_COLUMNS) {
            pieces.put(new Position(column, CHO_BASE_ROW), Piece.of(Camp.CHO, PieceType.GUARD));
            pieces.put(new Position(column, HAN_BASE_ROW), Piece.of(Camp.HAN, PieceType.GUARD));
        }

        return pieces;
    }

    private static Map<Position, Piece> createChariot() {
        Map<Position, Piece> pieces = new HashMap<>();
        for (Integer column : CHARIOT_COLUMNS) {
            pieces.put(new Position(column, CHO_BASE_ROW), Piece.of(Camp.CHO, PieceType.CHARIOT));
            pieces.put(new Position(column, HAN_BASE_ROW), Piece.of(Camp.HAN, PieceType.CHARIOT));
        }

        return pieces;
    }

    private static Map<Position, Piece> createCannon() {
        Map<Position, Piece> pieces = new HashMap<>();
        for (Integer column : CANNON_COLUMNS) {
            pieces.put(new Position(column, CHO_CANNON_ROW), Piece.of(Camp.CHO, PieceType.CANNON));
            pieces.put(new Position(column, HAN_CANNON_ROW), Piece.of(Camp.HAN, PieceType.CANNON));
        }

        return pieces;
    }

    private static Map<Position, Piece> createHorseAndElephant(BoardSetting boardSetting) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (int i = 0; i < boardSetting.piecesArrangement().size(); i++) {
            pieces.put(new Position(HORSE_AND_ELEPHANT_COLUMNS.get(i), CHO_BASE_ROW), Piece.of(Camp.CHO, boardSetting.piecesArrangement().get(i)));
            pieces.put(new Position(HORSE_AND_ELEPHANT_COLUMNS.get(i), HAN_BASE_ROW), Piece.of(Camp.HAN, boardSetting.piecesArrangement().get(i)));
        }

        return pieces;
    }

    private static Map<Position, Piece> createSoldier() {
        Map<Position, Piece> pieces = new HashMap<>();
        for (Integer column : SOLDIER_COLUMNS) {
            pieces.put(new Position(column, CHO_SOLDIER_ROW), Piece.of(Camp.CHO, PieceType.SOLDIER));
            pieces.put(new Position(column, HAN_SOLDIER_ROW), Piece.of(Camp.HAN, PieceType.SOLDIER));
        }

        return pieces;
    }
}
