import application.JanggiService;
import console.JanggiConsoleRunner;
import javax.sql.DataSource;
import repository.JanggiRepository;
import repository.JdbcJanggiRepository;
import repository.db.DataSourceManager;
import repository.db.JdbcDao;
import repository.db.TransactionTemplate;

public class Application {

    public static void main(String[] args) {
        DataSource dataSource = DataSourceManager.getDataSource();
        TransactionTemplate transactionTemplate = new TransactionTemplate(dataSource);
        JanggiRepository janggiRepository = new JdbcJanggiRepository(new JdbcDao(dataSource));

        JanggiService janggiService = new JanggiService(transactionTemplate, janggiRepository);
        new JanggiConsoleRunner(janggiService).run();
    }
}
