package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.SetupOption;
import java.util.List;

public class Application {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        final SetupOption setupOption = readSetupOption();
        final Board board = BoardGenerator.generate(setupOption);
        while (true) {
            outputView.displayBoard(board.getBoard());
            move(board);
        }
    }

    private SetupOption readSetupOption() {
        try {
            return SetupOption.of(inputView.readSetupOption());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readSetupOption();
        }
    }

    private void move(final Board board) {
        try {
            List<Integer> moveInput = inputView.readMoveCommand();
            Position start = new Position(Row.of(moveInput.get(0)), Column.of(moveInput.get(1)));
            Position end = new Position(Row.of(moveInput.get(2)), Column.of(moveInput.get(3)));
            board.move(start, end);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            move(board);
        }
    }
}
