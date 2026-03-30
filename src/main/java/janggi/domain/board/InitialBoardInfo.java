package janggi.domain.board;

import static janggi.domain.board.Position.BOARD_MAX_COLUMN;
import static janggi.domain.board.Position.BOARD_MIN_COLUMN;

import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public enum InitialBoardInfo {
    HAN(Side.HAN, 0, 1, 2, 3, PieceType.HAN_SOLDIER),
    CHO(Side.CHO, 9, 8, 7, 6, PieceType.CHO_SOLDIER),
    ;

    private static final int SOLDIER_COUNT = 5;
    private static final int MAJOR_PIECE_COUNT = 2;
    private static final int SOLDIER_TERM = 2;
    private static final int PALACE_COLUMN = 4;
    private static final List<Integer> CHARIOT_COLUMN = List.of(0, 8);
    private static final List<Integer> ELEPHANT_COLUMN = List.of(1, 7);
    private static final List<Integer> HORSE_COLUMN = List.of(2, 6);
    private static final List<Integer> GUARD_COLUMN = List.of(3, 5);
    private static final List<Integer> CANNON_COLUMN = List.of(1, 7);


    final Side side;
    final int bottomRow;
    final int palaceRow;
    final int cannonRow;
    final int soldierRow;
    final PieceType soldierType;

    InitialBoardInfo(Side side, int bottomRow, int palaceRow, int cannonRow, int soldierRow, PieceType soldierType) {
        this.side = side;
        this.bottomRow = bottomRow;
        this.palaceRow = palaceRow;
        this.cannonRow = cannonRow;
        this.soldierRow = soldierRow;
        this.soldierType = soldierType;
    }

    public Map<Position, Piece> generateInitialPiecePositions() {
        Map<Position, Piece> initialBoard = new HashMap<>();
        initSoldiers(initialBoard);
        initCannon(initialBoard);
        initChariot(initialBoard);
        initElephant(initialBoard);
        initHorse(initialBoard);
        initGuard(initialBoard);
        initPalace(initialBoard);
        return Map.copyOf(initialBoard);
    }

    private void initSoldiers(Map<Position, Piece> initialBoard) {
        for (int soldierColumn = BOARD_MIN_COLUMN; soldierColumn <= BOARD_MAX_COLUMN; soldierColumn += SOLDIER_TERM) {
            putPiece(initialBoard, soldierRow, soldierColumn, this.soldierType,
                    String.valueOf(soldierColumn / SOLDIER_TERM));
        }
    }

    private void initCannon(Map<Position, Piece> initialBoard) {
        AtomicInteger pieceCount = new AtomicInteger();
        CANNON_COLUMN
                .forEach(cannonColumn -> {
                    putPiece(initialBoard, cannonRow, cannonColumn, PieceType.CANNON, String.valueOf(pieceCount.get()));
                    pieceCount.addAndGet(1);
                });
        for (int count = 0; count < MAJOR_PIECE_COUNT; count++) {

        }

        putPiece(initialBoard, cannonRow, 7, PieceType.CANNON, "1");
    }

    private void initChariot(Map<Position, Piece> initialBoard) {
        putPiece(initialBoard, bottomRow, 0, PieceType.CHARIOT, "0");
        putPiece(initialBoard, bottomRow, 8, PieceType.CHARIOT, "1");
    }

    private void initPalace(Map<Position, Piece> initialBoard) {
        putPiece(initialBoard, palaceRow, 4, PieceType.PALACE, "0");
    }

    private void initElephant(Map<Position, Piece> initialBoard) {
        putPiece(initialBoard, bottomRow, 1, PieceType.ELEPHANT, "0");
        putPiece(initialBoard, bottomRow, 7, PieceType.ELEPHANT, "1");
    }

    private void initHorse(Map<Position, Piece> initialBoard) {
        putPiece(initialBoard, bottomRow, 2, PieceType.HORSE, "0");
        putPiece(initialBoard, bottomRow, 6, PieceType.HORSE, "1");
    }

    private void initGuard(Map<Position, Piece> initialBoard) {
        putPiece(initialBoard, bottomRow, 3, PieceType.GUARD, "0");
        putPiece(initialBoard, bottomRow, 5, PieceType.GUARD, "1");
    }

    private void putPiece(Map<Position, Piece> targetMap, int row, int column, PieceType type, String number) {
        targetMap.put(new Position(row, column), new Piece(this.side, type, number));
    }
}
