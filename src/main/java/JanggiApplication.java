import controller.JanggiController;
import domain.game.GameService;
import domain.piece.PieceService;
import domain.player.PlayerService;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(new InputView(), new OutputView(),
                new PlayerService(), new GameService(), new PieceService());
        janggiController.run();
    }
}
