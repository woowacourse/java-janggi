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
    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final SetupOption setupOption = SetupOption.of(inputView.readSetupOption());
        final Board board = BoardGenerator.generate(setupOption);
        while (true) {
            outputView.displayBoard(board.getBoard());
            List<Integer> moveInput = inputView.readMoveCommand();
            Position start = new Position(Row.of(moveInput.get(0)), Column.of(moveInput.get(1)));
            Position end = new Position(Row.of(moveInput.get(2)), Column.of(moveInput.get(3)));
            board.move(start, end);
        }
    }
}
