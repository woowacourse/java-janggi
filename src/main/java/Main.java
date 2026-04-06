import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import io.InputView;
import io.OutputView;
import persistence.GameDatabase;
import persistence.GameStateRepository;
import persistence.JdbcGameStateRepository;
import persistence.SchemaInitializer;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = GameDatabase.openFileConnection();
        SchemaInitializer.apply(connection);
        GameStateRepository repository = new JdbcGameStateRepository(() -> connection);
        new Runner(new InputView(), new OutputView(), repository).run();
    }
}
