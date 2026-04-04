package database;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import view.OutputView;

public class MysqlConnectionManager {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String SERVER = dotenv.get("SERVER");
    private static final String DATABASE = dotenv.get("DATABASE");
    private static final String OPTION = dotenv.get("OPTION");
    private static final String USERNAME = dotenv.get("USERNAME");
    private static final String PASSWORD = dotenv.get("PASSWORD");

    private static final String URL = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            OutputView.printError(e.getMessage());
            throw new RuntimeException("DB 연결 실패", e);
        }
    }
}
