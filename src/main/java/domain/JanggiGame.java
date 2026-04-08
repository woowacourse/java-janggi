package domain;

import domain.vo.Position;
import java.util.Map;

public class JanggiGame {

    private static final int DEFAULT_TURN_COUNT = 0;

    private final Board board;
    private TurnCount turnCount;

    private JanggiGame(final Board board) {
        this.board = board;
        this.turnCount = TurnCount.of(DEFAULT_TURN_COUNT);
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

    public boolean isFinished() {
        return !board.isGeneralAlive(Team.CHU) || !board.isGeneralAlive(Team.HAN);
    }
}
