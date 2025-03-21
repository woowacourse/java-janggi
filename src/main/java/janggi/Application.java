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
        boardView.displayGame(board);
        boardView.displayTurn(board);
        Command command = Command.STOP;
        do {
            command = executeCommand(command, board);
        } while (!command.equals(Command.STOP));
    }

    private SetupOption readSetupOption() {
        try {
            return SetupOption.of(inputView.read());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readSetupOption();
        }
    }

    private Command executeCommand(Command command, final Board board) {
        final String input = inputView.read();
        try {
            command = Command.of(input);
            moveUntilStop(command, board, input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return command;
    }

    private void moveUntilStop(final Command command, final Board board, final String input) {
        if (!command.equals(Command.STOP)) {
            move(board, input);
        }
    }

    private void move(final Board board, final String input) {
        try {
            final List<Integer> moveCommand = parseMoveCommand(input);
            final Position start = new Position(Row.of(moveCommand.get(0)), Column.of(moveCommand.get(1)));
            final Position end = new Position(Row.of(moveCommand.get(2)), Column.of(moveCommand.get(3)));
            board.move(start, end);
            boardView.displayGame(board);
            boardView.displayTurn(board);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<Integer> parseMoveCommand(final String input) {
        List<Integer> moveInfo = new ArrayList<>();
        System.out.println("내가 입력한 값 : " + input);
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(5))));
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(6))));
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(8))));
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(9))));
        return moveInfo;
    }
}
