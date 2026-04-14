import controller.JanggiController;
import domain.board.BasicBoardInitializer;
import repository.GameRepository;
import repository.PieceRepository;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiService janggiService = new JanggiService(
                new GameRepository(), new PieceRepository(), new BasicBoardInitializer()
        );
        JanggiController janggiController = new JanggiController(new InputView(), new OutputView(), janggiService);
        janggiController.play();
    }
}
