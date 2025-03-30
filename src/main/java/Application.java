import controller.JanggiController;
import domain.board.repository.BoardRepository;
import domain.turn.repository.TurnRepository;
import infrastructure.dao.PieceDao;
import infrastructure.dao.TurnDao;
import infrastructure.repository.BoardRepositoryAdapter;
import infrastructure.repository.TurnRepositoryAdapter;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(final String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        final BoardRepository boardRepository = new BoardRepositoryAdapter(new PieceDao());
        final TurnRepository turnRepository = new TurnRepositoryAdapter(new TurnDao());
        final JanggiService janggiService = new JanggiService(boardRepository, turnRepository);

        final JanggiController janggiController =
            new JanggiController(inputView, outputView, janggiService);

        janggiController.run();
    }
}
