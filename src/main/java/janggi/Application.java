package janggi;

import janggi.controller.JanggiFlow;
import janggi.db.connection.DbConnection;
import janggi.db.connection.MySqlConnection;
import janggi.db.dao.JanggiGameMySqlDao;
import janggi.db.dao.PieceMySqlDao;
import janggi.db.repository.GameRepository;
import janggi.service.JanggiService;
import janggi.view.ApplicationView;
import janggi.view.ConsoleReader;
import janggi.view.ConsoleWriter;

public class Application {

    public static void main(String[] args) {

        DbConnection dbConnection = new MySqlConnection();

        JanggiGameMySqlDao janggiGameDao = new JanggiGameMySqlDao();
        PieceMySqlDao pieceDao = new PieceMySqlDao();

        GameRepository repository = new GameRepository(dbConnection, janggiGameDao, pieceDao);

        JanggiService service = new JanggiService(repository);

        ApplicationView view = new ApplicationView(new ConsoleWriter(), new ConsoleReader());

        JanggiFlow janggi = new JanggiFlow(view, service);

        janggi.process();
    }
}
