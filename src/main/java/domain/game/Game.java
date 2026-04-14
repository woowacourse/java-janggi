package domain.game;

import domain.board.BoardState;
import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.point.Point;
import domain.team.Team;

public class Game {
    private final JanggiBoard janggiBoard;
    private final ScoreCalculator scoreCalculator;
    private GameScore currentScore;
    private boolean isGameRunning;
    private Team turn;

    public Game(JanggiBoard janggiBoard) {
        this(
                janggiBoard,
                new ScoreCalculator().calculate(janggiBoard.boardState()),
                Team.CHO,
                janggiBoard.isGameRunning()
        );
    }

    private Game(JanggiBoard janggiBoard, GameScore currentScore, Team turn, boolean isGameRunning) {
        this.janggiBoard = janggiBoard;
        this.scoreCalculator = new ScoreCalculator();
        this.currentScore = currentScore;
        this.turn = turn;
        this.isGameRunning = isGameRunning;
    }

    public static Game restored(JanggiBoard board, GameScore score, Team turn, boolean running) {
        return new Game(board, score, turn, running);
    }

    public void processTurn(MoveCommand move) {
        Point from = move.getFrom();
        Point to = move.getTo();
        validateTurn(from);
        janggiBoard.tryToMove(from, to);
        isGameRunning = janggiBoard.isGameRunning();
        currentScore = scoreCalculator.calculate(janggiBoard.boardState());
        turn = turn.nextTurn();
    }

    private void validateTurn(Point point) {
        if (!janggiBoard.isSameTeamAt(point, turn)) {
            throw new exception.InvalidTurnException();
        }
    }

    public boolean willCaptureOpponent(Point to) {
        Intersection toIntersection = janggiBoard.findIntersection(to);
        return toIntersection.hasPiece() && !toIntersection.isSameTeam(turn);
    }

    public Team currentTurn() {
        return turn;
    }

    public boolean isRunning() {
        return isGameRunning;
    }

    public GameScore currentScore() {
        return currentScore;
    }

    public BoardState getBoardState() {
        return janggiBoard.boardState();
    }
}
