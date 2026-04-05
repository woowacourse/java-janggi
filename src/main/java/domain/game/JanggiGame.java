package domain.game;

import domain.board.Board;
import domain.board.SangSetup;
import domain.game.exception.InvalidTurnException;
import domain.pieces.Side;
import domain.position.Position;

public class JanggiGame {
    private Board board;
    private Turn currentTurn = Turn.start();

    public JanggiGame(Board board) {
        this.board = board;
    }

    public static JanggiGame of(SangSetup choSangSetup, SangSetup hanSangSetup) {
        Board choBoard = choSangSetup.initialize(Side.CHO);
        Board hanBoard = hanSangSetup.initialize(Side.HAN);
        return new JanggiGame(choBoard.merge(hanBoard));
    }

    public void move(Position departure, Position destination) {
        if (currentTurn.isNotCurrentTurnPiece(board.pieces().get(departure))) {
            throw new InvalidTurnException(currentTurn.errorMessage());
        }
        board = board.move(departure, destination);
        currentTurn = currentTurn.next();
    }

    public Side currentTurn() {
        return currentTurn.side();
    }

    public Board board() {
        return board;
    }
}
