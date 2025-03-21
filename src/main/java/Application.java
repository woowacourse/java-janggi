import board.Board;
import board.BoardFactory;
import board.Position;
import java.util.List;
import view.InputView;
import piece.Country;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        OutputView.printIntroduce();
        final BoardFactory boardFactory = new BoardFactory();
        final Board board = boardFactory.generateBoard();
        Country type = Country.getDefaultTeam();

        while (true) {
            type = type.toggleTeam();
            OutputView.printBoard(board, type);
            final List<Position> positions = InputView.readPositions();
            board.updatePosition(positions.get(0), positions.get(1), type);
        }
    }
}
