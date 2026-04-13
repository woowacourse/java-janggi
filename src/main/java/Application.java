import controller.JanggiController;
import repository.BoardRepository;
import repository.DBConnection;
import repository.GameRepository;
import repository.JdbcBoardRepository;
import repository.JdbcGameRepository;
import service.JanggiService;

import javax.sql.DataSource;

public class Application {

    public static void main(String[] args) {
        try {
            DataSource dataSource = DBConnection.getConnection();
            BoardRepository jdbcBoardRepository = new JdbcBoardRepository(dataSource);
            GameRepository jdbcGameRepository = new JdbcGameRepository(dataSource);

            JanggiController controller = new JanggiController(
                    new JanggiService(jdbcBoardRepository, jdbcGameRepository)
            );

            controller.start();
        } catch (Exception e) {
            System.out.println("[ERROR] 애플리케이션 실행 중 문제가 발생하였습니다: " + e.getMessage());
        }
    }
}
