import controller.JanggiGame;
import service.JanggiService;
import database.SchemaInitializer;
import database.transaction.JanggiTransactionExecutor;
import database.transaction.TransactionExecutor;
import database.dao.JdbcBoardDao;
import database.dao.JdbcIntersectionDao;
import database.dao.JdbcTemplate;
import view.InputReader;
import view.OutputWriter;

public class Application {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        OutputWriter outputWriter = new OutputWriter();

        SchemaInitializer schemaInitializer = new SchemaInitializer();
        schemaInitializer.readShemaSQLFile();

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        JdbcBoardDao jdbcBoardDao = new JdbcBoardDao(jdbcTemplate);
        JdbcIntersectionDao jdbcIntersectionDao = new JdbcIntersectionDao(jdbcTemplate);
        TransactionExecutor transactionExecutor = new JanggiTransactionExecutor();

        JanggiService janggiService = new JanggiService(jdbcBoardDao, transactionExecutor, jdbcIntersectionDao);
        JanggiGame janggiGame = new JanggiGame(inputReader, outputWriter, janggiService);
        janggiGame.run();
    }

}
