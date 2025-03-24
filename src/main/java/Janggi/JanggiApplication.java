package Janggi;

import Janggi.board.Board;
import Janggi.board.Position;
import Janggi.piece.Country;
import Janggi.view.InputView;
import Janggi.view.OutputView;
import java.util.List;

public class JanggiApplication {
    private static final int MOVE_SOURCE = 0;
    private static final int MOVE_DESTINATION = 1;

    public static void main(String[] args) {
        OutputView.printIntroduce();
        final Board board = Board.createInitializedJanggiBoard();
        startJanggi(board);
    }

    private static void startJanggi(final Board board) {
        Country type = Country.getFirstTurnCountry();

        while (true) {
            OutputView.printBoard(board, type);
            final List<Position> positions = InputView.readPositions();
            board.updatePosition(positions.get(MOVE_SOURCE), positions.get(MOVE_DESTINATION), type);
            type = type.toggleCountry();
        }
    }
}
