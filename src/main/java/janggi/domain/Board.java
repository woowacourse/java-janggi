package janggi.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> piecePosition;

    private Board(Map<Position, Piece> piecePosition) {
        this.piecePosition = new HashMap<>(piecePosition);
    }

    public static Board initialize() {
        Map<Position, Piece> initialBoard = new HashMap<>();
        initSide(initialBoard, Side.HAN, 0, 1, 2, 3, PieceType.HAN_SOLDIER);
        initSide(initialBoard, Side.CHO, 9, 8, 7, 6, PieceType.CHO_SOLDIER);
        return new Board(initialBoard);
    }

    private static void initSide(Map<Position, Piece> initialBoard, Side side, int bR, int gR, int pR, int sR, PieceType st) {
        initMajorPieces(initialBoard, side, bR);
        initPalaceAndCannons(initialBoard, side, gR, pR);
        initSoldiers(initialBoard, side, sR, st);
    }

    private static void initMajorPieces(Map<Position, Piece> initialBoard, Side side, int r) {
        put(initialBoard, r, 0, side, PieceType.CHARIOT, "0");
        put(initialBoard, r, 8, side, PieceType.CHARIOT, "1");
        put(initialBoard, r, 1, side, PieceType.ELEPHANT, "0");
        put(initialBoard, r, 7, side, PieceType.ELEPHANT, "1");
        put(initialBoard, r, 2, side, PieceType.HORSE, "0");
        put(initialBoard, r, 6, side, PieceType.HORSE, "1");
        put(initialBoard, r, 3, side, PieceType.GUARD, "0");
        put(initialBoard, r, 5, side, PieceType.GUARD, "1");
    }

    private static void initPalaceAndCannons(Map<Position, Piece> initialBoard, Side side, int gR, int pR) {
        put(initialBoard, gR, 4, side, PieceType.PALACE, "0");
        put(initialBoard, pR, 1, side, PieceType.CANNON, "0");
        put(initialBoard, pR, 7, side, PieceType.CANNON, "1");
    }

    private static void initSoldiers(Map<Position, Piece> initialBoard, Side side, int r, PieceType t) {
        for (int i = 0; i < 5; i++) {
            put(initialBoard, r, i * 2, side, t, String.valueOf(i));
        }
    }

    private static void put(Map<Position, Piece> initialBoard, int r, int c, Side s, PieceType t, String n) {
        initialBoard.put(new Position(r, c), new Piece(s, t, n));
    }

    //    !------------임시---------------!
    public Map<Position, PieceVO> getPiecePosition() {
        Map<Position, PieceVO> piecePositions = new HashMap<>(piecePosition.size());
        this.piecePosition.forEach((position, piece) -> piecePositions.put(position, piece.toVO()));
        return Collections.unmodifiableMap(piecePositions);
    }

    public List<Position> calculateDestinations(Position currentPosition) {
        Piece piece = piecePosition.get(currentPosition);
        Paths moveablePaths = piece.calculatePaths(currentPosition);
        moveablePaths.forEach(move -> {
            System.out.println(move.toString());
        });
        Map<Position, PieceVO> boardState = generateStateByPaths(moveablePaths);

        return piece.determineDestinations(moveablePaths, boardState);
    }

    private Map<Position, PieceVO> generateStateByPaths(Paths moveablePaths) {
        Map<Position, PieceVO> boardState = new HashMap<>();
        moveablePaths.forEach(path -> {
            generateStateByPath(path, boardState);
        });
        return boardState;
    }

    private void generateStateByPath(Path path, Map<Position, PieceVO> boardState) {
        path.forEach(position -> {
            Piece piece = piecePosition.get(position);
            if (piece != null) {
                boardState.put(position, piece.toVO());
            }
        });
    }

    public void movePiece(Position selected, Position target) {
        Piece movingPiece = piecePosition.remove(selected);
        piecePosition.put(target, movingPiece);
    }
}
