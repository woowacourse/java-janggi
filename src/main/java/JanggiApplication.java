import controller.JanggiController;
import database.GameRepository;
import database.jdbc.JdbcGameDao;
import database.jdbc.JdbcPieceDao;
import view.ConsolePieceAppearance;
import view.InputView;
import view.OutputView;

public class JanggiApplication {
    public static void main(String[] args) {
        JdbcGameDao jdbcGameDao = new JdbcGameDao();
        JdbcPieceDao jdbcPieceDao = new JdbcPieceDao();
        GameRepository gameRepository = new GameRepository(jdbcGameDao, jdbcPieceDao);
        JanggiController janggiController = new JanggiController(new InputView(),
                new OutputView(new ConsolePieceAppearance()), gameRepository);
        try {
            janggiController.run();
        } catch (RuntimeException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
}
