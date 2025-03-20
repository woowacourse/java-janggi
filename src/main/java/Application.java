import board.Board;
import board.BoardFactory;
import board.Position;
import java.util.List;
import piece.TeamType;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        OutputView.printIntroduce();
        final BoardFactory boardFactory = new BoardFactory();
        final Board board = boardFactory.generateBoard();
        TeamType type = TeamType.getDefaultTeam();

        while (true) {
            type = type.toggleTeam();
            OutputView.printBoard(board, type);
            final List<Position> positions = InputView.readPositions();
            board.updatePosition(positions.get(0), positions.get(1), type);
        }
    }
}
