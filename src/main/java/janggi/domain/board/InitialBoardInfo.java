package janggi.domain.board;

import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum InitialBoardInfo {
    HAN(Side.HAN, 0, 1, 2, 3),
    CHO(Side.CHO, 9, 8, 7, 6),
    ;

    private static final List<Integer> SOLDIER_COLUMNS = List.of(0, 2, 4, 6, 8);
    private static final List<Integer> CHARIOT_COLUMNS = List.of(0, 8);
    private static final List<Integer> CANNON_COLUMNS = List.of(1, 7);
    private static final List<Integer> ELEPHANT_COLUMNS = List.of(1, 7);
    private static final List<Integer> HORSE_COLUMNS = List.of(2, 6);
    private static final List<Integer> GUARD_COLUMNS = List.of(3, 5);
    private static final List<Integer> PALACE_COLUMNS = List.of(4);

    private final Side side;
    private final int bottomRow;
    private final int palaceRow;
    private final int cannonRow;
    private final int soldierRow;

    InitialBoardInfo(Side side, int bottomRow, int palaceRow, int cannonRow, int soldierRow) {
        this.side = side;
        this.bottomRow = bottomRow;
        this.palaceRow = palaceRow;
        this.cannonRow = cannonRow;
        this.soldierRow = soldierRow;
    }

    public Map<Position, Piece> generateInitialPiecePositions() {
        Map<Position, Piece> initialBoard = new HashMap<>();
        putPieces(initialBoard, soldierRow, SOLDIER_COLUMNS, PieceType.SOLDIER);
        putPieces(initialBoard, cannonRow, CANNON_COLUMNS, PieceType.CANNON);
        putPieces(initialBoard, bottomRow, CHARIOT_COLUMNS, PieceType.CHARIOT);
        putPieces(initialBoard, bottomRow, ELEPHANT_COLUMNS, PieceType.ELEPHANT);
        putPieces(initialBoard, bottomRow, HORSE_COLUMNS, PieceType.HORSE);
        putPieces(initialBoard, bottomRow, GUARD_COLUMNS, PieceType.GUARD);
        putPieces(initialBoard, palaceRow, PALACE_COLUMNS, PieceType.PALACE);
        return Map.copyOf(initialBoard);
    }

    private void putPieces(Map<Position, Piece> board, int row, List<Integer> columns, PieceType type) {
        for (int currentColumn = 0; currentColumn < columns.size(); currentColumn++) {
            int column = columns.get(currentColumn);
            String pieceNumber = String.valueOf(currentColumn);
            board.put(new Position(row, column), new Piece(side, type, pieceNumber));
        }
    }
}
