package domain.board;

import domain.Position;
import domain.Side;
import domain.piece.EmptyPiece;
import domain.piece.Piece;

import java.util.Arrays;
import java.util.Map;

public class Board {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;
    private static final int POSITION_THRESHOLD = 0;

    private final Piece[][] board = new Piece[COL_SIZE][ROW_SIZE];
    private Side turn;

    public Board(BoardInitializer boardInitializer) {
        for (int i = 0; i < COL_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                Map<Position, Piece> initializedPosition = boardInitializer.initialize();
                board[i][j] = initializedPosition.getOrDefault(new Position(i, j), new EmptyPiece());
            }
        }
        turn = boardInitializer.getFirstTurnSide();
    }

    public boolean isPieceAt(Position position, Piece piece) {
        return board[position.col()][position.row()].equals(piece);
    }

    public Piece getPieceBy(Position start) {
        validateRange(start);
        validateStartPosition(start);
        return board[start.col()][start.row()];
    }

    private boolean isOpponentTurnPiece(Position position) {
        return !board[position.col()][position.row()].isFriendly(turn);
    }

    public boolean isAvailableDestination(Position destination) {
        if (destination.col() < POSITION_THRESHOLD || destination.col() >= COL_SIZE) {
            return false;
        }

        if (destination.row() < POSITION_THRESHOLD || destination.row() >= ROW_SIZE) {
            return false;
        }

        return isOpponentTurnPiece(destination);
    }

    public void move(Position start, Position destination) {
        validateStartPosition(start);
//        validateDestination(destination);

        board[destination.col()][destination.row()] = board[start.col()][start.row()];
        board[start.col()][start.row()] = new EmptyPiece();

        endTurn();
    }

    private void endTurn() {
        turn = turn.change();
    }

    private void validateRange(Position position) {
        if (position.col() < POSITION_THRESHOLD || position.col() >= COL_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 열 좌표: %d (열 좌표는 0 에서 9 사이여야 합니다.)", position.col()));
        }

        if (position.row() < POSITION_THRESHOLD || position.row() >= ROW_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 행 좌표: %d (행 좌표는 0 에서 8 사이여야 합니다.)", position.row()));
        }
    }

    private void validateStartPosition(Position start) {
        if (isOpponentTurnPiece(start)) {
            throw new IllegalArgumentException("아군 기물만 이동 가능합니다.");
        }
    }

    private void validateCurrentTurnPiece(Position destination) {
        if (!isOpponentTurnPiece(destination)) {
            throw new IllegalArgumentException("아군 기물이 있는 위치는 이동할 수 없습니다.");
        }
    }

    public Piece[][] getBoard() {
        return Arrays.copyOf(board, board.length);
    }
}
