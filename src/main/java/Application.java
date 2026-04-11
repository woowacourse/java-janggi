import controller.JanggiController;
import repository.BoardRepository;
import repository.DBConnection;
import repository.GameRepository;
import repository.JdbcBoardRepository;
import repository.JdbcGameRepository;
import service.JanggiService;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()) {
            BoardRepository jdbcBoardRepository = new JdbcBoardRepository(connection);
            GameRepository jdbcGameRepository = new JdbcGameRepository(connection);
            JanggiController controller = new JanggiController(new JanggiService(jdbcBoardRepository, jdbcGameRepository));
            controller.start();
        } catch (SQLException e) {
            System.out.println("[ERROR] 데이터베이스 연결에 실패하였습니다. " + e.getMessage());
        }
    }
}
