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

        Turn turn = Turn.create();
        Board board = Board.from(players.getBothPieces());

        while (true) {
            outputView.displayBoard(board);
            outputView.displayScore(players);

            try {
                MoveCommand moveCommand = inputView.inputMoveCommand();

                board.movePiece(
                        players.getPlayer(turn.next()),
                        moveCommand.getDeparturePosition(),
                        moveCommand.getDestinationPosition());

                processScore(players);
            } catch (GameOverException e) {
                outputView.display(e.getMessage());
                return;
            } catch (IllegalArgumentException e) {
                outputView.displayError(e.getMessage());
            } catch (RuntimeException e) {
                outputView.displayError();
            }
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
