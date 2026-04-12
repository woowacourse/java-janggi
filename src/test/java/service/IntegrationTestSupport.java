package service;

import java.nio.file.Path;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import repository.DatabaseInitializer;
import repository.MemoryDBConnectionUtil;

public abstract class IntegrationTestSupport {

    protected static DataSource dataSource = MemoryDBConnectionUtil.getDataSource();

    @BeforeEach
    void initDatabase() {
        new DatabaseInitializer(dataSource).init(Path.of(("src/test/resources/test_schema.sql")));
    }
}
