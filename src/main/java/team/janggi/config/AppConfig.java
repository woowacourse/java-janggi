package team.janggi.config;

import team.janggi.application.ConnectionManager;
import team.janggi.application.JdbcExecutor;
import team.janggi.application.TransactionManager;
import team.janggi.control.JanggiController;
import team.janggi.repository.BoardRepository;
import team.janggi.repository.GameRoomRepository;
import team.janggi.service.JanggiService;

public class AppConfig {
    private static final String URL = "jdbc:h2:./janggi;INIT=RUNSCRIPT FROM './schema.sql'";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static final ConnectionManager connectionManager = new ConnectionManager(URL, USERNAME, PASSWORD);
    private static final TransactionManager transactionManager = new TransactionManager(connectionManager);
    private static final JdbcExecutor jdbcExecutor = new JdbcExecutor(transactionManager);
    private static final JanggiService janggiService = new JanggiService(new GameRoomRepository(jdbcExecutor), new BoardRepository(jdbcExecutor), transactionManager);
    private static final JanggiController janggiController = new JanggiController(janggiService);

    public static JanggiController janggiController() {
        return janggiController;
    }

    public static TransactionManager transactionManager() {
        return transactionManager;
    }
}
