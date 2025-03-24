package Janggi;

import Janggi.board.Board;
import Janggi.board.BoardFactory;
import Janggi.board.Position;
import java.util.List;
import Janggi.view.InputView;
import Janggi.piece.Country;
import Janggi.view.OutputView;

public class JanggiApplication {
    private static final int MOVE_SOURCE = 0;
    private static final int MOVE_DESTINATION = 1;

    public static void main(String[] args) {
        OutputView.printIntroduce();
        final BoardFactory boardFactory = new BoardFactory();
        final Board board = boardFactory.generateBoard();
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
