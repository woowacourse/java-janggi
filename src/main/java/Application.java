import controller.JanggiController;
import dao.JanggiDao;
import dao.PiecePositionDao;
import manager.JanggiManager;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        final PiecePositionDao piecePositionDao = new PiecePositionDao();
        final JanggiDao janggiDao = new JanggiDao();
        final JanggiManager janggiManager = new JanggiManager(piecePositionDao, janggiDao);

        final JanggiController janggiController = new JanggiController(
                janggiManager,
                inputView,
                outputView);

        janggiController.run();
    }
}
