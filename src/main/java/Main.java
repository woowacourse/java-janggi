import config.DataSourceConfig;
import controller.JanggiController;
import dao.BoardDao;
import dao.GameDao;
import service.GameService;
import view.InputView;
import view.OutputView;

public class Main {
    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(
                new InputView(),
                new OutputView(),
                new GameService(
                        new DataSourceConfig().getDataSource(),
                        new GameDao(),
                        new BoardDao()
                )
        );
        janggiController.run();
    }
}
