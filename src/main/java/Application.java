import controller.JanggiGame;
import database.JanggiService;
import database.SchemaInitializer;
import database.dao.JdbcBoardDao;
import database.dao.JdbcIntersectionDao;
import database.mapper.JanggiBoardMapper;
import view.InputReader;
import view.OutputWriter;

public class Application {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        OutputWriter outputWriter = new OutputWriter();

        SchemaInitializer schemaInitializer = new SchemaInitializer();
        schemaInitializer.readShemaSQLFile();

        JdbcBoardDao jdbcBoardDao = new JdbcBoardDao();
        JanggiBoardMapper mapper = new JanggiBoardMapper();
        JdbcIntersectionDao jdbcIntersectionDao = new JdbcIntersectionDao();

        JanggiService janggiService = new JanggiService(jdbcBoardDao, mapper, jdbcIntersectionDao);
        JanggiGame janggiGame = new JanggiGame(inputReader, outputWriter, janggiService);
        janggiGame.run();
    }

}
