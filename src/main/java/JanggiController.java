import domain.BoardFactory;
import view.OutputView;

public class JanggiController {

    private final OutputView outputView;

    public JanggiController(OutputView outputView) {
        this.outputView = outputView;
    }

    public void run() {
        outputView.printBoard(BoardFactory.setUp().getBoard());
    }
}
