package janggi;

import janggi.temp.game.Board;
import janggi.temp.game.BoardGenerator;
import janggi.temp.game.Game;
import janggi.temp.position.Column;
import janggi.temp.position.Position;
import janggi.temp.position.Row;
import janggi.view.BoardView;
import janggi.view.InputView;
import janggi.view.SetupOption;
import janggi.view.SystemView;
import java.util.ArrayList;
import java.util.List;

public class Application {

    private final SystemView systemView = new SystemView();
    private final InputView inputView = new InputView();
    private final BoardView boardView = new BoardView();

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        systemView.display();
        final Game game = setupGame();
        boardView.displaySetUp(game);
        while (true) {
            String command = inputView.readCommand();
            if (command.equals("end")) {
                systemView.outGame();
                return;
            }
            move(game, command);
            boardView.displayBoard(game);
        }
    }

    private Game setupGame() {
        final String input = inputView.readSetupOption();
        final Board board = BoardGenerator.generate(SetupOption.of(input));
        return new Game(board);
    }


    private void move(final Game game, final String input) {
        try {
            final List<Integer> moveCommand = parseMoveCommand(input);
            final Position start = new Position(Column.of(moveCommand.get(0)), Row.of(moveCommand.get(1)));
            final Position end = new Position(Column.of(moveCommand.get(2)), Row.of(moveCommand.get(3)));
            game.move(start, end);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<Integer> parseMoveCommand(final String input) {
        List<Integer> moveInfo = new ArrayList<>();
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(5))));
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(6))));
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(8))));
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(9))));
        return moveInfo;
    }
}
