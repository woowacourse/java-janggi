import controller.JanggiController;
import domain.board.repository.BoardRepository;
import domain.janggi.service.JanggiService;
import domain.turn.repository.TurnRepository;
import infra.dao.PieceDao;
import infra.dao.TurnDao;
import infra.repository.BoardRepositoryAdapter;
import infra.repository.TurnRepositoryAdapter;
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
