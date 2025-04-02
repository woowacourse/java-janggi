package janggi;

import janggi.board.Board;
import janggi.command.Command;
import janggi.player.Player;
import janggi.player.Players;
import janggi.player.Turn;
import janggi.repository.dto.GameDto;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.StartOption;

import java.util.List;

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
                final StartOption option = inputView.inputStartOption();
                if (option.isQuit()) {
                    outputView.displayQuit();
                    return;
                }

                final GameContext context = prepareGameContext(option);
                runGameLoop(context);
            } catch (final IllegalArgumentException e) {
                outputView.displayError(e.getMessage());
            } catch (final RuntimeException e) {
                outputView.displayError();
            }
        }
    }

    private GameContext prepareGameContext(final StartOption option) {
        if (option.isNew()) {
            return janggiService.createNewContext();
        }
        return janggiService.loadSavedContext(selectSavedGameId());
    }

    private GameId selectSavedGameId() {
        final List<GameDto> runningGames = janggiService.getRunningGames();
        return GameId.from(inputView.inputSelectedSavedGameId(runningGames));
    }

    private void runGameLoop(final GameContext context) {
        final Board board = context.getBoard();
        final Players players = context.getPlayers();
        final Turn turn = context.getTurn();

        while (true) {
            outputView.displayBoard(board);
            outputView.displayScore(players);

            try {
                final Player player = players.getCurrentPlayer();

                final Command command = inputView.inputCommand(player);
                command.execute(context, outputView, janggiService);
                if (command.isExitCommand()) {
                    return;
                }

                if (player.isWin()) {
                    outputView.displayWinner(player);
                    return;
                }
            } catch (final IllegalArgumentException e) {
                outputView.displayError(e.getMessage());
            } catch (final RuntimeException e) {
                outputView.displayError();
            }

            turn.next();
        }
    }
}
