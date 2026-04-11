import controller.GameController;
import domain.Janggi;
import domain.board.Board;
import domain.player.Players;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        GameController controller = new GameController(inputView, outputView);

        Players players = controller.getPlayer();
        Board board = controller.getBoard();
        Janggi janggi = new Janggi(players, board);

        controller.run(janggi);
    }
}
