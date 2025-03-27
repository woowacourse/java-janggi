import controller.JanggiController;
import dao.JanggiDao;
import dao.PiecePositionDao;
import manager.JanggiManager;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        PiecePositionDao piecePositionDao = new PiecePositionDao();
        JanggiDao janggiDao = new JanggiDao();
        JanggiManager janggiManager = new JanggiManager(piecePositionDao, janggiDao);
        JanggiController janggiController = new JanggiController(janggiManager, inputView, outputView);

        janggiController.run();
    }
}
