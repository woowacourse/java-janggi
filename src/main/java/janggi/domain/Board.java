package janggi.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> piecePosition;

    private Board(Map<Position, Piece> piecePosition) {
        this.piecePosition = Collections.unmodifiableMap(new HashMap<>(piecePosition));
    }

    public static Board initialize() {
        Map<Position, Piece> initialBoard = new HashMap<>();
        initSide(initialBoard, Side.HAN, 0, 1, 2, 3, PieceType.HAN_SOLDIER, "한", "병");
        initSide(initialBoard, Side.CHO, 9, 8, 7, 6, PieceType.CHO_SOLDIER, "초", "졸");
        return new Board(initialBoard);
    }

    private static void initSide(Map<Position, Piece> initialBoard, Side side, int bR, int gR, int pR, int sR, PieceType st, String gn, String sn) {
        initMajorPieces(initialBoard, side, bR);
        initPalaceAndCannons(initialBoard, side, gR, pR, gn);
        initSoldiers(initialBoard, side, sR, st, sn);
    }

    private static void initMajorPieces(Map<Position, Piece> initialBoard, Side side, int r) {
        put(initialBoard, r, 0, side, PieceType.CHARIOT, "차０");
        put(initialBoard, r, 8, side, PieceType.CHARIOT, "차１");
        put(initialBoard, r, 1, side, PieceType.ELEPHANT, "상０");
        put(initialBoard, r, 7, side, PieceType.ELEPHANT, "상１");
        put(initialBoard, r, 2, side, PieceType.HORSE, "마０");
        put(initialBoard, r, 6, side, PieceType.HORSE, "마１");
        put(initialBoard, r, 3, side, PieceType.GUARD, "사０");
        put(initialBoard, r, 5, side, PieceType.GUARD, "사１");
    }

    private static void initPalaceAndCannons(Map<Position, Piece> initialBoard, Side side, int gR, int pR, String gN) {
        put(initialBoard, gR, 4, side, PieceType.PALACE, gN + "０");
        put(initialBoard, pR, 1, side, PieceType.CANNON, "포０");
        put(initialBoard, pR, 7, side, PieceType.CANNON, "포１");
    }

    private static void initSoldiers(Map<Position, Piece> initialBoard, Side side, int r, PieceType t, String n) {
        for (int i = 0; i < 5; i++) {
            put(initialBoard, r, i * 2, side, t, n + (char)('０' + i));
        }
    }

    private static void put(Map<Position, Piece> initialBoard, int r, int c, Side s, PieceType t, String n) {
        initialBoard.put(new Position(r, c), new Piece(s, t, n));
    }
}
f