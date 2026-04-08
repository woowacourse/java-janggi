package domain;

import constant.BoardSpec;
import domain.piece.Empty;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {

    private static final int BACK_Y = 0;
    private static final int KING_Y = 1;
    private static final int CANNON_Y = 2;
    private static final int SOLDIER_Y = 3;

    private static final List<Integer> CANNON_X = List.of(2, 8);
    private static final List<Integer> CHARIOT_X = List.of(1, 9);
    private static final List<Integer> GUARD_X = List.of(4, 6);
    private static final List<Integer> KING_X = List.of(5);
    private static final List<Integer> SOLIDER_X = List.of(1, 3, 5, 7, 9);
    private static final List<Integer> FORMATION_X = List.of(2, 3, 7, 8);

    public static Board createBoard(Formation choFormation, Formation hanFormation) {
        Map<Position, Piece> board = createEmptyBoard();
        placePieces(board, choFormation, Side.CHO);
        placePieces(board, hanFormation, Side.HAN);

        return new Board(board);
    }

    public static Board createTestBoard() {
        Map<Position, Piece> board = createEmptyBoard();
        placePiece(board, Position.of(5, 9), PieceType.KING.create(Side.CHO));
        placePiece(board, Position.of(5, 6), PieceType.KING.create(Side.HAN));
        placePiece(board, Position.of(5, 7), PieceType.SOLDIER.create(Side.CHO));
        return new Board(board);
    }

    private static void placePieces(Map<Position, Piece> board, Formation formation, Side side) {
        List<Integer> rows = getRowForSide(side);
        placePiece(board, CANNON_X, rows.get(CANNON_Y), PieceType.CANNON, side);
        placePiece(board, CHARIOT_X, rows.get(BACK_Y), PieceType.CHARIOT, side);
        placePiece(board, GUARD_X, rows.get(BACK_Y), PieceType.GUARD, side);
        placePiece(board, KING_X, rows.get(KING_Y), PieceType.KING, side);
        placePiece(board, SOLIDER_X, rows.get(SOLDIER_Y), PieceType.SOLDIER, side);
        placeFormationPiece(board, formation, FORMATION_X, rows.get(BACK_Y), side);
    }

    private static List<Integer> getRowForSide(Side side) {
        if (side == Side.HAN) {
            return List.of(1, 2, 3, 4);
        }
        return List.of(10, 9, 8, 7);
    }

    private static void placePiece(Map<Position, Piece> board, Position position, Piece piece) {
        board.put(position, piece);
    }

    private static Map<Position, Piece> createEmptyBoard() {
        Map<Position, Piece> board = new HashMap<>();
        for (int x = BoardSpec.MIN_X; x <= BoardSpec.MAX_X; x++) {
            for (int y = BoardSpec.MIN_Y; y <= BoardSpec.MAX_Y; y++) {
                placePiece(board, Position.of(x, y), new Empty());
            }
        }
        return board;
    }

    private static void placePiece(Map<Position, Piece> board, List<Integer> xPositions, int y, PieceType pieceType,
        Side side) {
        for (int x : xPositions) {
            board.put(Position.of(x, y), pieceType.create(side));
        }
    }

    private static void placeFormationPiece(Map<Position, Piece> board, Formation formation, List<Integer> xPositions,
        int y, Side side) {
        List<PieceType> pieceTypes = formation.getPieceTypes();
        for (int i = 0; i < pieceTypes.size(); i++) {
            board.put(Position.of(xPositions.get(i), y), pieceTypes.get(i).create(side));
        }
    }
}
