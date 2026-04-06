import controller.JanggiGame;
import database.JanggiService;
import database.SchemaInitializer;
import database.dao.JdbcBoardDao;
import database.dao.JdbcIntersectionDao;
import view.InputReader;
import view.OutputWriter;

public class Application {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        OutputWriter outputWriter = new OutputWriter();

        SchemaInitializer schemaInitializer = new SchemaInitializer();
        schemaInitializer.readShemaSQLFile();

        JdbcBoardDao jdbcBoardDao = new JdbcBoardDao();
        JdbcIntersectionDao jdbcIntersectionDao = new JdbcIntersectionDao();

        JanggiService janggiService = new JanggiService(jdbcBoardDao, jdbcIntersectionDao);
        JanggiGame janggiGame = new JanggiGame(inputReader, outputWriter, janggiService);
        janggiGame.run();
    }

}
