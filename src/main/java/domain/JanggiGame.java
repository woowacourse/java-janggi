package domain;

import domain.vo.Position;
import java.util.Map;

public class JanggiGame {

    private final Board board;
    private TurnCount turnCount;

    private JanggiGame(final Board board) {
        this.board = board;
        this.turnCount = TurnCount.of(0);
    }

    public static JanggiGame of(final Board board) {
        return new JanggiGame(board);
    }

    public Team currentTurn() {
        if (turnCount.getTurnCount() % 2 == 0) {
            return Team.HAN;
        }

        return Team.CHU;
    }

    public void passTheTurn() {
        turnCount = TurnCount.of(turnCount.getTurnCount() + 1);
    }

    public void move(Position from, Position to) {
        board.move(from, to, currentTurn());
    }

    public Map<Position, Piece> getBoardStatus() {
        return board.getBoard();
    }
}
