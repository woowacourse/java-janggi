package domain.game;

import domain.board.AbstractBoardFactory;
import domain.board.Board;
import domain.board.FormationType;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class JanggiGame {
    private Turn turn;
    private final Board board;

    public JanggiGame(Turn turn, Board board) {
        this.turn = turn;
        this.board = board;
    }

    public static JanggiGame of(FormationType choFormation, FormationType hanFormation) {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.putAll(AbstractBoardFactory.from(choFormation)
                .createFormation(Team.CHO));
        pieces.putAll(AbstractBoardFactory.from(hanFormation)
                .createFormation(Team.HAN));
        return new JanggiGame(Turn.first(),new Board(pieces));
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
