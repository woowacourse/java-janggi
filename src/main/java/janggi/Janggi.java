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
        Players players = Players.create();

        Turn turn = Turn.create(1);
        Board board = Board.from(players.getBothPieces());
        Turn turn = Turn.start();

        while (true) {
            outputView.displayBoard(board);
            outputView.displayScore(players);

            try {
                Player player = players.getPlayer(turn.getCurrentTeam());
                MoveCommand moveCommand = inputView.inputMoveCommand(player);

                board.movePiece(
                        player,
                        moveCommand.getDeparturePosition(),
                        moveCommand.getDestinationPosition());

                processScore(players);
            } catch (GameOverException e) {
                outputView.display(e.getMessage());
                return;
            } catch (IllegalArgumentException e) {
                outputView.displayError(e.getMessage());
                continue;
            } catch (RuntimeException e) {
                outputView.displayError();
                continue;
            }

            turn.next();
        }
    }

    private void processScore(final Players players) {
        if (players.getScore(Team.CHO).isGreaterThan(WIN)) {
            outputView.displayWinner(players.getPlayer(Team.CHO));
            throw new GameOverException();
        }

        if (players.getScore(Team.HAN).isGreaterThan(WIN)) {
            outputView.displayWinner(players.getPlayer(Team.HAN));
            throw new GameOverException();
        }
    }
}
