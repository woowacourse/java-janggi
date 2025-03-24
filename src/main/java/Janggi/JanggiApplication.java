package Janggi;

import Janggi.board.Board;
import Janggi.board.BoardFactory;
import Janggi.board.Position;
import java.util.List;
import Janggi.view.InputView;
import Janggi.piece.Country;
import Janggi.view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        OutputView.printIntroduce();
        final BoardFactory boardFactory = new BoardFactory();
        final Board board = boardFactory.generateBoard();
        Country type = Country.getFirstTurnCountry();

        while (true) {
            OutputView.printBoard(board, type);
            final List<Position> positions = InputView.readPositions();
            board.updatePosition(positions.get(0), positions.get(1), type);
            type = type.toggleCountry();
        }
    }
}
