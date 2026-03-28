package domain.game;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;

public class Game {
    private static final String INVALID_TURN_ERROR_MESSAGE = "[ERROR] 현재 턴의 기물만 움직일 수 있습니다.";

    private final Board board;
    private Camp currentTurn;

    public Game(int choSetUp, int hanSetUp) {
        this.board = new Board(BoardInitializer.init(choSetUp, hanSetUp));
        this.currentTurn = Camp.CHO;
    }

    public Board board() {
        return board;
    }

    public Camp currentTurn() {
        return currentTurn;
    }

    public void move(Position from, Position to) {
        Piece piece = board.findBy(from);
        validateTurn(piece);

        board.move(from, to);
        changeTurn();
    }

    public void passTurn() {
        changeTurn();
    }

    private void validateTurn(Piece piece) {
        if (piece.camp() != currentTurn) {
            throw new IllegalArgumentException(INVALID_TURN_ERROR_MESSAGE);
        }
    }

    private void changeTurn() {
        if (currentTurn == Camp.CHO) {
            currentTurn = Camp.HAN;
            return;
        }

        currentTurn = Camp.CHO;
    }
}
