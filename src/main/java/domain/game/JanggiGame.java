package domain.game;

import domain.board.Board;
import domain.board.MoveResult;
import domain.piece.Position;
import domain.player.Player;
import domain.player.Players;
import domain.player.Team;

import java.util.List;

public class JanggiGame {

    private final long gameId;
    private final Board board;
    private final Players players;
    private Player currentPlayer;
    private GameStatus gameStatus;

    private static final String GAME_NOT_FINISHED = "게임이 아직 종료되지 않았습니다.";

    private JanggiGame(
            final long gameId,
            final Board board,
            final Player choPlayer,
            final Player hanPlayer,
            final Player currentPlayer,
            final GameStatus gameStatus
    ) {
        this.gameId = gameId;
        this.board = board;
        this.players = new Players(List.of(choPlayer, hanPlayer));
        this.currentPlayer = currentPlayer;
        this.gameStatus = gameStatus;
    }

    public static JanggiGame newGame(
            final long gameId,
            final Board board,
            final Player choPlayer,
            final Player hanPlayer
    ) {
        return new JanggiGame(
                gameId,
                board,
                choPlayer,
                hanPlayer,
                choPlayer,
                GameStatus.PLAYING
        );
    }

    public static JanggiGame loadGame(
            final long gameId,
            final Board board,
            final Player choPlayer,
            final Player hanPlayer,
            final Player currentPlayer,
            final GameStatus gameStatus
    ) {
        return new JanggiGame(
                gameId,
                board,
                choPlayer,
                hanPlayer,
                currentPlayer,
                gameStatus
        );
    }


    public void movePiece(final Position from, final Position to) {
        final MoveResult moveResult = board.move(from, to);

        currentPlayer.addScore(moveResult.capturedScore());

        if (moveResult.capturesGeneral()) {
            finish();
            return;
        }

        switchTurn();
    }


    public boolean isPlaying() {
        return gameStatus == GameStatus.PLAYING;
    }


    public long getGameId() {
        return gameId;
    }

    public Players getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
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
