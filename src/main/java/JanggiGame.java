import domain.Board;
import domain.BoardLocation;
import domain.ChoBoard;
import domain.HanBoard;
import domain.Turn;
import java.util.List;
import view.ConsoleView;

public class JanggiGame {

    private final ConsoleView consoleView;

    public JanggiGame(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public void start() {
        List<BoardLocation> hanHorses = List.of(new BoardLocation(2, 1), new BoardLocation(8, 1));
        List<BoardLocation> hanElephants = List.of(new BoardLocation(3, 1), new BoardLocation(7, 1));

        List<BoardLocation> choHorses = List.of(new BoardLocation(2, 10), new BoardLocation(8, 10));
        List<BoardLocation> choElephants = List.of(new BoardLocation(3, 10), new BoardLocation(7, 10));

        HanBoard hanBoard = HanBoard.createWithPieces(hanHorses, hanElephants);
        ChoBoard choBoard = ChoBoard.createWithPieces(choHorses, choElephants);
        Board board = new Board(choBoard, hanBoard);

        Turn turn = new Turn(board);
        consoleView.showBoard();
        while (true) {
            consoleView.printTurn(turn);
            BoardLocation current = consoleView.requestCurrent();
            BoardLocation destination = consoleView.requestDestination();
            turn.process(current, destination);

            consoleView.printResult(turn);
        }
    }
}
