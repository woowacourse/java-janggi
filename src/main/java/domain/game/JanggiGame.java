package domain.game;

import domain.board.Board;
import domain.board.SangSetup;
import domain.game.exception.GameErrorMessage;
import domain.game.exception.InvalidTurnException;
import domain.pieces.Side;
import domain.position.Position;

public class JanggiGame {
    private final Board board;
    private boolean isChoTurn = true;

    public JanggiGame(Board board) {
        this.board = board;
    }

    public static JanggiGame of(SangSetup choSangSetup, SangSetup hanSangSetup) {
        Board choBoard = choSangSetup.initialize(Side.CHO);
        Board hanBoard = hanSangSetup.initialize(Side.HAN);
        return new JanggiGame(choBoard.merge(hanBoard));
    }

    public void move(Position departure, Position destination, Side side) {
        if (isChoTurn && side.isHan()) {
            throw new InvalidTurnException(GameErrorMessage.CHO_TURN);
        }
        if (!isChoTurn && side.isCho()) {
            throw new InvalidTurnException(GameErrorMessage.HAN_TURN);
        }
        board.move(departure, destination);
        isChoTurn = !isChoTurn;
    }

    public Side currentTurn() {
        if (isChoTurn) {
            return Side.CHO;
        }
        return Side.HAN;
    }

    public Board board() {
        return board;
    }
}
