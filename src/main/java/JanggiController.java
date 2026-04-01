import domain.Board;
import domain.BoardFactory;
import domain.Formation;
import domain.Team;
import domain.vo.Position;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Formation hanFormation = inputView.readHorseElephantFormation(Team.HAN.getName());
        Formation chuFormation = inputView.readHorseElephantFormation(Team.CHU.getName());

        Board board = BoardFactory.setUp(hanFormation, chuFormation);
        outputView.printBoard(board.getBoard());

        move(board);

        while (inputView.readRetryCommand()) {
            move(board);
        }
    }

    private void move(Board board) {
        try {
            Position position = inputView.readPosition();
            Position targetPosition = inputView.readTargetPosition();

            board.tryToMove(position, targetPosition);
            outputView.printBoard(board.getBoard());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            move(board);
        }
    }
}
