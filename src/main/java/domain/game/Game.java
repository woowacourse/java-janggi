package domain.game;

import domain.board.Board;
import domain.piece.TeamColor;

public class Game {
    private Long id;
    private final Board board;
    private final TurnManager turnManager;
    private GameStatus status;

    public Game(Board board, TurnManager turnManager, GameStatus status) {
        this(null, board, turnManager, status);
    }

    public Game(Long id, Board board, TurnManager turnManager, GameStatus status) {
        this.id = id;
        this.board = board;
        this.turnManager = turnManager;
        this.status = status;
    }

    public Long id() {
        return id;
    }

    public void assignId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("이미 식별자가 할당된 게임입니다.");
        }
        this.id = id;
    }

    public Board board() {
        return board;
    }

    public TeamColor currentTurn() {
        return turnManager.getCurrentTurn();
    }

    public void advanceTurn() {
        turnManager.advanceTurn();
    }

    public TurnManager turnManager() {
        return turnManager;
    }

    public GameStatus status() {
        return status;
    }

    public boolean isInProgress() {
        return status == GameStatus.IN_PROGRESS;
    }

    public void finish() {
        status = GameStatus.FINISHED;
    }
}
