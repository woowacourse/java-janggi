package core;

import board.Board;
import board.SangSetup;
import participant.ChoTurn;
import participant.Turn;
import pieces.Side;
import position.Position;

public class JanggiGame {
    private final Board board;
    private Turn turn;

    public JanggiGame(Board board, Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public JanggiGame(Board board) {
        this(board, new ChoTurn());
    }

    public static JanggiGame of(SangSetup choSangSetup, SangSetup hanSangSetup) {
        Board choBoard = choSangSetup.initialize(Side.CHO);
        Board hanBoard = hanSangSetup.initialize(Side.HAN);
        return new JanggiGame(choBoard.merge(hanBoard));
    }

    public void move(Position departure, Position destination) {
        board.validateDeparturePieceSide(departure, turn);
        board.move(departure, destination, turn);
        turn = turn.move();
    }
}
