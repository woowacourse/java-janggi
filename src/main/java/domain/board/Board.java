package domain.board;

import domain.Position;
import domain.Side;
import domain.piece.Cannon;
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
        Map<Position, Piece> initializedPosition = boardInitializer.initialize();
        for (int i = 0; i < COL_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                board[i][j] = initializedPosition.getOrDefault(new Position(i, j), new EmptyPiece());
            }
        }
        turn = boardInitializer.getFirstTurnSide();
    }

    public boolean isEmpty(Position position) {
        return board[position.col()][position.row()].isNeutral();
    }

    public boolean isCannon(Position position) {
        Piece piece = board[position.col()][position.row()];
        return piece.equals(new Cannon(Side.HAN)) || piece.equals(new Cannon(Side.CHU));
    }

    public Piece getPieceBy(Position start) {
        validateStartPosition(start);
        return board[start.col()][start.row()];
    }

    public boolean isAvailableDestination(Position destination) {
        if (isInvalidRange(destination)) {
            return false;
        }

        return isNotFriendlyPiece(destination);
    }

    public boolean isInvalidRange(Position destination) {
        if (destination.col() < POSITION_THRESHOLD || destination.col() >= COL_SIZE) {
            return true;
        }
        return destination.row() < POSITION_THRESHOLD || destination.row() >= ROW_SIZE;
    }

    public boolean isOpponentPiece(Position position) {
        return isNotFriendlyPiece(position) && !isEmpty(position);
    }

    public void move(Position start, Position destination) {
        board[destination.col()][destination.row()] = board[start.col()][start.row()];
        board[start.col()][start.row()] = new EmptyPiece();

        endTurn();
    }

    public void validateStartPosition(Position position) {
        validateRange(position);
        validateCurrentTurnPiece(position);
    }

    public Side getTurn() {
        return turn;
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

    private boolean isNotFriendlyPiece(Position position) {
        return !board[position.col()][position.row()].isFriendly(turn);
    }

    private void validateCurrentTurnPiece(Position start) {
        if (isNotFriendlyPiece(start)) {
            throw new IllegalArgumentException("아군 기물만 이동 가능합니다.");
        }
    }

    public Piece[][] getBoard() {
        return Arrays.copyOf(board, board.length);
    }
}
