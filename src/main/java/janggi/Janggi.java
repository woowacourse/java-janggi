package janggi;

import janggi.view.InputView;
import janggi.view.MoveCommand;
import janggi.view.OutputView;

public class Janggi {

    private static final Score WIN = Score.win();

    private final InputView inputView;
    private final OutputView outputView;

    public Janggi(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void play() {
        final Turn turn = Turn.start();
        final Players players = Players.create(turn);
        final Board board = players.createBoard();

        while (true) {
            outputView.displayBoard(board);
            outputView.displayScore(players);

            try {
                final Player player = players.getCurrentPlayer();
                final MoveCommand moveCommand = inputView.inputMoveCommand(player);

                board.movePiece(
                        player,
                        moveCommand.getDeparturePosition(),
                        moveCommand.getDestinationPosition());

                checkWinner(players);
            } catch (final GameOverException e) {
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
        for (final Team team : Team.values()) {
            if (players.getScore(team).isLessThan(WIN)) {
                continue;
            }
            outputView.displayWinner(players.getPlayer(team));
            throw new GameOverException();
        }
    }
}
