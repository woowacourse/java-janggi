import board.Board;
import board.BoardFactory;
import position.Position;
import piece.Country;
import position.LineDirection;
import view.InputView;
import view.OutputView;

import java.util.List;

public class Application {

    private static final int MAX_TRY_COUNT = 150;

    public static void main(String[] args) {
        OutputView.printIntroduce();

        final BoardFactory boardFactory = new BoardFactory(Country.HAN, LineDirection.UP);
        final Board board = boardFactory.generateBoard();
        Country type = Country.getDefaultTeam();

        int count = 0;
        while (++count < MAX_TRY_COUNT) {
            type = type.opposite();
            OutputView.printBoard(board, type);
            final List<Position> positions = InputView.readPositions();
            board.updatePosition(positions.get(0), positions.get(1), type);
        }
    }
}
