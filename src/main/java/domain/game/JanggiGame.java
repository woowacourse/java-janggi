package domain.game;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.FormationType;
import domain.position.Position;

public class JanggiGame {
    private Turn turn;
    private final Board board;

    public JanggiGame(Turn turn, Board board) {
        this.turn = turn;
        this.board = board;
    }

    public static JanggiGame of(FormationType choFormation, FormationType hanFormation) {
        return new JanggiGame(Turn.first(), BoardFactory.create(choFormation, hanFormation));
    }

    public void move(Position source, Position destination) {
        board.move(source, destination);
        turn = turn.next();
    }

    public boolean isRunning() {
        return true; // TODO: 종료 조건 구현
    }

    public Team currentTurn() {
        return turn.current();
    }

    public Board getBoard() {
        return board;
    }
}
