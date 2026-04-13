package domain.game;

import domain.board.Board;
import domain.board.MoveResult;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.TeamColor;

public class Game {
    private final Board board;
    private final TurnManager turnManager;
    private GameStatus status;

    public Game(Board board, TurnManager turnManager, GameStatus status) {
        this.board = board;
        this.turnManager = turnManager;
        this.status = status;
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

    public TurnResult move(Piece piece, Position destination) {
        final TeamColor movingTeam = currentTurn();
        final MoveResult moveResult = board.move(piece, destination);

        if (moveResult.capturedKing()) {
            finish();
            return TurnResult.finished(moveResult, movingTeam);
        }

        advanceTurn();
        return TurnResult.inProgress(moveResult);
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
