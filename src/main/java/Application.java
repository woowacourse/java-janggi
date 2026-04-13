import controller.JanggiController;
import java.nio.file.Path;
import javax.sql.DataSource;
import repository.DatabaseInitializer;
import support.DBConnectionUtil;
import repository.JanggiGameRepository;
import service.JanggiService;
import support.TransactionTemplate;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        DataSource dataSource = DBConnectionUtil.getDataSource();
        DatabaseInitializer initializer = new DatabaseInitializer(dataSource);
        initializer.init(Path.of(("sql/ddl.sql")));

        JanggiController janggiController = new JanggiController(
                new JanggiService(
                        new TransactionTemplate(dataSource),
                        new JanggiGameRepository()
                ),
                new InputView(),
                new OutputView()
        );

        janggiController.run();
    }
}
