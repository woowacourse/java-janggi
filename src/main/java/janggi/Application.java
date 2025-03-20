package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.view.BoardView;
import janggi.view.InputView;
import janggi.view.SetupOption;
import janggi.view.WelcomeView;
import java.util.ArrayList;
import java.util.List;

public class Application {

    private final WelcomeView welcomeView = new WelcomeView();
    private final InputView inputView = new InputView();
    private final BoardView boardView = new BoardView();

    public static void main(String[] args) {
        final Application janggi = new Application();
        janggi.run();
    }

    private void run() {
        welcomeView.display();
        final SetupOption setupOption = readSetupOption();
        final Board board = BoardGenerator.generate(setupOption);
        boardView.display(board);
        String input;
        while (Command.END != readCommand(input = inputView.read())) {
            move(board, input);
        }
    }

    private SetupOption readSetupOption() {
        try {
            return SetupOption.of(inputView.read());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readSetupOption();
        }
    }

    private Command readCommand(String input) {
        try {
            return Command.of(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            input = inputView.read();
            return readCommand(input);
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

    private void move(final Board board, final String input) {
        try {
            final List<Integer> moveCommand = parseMoveCommand(input);
            final Position start = new Position(Row.of(moveCommand.get(0)), Column.of(moveCommand.get(1)));
            final Position end = new Position(Row.of(moveCommand.get(2)), Column.of(moveCommand.get(3)));
            board.move(start, end);
            boardView.display(board);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
