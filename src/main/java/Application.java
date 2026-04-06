import controller.JanggiController;
import java.io.IOException;
import repository.DBConnectionUtil;
import repository.JanggiGameRepository;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) throws IOException {
        JanggiController janggiController = new JanggiController(
                new JanggiService(
                        new JanggiGameRepository(DBConnectionUtil.getDataSource())
                ),
                new InputView(),
                new OutputView()
        );

        janggiController.run();
    }
}
