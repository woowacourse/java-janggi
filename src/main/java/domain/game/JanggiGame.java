package domain.game;

import domain.board.Board;
import domain.board.SangSetup;
import domain.game.exception.GameErrorMessage;
import domain.game.exception.InvalidTurnException;
import domain.pieces.Side;
import domain.position.Position;

public class JanggiGame {
    private Board board;
    private Side currentTurn = Side.CHO;

    public JanggiGame(Board board) {
        this.board = board;
    }

    public static JanggiGame of(SangSetup choSangSetup, SangSetup hanSangSetup) {
        Board choBoard = choSangSetup.initialize(Side.CHO);
        Board hanBoard = hanSangSetup.initialize(Side.HAN);
        return new JanggiGame(choBoard.merge(hanBoard));
    }

    public void move(Position departure, Position destination) {
        if (isNotCurrentTurnPiece(departure)) {
            throw new InvalidTurnException(currentTurnErrorMessage());
        }
        board = board.move(departure, destination);
        currentTurn = nextTurn();
    }

    private GameErrorMessage currentTurnErrorMessage() {
        if (currentTurn.isCho()) {
            return GameErrorMessage.CHO_TURN;
        }
        return GameErrorMessage.HAN_TURN;
    }

    private boolean isNotCurrentTurnPiece(Position departure) {
        if (currentTurn.isCho()) {
            return board.pieces().get(departure).isHan();
        }
        return board.pieces().get(departure).isCho();
    }

    private Side nextTurn() {
        if (currentTurn.isCho()) {
            return Side.HAN;
        }
        return Side.CHO;
    }

    public Side currentTurn() {
        return currentTurn;
    }

    public Board board() {
        return board;
    }
}
