import repository.BoardRepository;
import repository.GameRepository;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        BoardRepository boardRepository = new BoardRepository();
        GameRepository gameRepository = new GameRepository();

        JanggiController janggiController = new JanggiController(inputView, outputView,
                boardRepository, gameRepository);
        janggiController.run();
    }
}
