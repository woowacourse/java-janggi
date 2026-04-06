import controller.JanggiController;
import repository.BoardRepository;
import repository.GameRepository;
import repository.JdbcBoardRepository;
import repository.JdbcGameRepository;

public class Application {

    public static void main(String[] args) {
        BoardRepository jdbcBoardRepository = new JdbcBoardRepository();
        GameRepository jdbcGameRepository = new JdbcGameRepository();
        JanggiController controller = new JanggiController(jdbcBoardRepository, jdbcGameRepository);
        controller.start();
    }
}
