package janggi;

import janggi.controller.JanggiGameController;
import janggi.dao.BoardDao;
import janggi.dao.PieceDao;
import janggi.db.TransactionManager;
import janggi.service.BoardService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) throws SQLException {
        try (Connection connection = getConnection()) {
            BoardDao boardDao = new BoardDao(connection);
            PieceDao pieceDao = new PieceDao(connection);
            TransactionManager transactionManager = new TransactionManager(connection);

            BoardService boardService = new BoardService(boardDao, pieceDao, transactionManager);
            InputView inputView = new InputView();
            OutputView outputView = new OutputView();

            JanggiGameController janggiGameController = new JanggiGameController(inputView, outputView, boardService);
            janggiGameController.runGame();
        }
    }
}
