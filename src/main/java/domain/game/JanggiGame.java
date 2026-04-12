package domain.game;

import domain.board.Board;
import domain.board.MoveResult;
import domain.piece.Position;
import domain.player.Player;
import domain.player.Players;
import domain.player.Team;

import java.util.List;

public class JanggiGame {

    private final Board board;
    private final Players players;
    private Player currentPlayer;
    private GameStatus gameStatus;

    private static final String GAME_NOT_FINISHED = "게임이 아직 종료되지 않았습니다.";

    public JanggiGame(final Board board, final Player choPlayer, final Player hanPlayer) {
        this.board = board;
        this.players = new Players(List.of(choPlayer, hanPlayer));
        currentPlayer = choPlayer;
        gameStatus = GameStatus.PLAYING;
    }


    public void movePiece(final Position from, final Position to) {
        final MoveResult moveResult = board.move(from, to);

        if (moveResult.capturesGeneral()) {
            finish();
            return;
        }

        switchTurn();
    }


    public boolean isPlaying() {
        return gameStatus == GameStatus.PLAYING;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public Player getWinner() {
        if (gameStatus != GameStatus.FINISHED) {
            throw new IllegalStateException(GAME_NOT_FINISHED);
        }

        return currentPlayer;
    }


    private void finish() {
        gameStatus = GameStatus.FINISHED;
    }

    private void switchTurn() {
        final Team teamNextTurn = currentPlayer.getTeam().opponent();
        currentPlayer = players.findByTeam(teamNextTurn);
    }
}
