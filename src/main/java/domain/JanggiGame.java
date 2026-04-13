package domain;

import domain.vo.Position;
import java.util.Map;

public class JanggiGame {

    private static final int DEFAULT_TURN_COUNT = 0;

    private final Board board;
    private TurnCount turnCount;
    private boolean isSurrender;

    private JanggiGame(final Board board, final TurnCount turnCount, boolean isSurrender) {
        this.board = board;
        this.turnCount = turnCount;
        this.isSurrender = isSurrender;
    }

    public static JanggiGame of(final Board board) {
        return new JanggiGame(board, TurnCount.of(DEFAULT_TURN_COUNT), false);
    }

    public static JanggiGame of(final Board board, final int turnCount) {
        JanggiGame game = new JanggiGame(board, TurnCount.of(turnCount), false);
        game.turnCount = TurnCount.of(turnCount);

        return game;
    }

    public Team currentTurn() {
        if (turnCount.getTurnCount() % 2 == 0) {
            return Team.HAN;
        }

        return Team.CHU;
    }

    public int getTurnCount() {
        return turnCount.getTurnCount();
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

    public boolean isSurrendered() {
        return isSurrender;
    }

    public void surrender() {
        isSurrender = true;
    }

    public int calculateScore(Team team) {
        return board.calculateScore(team);
    }
}
