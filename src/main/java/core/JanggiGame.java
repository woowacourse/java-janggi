package core;

import board.Board;
import board.SangSetup;
import board.SangSetupType;
import participant.Turn;
import pieces.Side;
import position.Position;

public class JanggiGame {

    private final Board board;
    private final Turn turn;
    private final boolean isOver;

    public JanggiGame(Board board, Turn turn, boolean isOver) {
        this.board = board;
        this.turn = turn;
        this.isOver = isOver;
    }

    public JanggiGame(Board board, Turn turn) {
        this(board, turn, false);
    }

    public JanggiGame(Board board) {
        this(board, Turn.CHO_TURN);
    }

    public Board getBoard() {
        return new Board(board.pieces());
    }

    public boolean isOver() {
        return isOver;
    }

    public Side getTurnSide() {
        return turn.getSide();
    }

    public static JanggiGame of(SangSetupType choSangSetup, SangSetupType hanSangSetup) {
        return new JanggiGame(SangSetup.initialize(choSangSetup, hanSangSetup));
    }

    public JanggiGame move(Position departure, Position destination) {
        if (isOver) {
            throw new IllegalArgumentException("게임이 종료되어 더 이상 말을 이동시킬 수 없습니다.");
        }
        board.validateDeparturePiece(departure, turn);

        Board updatedBoard = board.move(departure, destination);
        Turn nextTurn = turn.next();
        boolean isOver = !updatedBoard.hasGung(nextTurn.getSide());
        return new JanggiGame(updatedBoard, nextTurn, isOver);
    }

    public Side getWinnerSide() {
        if (!isOver) {
            throw new IllegalArgumentException("게임이 종료되지 않아 승리 진영을 조회할 수 없습니다.");
        }
        return turn.prev().getSide();
    }
}
