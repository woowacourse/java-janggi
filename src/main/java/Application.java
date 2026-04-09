import controller.JanggiController;
import java.io.IOException;
import javax.sql.DataSource;
import repository.DBConnectionUtil;
import repository.JanggiGameRepository;
import service.JanggiService;
import service.TransactionTemplate;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) throws IOException {
        DataSource dataSource = DBConnectionUtil.getDataSource();
        JanggiController janggiController = new JanggiController(
                new JanggiService(
                        new TransactionTemplate(dataSource),
                        new JanggiGameRepository(dataSource)
                ),
                new InputView(),
                new OutputView()
        );

        janggiController.run();
    }
}
