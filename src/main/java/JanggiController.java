import domain.BoardFactory;
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
        outputView.printBoard(BoardFactory.setUp().getBoard());

        Position position = inputView.readPosition();

        while (true) {
            if (!inputView.readRetryCommand()) {
                break;
            }

            inputView.readPosition();
        }
    }
}
