package janggi.console;

import janggi.game.Board;
import janggi.game.BoardGenerator;
import janggi.game.Game;
import janggi.view.BoardView;
import janggi.view.InputView;
import janggi.view.SetupOption;
import janggi.view.SystemView;

public final class GameSetupConsole {

    private final InputView inputView;
    private final SystemView systemView;
    private final BoardView boardView;

    public GameSetupConsole(final InputView inputView, final SystemView systemView, final BoardView boardView) {
        this.inputView = inputView;
        this.systemView = systemView;
        this.boardView = boardView;
    }

    public void displayGameDescriptions() {
        systemView.displayGameDescriptions();
    }

    public Game setupGame() {
        systemView.displaySetupMenus();
        final String input = inputView.readSetupOption();
        final Board board = BoardGenerator.generate(SetupOption.of(input));
        return new Game(board);
    }

    public void displayGameSetup(final Game game) {
        systemView.inGame();
        boardView.displayBoard(game);
    }
}
