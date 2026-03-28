package application;

import domain.game.Game;
import domain.piece.Camp;
import view.GameCommand;
import view.InputView;
import view.MoveCommand;
import view.OutputView;
import view.PassCommand;

import java.util.NoSuchElementException;

public class Application {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        outputView.printSetUpOptions();
        int hanSetUp = readSetUp(Camp.HAN);
        int choSetUp = readSetUp(Camp.CHO);

        Game game = new Game(choSetUp, hanSetUp);
        outputView.printBoard(game.board());

        playJanggi(game);
    }

    private void playJanggi(Game game) {
        while (true) {
            outputView.printTurnPrompt(game.currentTurn());

            try {
                GameCommand command = inputView.readCommand();
                execute(game, command);
                outputView.printBoard(game.board());
            } catch (IllegalArgumentException | NoSuchElementException exception) {
                outputView.printError(exception.getMessage());
            } catch (IllegalStateException exception) {
                return;
            }
        }
    }

    private int readSetUp(Camp camp) {
        while (true) {
            outputView.printSetUpPrompt(camp);

            try {
                return inputView.readSetUp();
            } catch (IllegalArgumentException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private void execute(Game game, GameCommand command) {
        if (command instanceof PassCommand) {
            game.passTurn();
            return;
        }

        MoveCommand moveCommand = (MoveCommand) command;
        game.move(moveCommand.from(), moveCommand.to());
    }
}
