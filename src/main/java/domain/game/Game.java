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
    private GameState gameState;

    public Game(int choSetUp, int hanSetUp) {
        this.board = new Board(BoardInitializer.init(choSetUp, hanSetUp));
        this.currentTurn = Camp.CHO;
        this.gameState = new InProgress();
    }

    public Game(Board board, Camp currentTurn, GameState restoredState) {
        this.board = board;
        this.currentTurn = currentTurn;
        this.gameState = restoredState;
    }

    public Board board() {
        return board;
    }

    public Camp currentTurn() {
        return currentTurn;
    }

    public void move(Position from, Position to) {
        gameState.validateMovable();

        Piece piece = board.findBy(from);
        validateTurn(piece);

        boolean isGeneralCaptured = board.isGeneral(to);

        board.move(from, to);

        updateGameIfFinished(isGeneralCaptured);
        updateGameIfDraw();

        changeTurn();
    }

    private void updateGameIfFinished(boolean isGeneralCaptured) {
        if (isGeneralCaptured) {
            this.gameState = new Finished(currentTurn);
        }
    }

    private void updateGameIfDraw() {
        if (board.isOnlyGeneralAndGuard()) {
            double choScore = board.calculateScore(Camp.CHO);
            double hanScore = board.calculateScore(Camp.HAN);

            checkWinCamp(choScore, hanScore);
        }
    }

    private void checkWinCamp(double choScore, double hanScore) {
        if (choScore > hanScore) {
            this.gameState = new Finished(Camp.CHO);
            return;
        }
        this.gameState = new Finished(Camp.HAN);
    }

    public boolean isNotEnoughPieces() {
        return board.isOnlyGeneralAndGuard();
    }

    public double choScore() {
        return board.calculateScore(Camp.CHO);
    }

    public double hanScore() {
        return board.calculateScore(Camp.HAN);
    }

    public boolean isFinished() {
        return gameState.isFinished();
    }

    public Camp winner() {
        return gameState.winner();
    }

    public void passTurn() {
        gameState.validateMovable();
        changeTurn();
    }

    private void validateTurn(Piece piece) {
        if (piece.camp() != currentTurn) {
            throw new IllegalArgumentException(INVALID_TURN_ERROR_MESSAGE);
        }
    }

    private void changeTurn() {
        this.currentTurn = this.currentTurn.nextTurn();
    }
}
