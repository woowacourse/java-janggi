package core;

import board.Board;
import board.SangSetup;
import board.SangSetupType;
import participant.Turn;
import pieces.Side;
import position.Position;

public class JanggiGame {

    private Board board;
    private Turn turn;
    private boolean isOver;

    public JanggiGame(Board board, Turn turn) {
        this.board = board;
        this.turn = turn;
        this.isOver = false;
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

    public Side turnSide() {
        return turn.side();
    }

    public static JanggiGame of(SangSetupType choSangSetup, SangSetupType hanSangSetup) {
        Board choBoard = SangSetup.initialize(choSangSetup, Side.CHO);
        Board hanBoard = SangSetup.initialize(hanSangSetup, Side.HAN);
        return new JanggiGame(choBoard.merge(hanBoard));
    }

    public JanggiGame move(Position departure, Position destination) {
        board.validateDeparturePiece(departure, turn);
        board = board.move(departure, destination);
        turn = turn.move();
        // TODO: 장군이 잡히면 isOver = true 초기화 (사이클2)
        return new JanggiGame(board, turn);
    }
}
