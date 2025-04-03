package domain;

import domain.game.dto.JanggiGameDto;
import domain.piece.Piece;
import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.player.Player;
import domain.player.Players;
import domain.player.Username;
import domain.player.Usernames;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class JanggiRunner {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiTransactionManager janggiTransactionManager;

    public JanggiRunner(InputView inputView, OutputView outputView, JanggiTransactionManager janggiTransactionManager) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiTransactionManager = janggiTransactionManager;
    }

    public void run() {
        Long gameId = getPlayingGameId();
        showCurrentBoard(gameId);
        executeGame(gameId);
        showWinner(gameId);
    }

    private void showCurrentBoard(Long gameId) {
        Map<Position, Piece> alivePieces = janggiTransactionManager.getGamePieces(gameId);
        outputView.printBoard(alivePieces);
    }

    private void executeGame(Long gameId) {
        while (janggiTransactionManager.isInProgress(gameId)) {
            playTurn(gameId);
        }
    }

    private void playTurn(Long gameId) {
        Player currentPlayer = janggiTransactionManager.getCurrentPlayer(gameId);
        if (inputCommand(currentPlayer) == CommandOption.UNDO) {
            janggiTransactionManager.undo(gameId);
            return;
        }
        handleError(() -> movePiece(gameId));
    }

    private void movePiece(Long gameId) {
        Player nowPlayer = janggiTransactionManager.getCurrentPlayer(gameId);
        Position from = handleError(() -> inputView.getStartPosition(nowPlayer));
        Position to = handleError(() -> inputView.getEndPosition(nowPlayer));
        janggiTransactionManager.movePiece(gameId, from, to);
        showCurrentBoard(gameId);
    }

    private CommandOption inputCommand(Player player) {
        return handleError(
                () -> inputView.getOptionCommand(player)
        );
    }

    private void showWinner(Long gameId) {
        Player winner = janggiTransactionManager.findWinner(gameId);
        if (janggiTransactionManager.isFinishedByCheckmate(gameId)) {
            outputView.printWinner(winner);
            return;
        }
        Map<String, Double> playerScore = janggiTransactionManager.calculatePlayerScore(gameId);
        outputView.printScoreWinner(winner, playerScore);
    }

    private long getPlayingGameId() {
        List<JanggiGameDto> inProgressGames = janggiTransactionManager.findInProgressGames();
        if (isInProgressGameResumed(inProgressGames)) {
            return inputView.getInProgressGameId(inProgressGames);
        }

        return getNewGameId();
    }

    private long getNewGameId() {
        Players players = handleError(this::createPlayers);
        HorseElephantSetupStrategy choPlayerStrategy = handleError(
                () -> inputView.getSetupStrategy(players.getChoPlayerName()));
        HorseElephantSetupStrategy hanPlayerStrategy = handleError(
                () -> inputView.getSetupStrategy(players.getHanPlayerName()));
        return janggiTransactionManager.saveNewGame(players, choPlayerStrategy, hanPlayerStrategy);
    }

    private boolean isInProgressGameResumed(List<JanggiGameDto> inProgressGames) {
        return !inProgressGames.isEmpty() && inputView.askToPlayInProgressGame();
    }

    private Players createPlayers() {
        Usernames usernames = createUsernames();
        return handleError(() -> createPlayersFromStartPlayer(usernames));
    }

    private Players createPlayersFromStartPlayer(Usernames usernames) {
        Username startPlayerName = inputView.getStartPlayerName();
        return Players.createFrom(usernames, startPlayerName);
    }

    private Usernames createUsernames() {
        Username firstPlayerName = handleError(inputView::getFirstPlayerName);
        Username secondPlayerName = handleError(inputView::getSecondPlayerName);
        return new Usernames(firstPlayerName, secondPlayerName);
    }

    private <T> T handleError(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return handleError(supplier);
        }
    }

    private void handleError(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            handleError(runnable);
        }
    }
}
