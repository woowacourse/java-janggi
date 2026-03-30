package exception;

import exception.custom.GameException;
import view.OutputView;

public class GameExceptionHandler {
    private final OutputView outputView;

    public GameExceptionHandler(OutputView outputView){
        this.outputView = outputView;
    }

    public void handle(GameException exception){
        outputView.printErrorMessage(exception.getMessage());
    }
}
