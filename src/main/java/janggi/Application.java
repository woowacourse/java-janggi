package janggi;

import janggi.dao.GameRoomDao;
import janggi.dao.PieceDao;
import janggi.db.SQLManager;
import janggi.db.TransactionManager;
import janggi.domain.Game;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Application {
    private static final String FAILED_SETTING_FILE_FIND_MESSAGE = "설정 파일을 찾을 수 없습니다.";
    private static final String FAILED_DB_SETTING_LOAD_MESSAGE = "DB 설정을 불러오는 중 오류가 발생했습니다.";

    public static void main(String[] args) {
        String dbUrl = loadDbUrl();
        SQLManager sqlManager = new SQLManager(dbUrl);
        TransactionManager transactionManager = new TransactionManager(sqlManager);

        GameRoomDao gameRoomDao = new GameRoomDao(sqlManager);
        gameRoomDao.initTable();

        PieceDao pieceDao = new PieceDao(sqlManager);
        pieceDao.initTable();

        JanggiService janggiService = new JanggiService(transactionManager, gameRoomDao, pieceDao);

        Game game = new Game();

        Runner runner = new Runner(janggiService, game);

        int gameId = runner.initBoard();
        runner.runJanggi(gameId);

        sqlManager.closeConnection();
    }

    private static String loadDbUrl() {
        try (InputStream input = Application.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException(FAILED_SETTING_FILE_FIND_MESSAGE);
            }
            prop.load(input);
            return prop.getProperty("db.url");
        } catch (IOException e) {
            throw new RuntimeException(FAILED_DB_SETTING_LOAD_MESSAGE, e);
        }
    }
}
