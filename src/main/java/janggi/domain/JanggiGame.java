package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.dto.PieceDto;
import java.util.Map;
import java.util.Optional;

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

    public JanggiGame(final Board board,
                      final Player redPlayer,
                      final Player greenPlayer,
                      Team turn,
                      GameStatus gameStatus) {
        this.board = board;
        this.redPlayer = redPlayer;
        this.greenPlayer = greenPlayer;
        this.turn = turn;
        this.gameStatus = gameStatus;
    }

    public PieceDto moveByPlayer(final Position departure, final Position destination) {
        Optional<Piece> removed = Optional.empty();
        if (turn.isRed()) {
            removed = board.movePiece(redPlayer, greenPlayer, departure, destination);
        }
        if (turn.isGreen()) {
            removed = board.movePiece(greenPlayer, redPlayer, departure, destination);
        }
        checkWinCondition();
        changeTurn(this.turn);
        return new PieceDto(board.getPiece(destination), removed);
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

    public Score getScore(Team team) {
        if (team.isRed()) {
            return redPlayer.getScore();
        }
        return greenPlayer.getScore();
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getGreenPlayer() {
        return greenPlayer;
    }

    public Team getTurn() {
        return turn;
    }

    public Board getBoard() {
        return board;
    }
}
