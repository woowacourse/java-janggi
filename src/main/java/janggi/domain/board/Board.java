package janggi.domain.board;

import janggi.domain.PieceInfo;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.List;
import java.util.Map;

public class Board implements BoardInterface {
    public static final int BOARD_START_ROWS = 1;
    public static final int BOARD_START_COLS = 1;
    public static final int ARRAY_INDEX_OFFSET = 1;
    public static final int BOARD_END_ROWS = 10;
    public static final int BOARD_END_COLS = 9;

    private static final String INVALID_PIECE_SIDE_MESSAGE = "자기 진영의 기물만 움직일 수 있습니다.";
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    @Override
    public boolean isEmpty(Position position) {
        return board.get(position).isEqualPieceType(PieceType.NONE);
    }

    @Override
    public boolean isPo(Position position) {
        return board.get(position).isEqualPieceType(PieceType.PO);
    }

    @Override
    public boolean isEnemy(Side side, Position position) {
        return !board.get(position).isEqualSide(side);
    }

    @Override
    public boolean isAlly(Side side, Position position) {
        return board.get(position).isEqualSide(side);
    }

    @Override
    public PieceInfo[][] getCurrentBoard() {
        PieceInfo[][] currentBoard = new PieceInfo[BOARD_END_ROWS][BOARD_END_COLS];

        board.forEach((position, piece) -> {
            int rowIndex = position.x() - ARRAY_INDEX_OFFSET;
            int colIndex = position.y() - ARRAY_INDEX_OFFSET;
            currentBoard[rowIndex][colIndex] = piece.getPieceInfo();
        });

        return currentBoard;
    }

    public boolean move(Position start, Position end, Side side) {
        Piece piece = board.get(start);
        if (!piece.isEqualSide(side)) {
            throw new IllegalArgumentException(INVALID_PIECE_SIDE_MESSAGE);
        }
        List<Position> route = piece.findRoute(start, end);

        piece.validateRoute(route, this);
        board.put(end, piece);
        board.put(start, new None());
        return true;
    }
}
