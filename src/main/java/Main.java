import controller.JanggiGameController;
import repository.adapter.GameDaoImplementation;
import repository.dao.GameDao;
import repository.jdbc.GameJdbcRepository;
import repository.jdbc.GamePieceJdbcRepository;
import repository.jdbc.JdbcConnectionGenerator;
import repository.jdbc.JdbcTemplate;
import repository.mapper.GameContextMapper;
import repository.mapper.PieceEntityMapper;
import service.JanggiGameService;
import service.TransactionTemplate;
import view.InputView;
import view.ResultView;

public class Main {

    private static final String DB_CONFIG_FILE = "database.properties";

    public static void main(String[] args) {
        createJanggiGameController().play();
    }

    private static JanggiGameController createJanggiGameController() {
        InputView inputView = new InputView();
        ResultView resultView = new ResultView();

        JanggiGameService janggiGameService = createJanggiGameService();

        return new JanggiGameController(inputView, resultView, janggiGameService);
    }

    private static JanggiGameService createJanggiGameService() {
        JdbcConnectionGenerator jdbcConnectionGenerator = JdbcConnectionGenerator.create(DB_CONFIG_FILE);
        TransactionTemplate transactionTemplate = new TransactionTemplate(jdbcConnectionGenerator);

        return new JanggiGameService(transactionTemplate, createGameDao());
    }

    private static GameDao createGameDao() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        GameJdbcRepository gameJdbcRepository = new GameJdbcRepository(jdbcTemplate);
        GamePieceJdbcRepository gamePieceJdbcRepository = new GamePieceJdbcRepository(jdbcTemplate);

        GameContextMapper gameContextMapper = new GameContextMapper();
        PieceEntityMapper pieceEntityMapper = new PieceEntityMapper();

        return new GameDaoImplementation(gameJdbcRepository, gamePieceJdbcRepository, gameContextMapper,
                pieceEntityMapper);
    }
}
