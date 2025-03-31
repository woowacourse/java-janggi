package janggi;

import janggi.board.Board;
import janggi.player.Player;
import janggi.player.Players;
import janggi.player.Turn;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.StartOption;
import janggi.view.command.Command;
import janggi.view.command.MoveCommand;

import java.util.List;
import java.util.NoSuchElementException;

public class Janggi {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public Janggi(final InputView inputView,
                  final OutputView outputView,
                  final JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void run() {
        while (true) {
            try {
                final GameContext context = prepareGameContext();
                runGameLoop(context);
            } catch (final GameQuitException e) {
                outputView.display(e.getMessage());
                return;
            } catch (final IllegalArgumentException | NoSuchElementException e) {
                outputView.displayError(e.getMessage());
            } catch (final RuntimeException e) {
                outputView.displayError();
            }
        }
    }

    private GameContext prepareGameContext() {
        final StartOption select = inputView.inputStartOption();

        if (select.isNew()) {
            return janggiService.createNewContext();
        }

        if (select.isLoad()) {
            return janggiService.loadSavedContext(selectSavedGameId());
        }

        if (select.isQuit()) {
            throw new GameQuitException("게임 종료를 선택했습니다");
        }

        throw new RuntimeException();
    }

    private long selectSavedGameId() {
        final List<Integer> runningGameIds = janggiService.getRunningGameIds();
        return inputView.inputSelectedSavedGameId(runningGameIds);
    }

    private void runGameLoop(final GameContext context) {
        final Long gameId = context.getGameId();
        final Board board = context.getBoard();
        final Players players = context.getPlayers();
        final Turn turn = context.getTurn();

        while (true) {
            outputView.displayBoard(board);
            outputView.displayScore(players);

            try {
                final Player player = players.getCurrentPlayer();
                final Command command = inputView.inputCommand(player);

                if (command.getType().isMove()) {
                    janggiService.movePiece(
                            board,
                            player,
                            (MoveCommand) command);
                    checkWinner(players);
                }

                if (command.getType().isSave()) {
                    janggiService.saveGame(
                            gameId,
                            players,
                            turn,
                            board.getAlivePieces().getPieces());
                    return;
                }

                if (command.getType().isQuit()) {
                    throw new GameQuitException();
                }

            } catch (final GameQuitException e) {
                outputView.display(e.getMessage());
                return;
            } catch (final IllegalArgumentException e) {
                outputView.displayError(e.getMessage());
                continue;
            } catch (final RuntimeException e) {
                outputView.displayError();
                continue;
            }

            turn.next();
        }
    }

    private void checkWinner(final Players players) {
        if (janggiService.isGameOver(players)) {
            outputView.displayWinner(players.getCurrentPlayer());
            throw new GameQuitException();
        }
    }
}
