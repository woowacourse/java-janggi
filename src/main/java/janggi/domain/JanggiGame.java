package janggi.domain;

import janggi.domain.piece.Piece;
import java.util.Map;

public class JanggiGame {

    private final Board board;
    private final Player redPlayer;
    private final Player greenPlayer;
    private Team turn;
    private GameStatus gameStatus;

    public JanggiGame(final Board board, final Player redPlayer, final Player greenPlayer) {
        this.board = board;
        this.redPlayer = redPlayer;
        this.greenPlayer = greenPlayer;
        this.turn = Team.GREEN;
        this.gameStatus = GameStatus.CONTINUE;
    }

    public void moveByPlayer(final Position departure, final Position destination) {
        if (turn.isRed()) {
            board.movePiece(redPlayer, departure, destination);
        }
        if (turn.isGreen()) {
            board.movePiece(greenPlayer, departure, destination);
        }
        checkWinCondition();
        changeTurn(this.turn);
    }

    private void changeTurn(final Team currentTurn) {
        if (gameStatus == GameStatus.CONTINUE) {
            turn = currentTurn.getEnemy();
        }
    }

    public void checkWinCondition() {
        GameStatus status = board.checkGeneralDied();
        if (status == GameStatus.CONTINUE) {
            status = board.checkRemainOnlyGeneral();
        }
        this.gameStatus = status;
    }

    public boolean isContinue() {
        return this.gameStatus == GameStatus.CONTINUE;
    }

    public Map<Position, Piece> getPositionToPiece() {
        return board.getPositionToPiece();
    }

    public Player getCurrentPlayer() {
        if (turn.isRed()) {
            return redPlayer;
        }
        return greenPlayer;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
