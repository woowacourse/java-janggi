import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Clock;

import io.InputView;
import io.OutputView;
import persistence.GameDatabase;
import persistence.GameStatePersister;
import persistence.GameStateRepository;
import persistence.JdbcGameStateRepository;
import persistence.SchemaInitializer;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = GameDatabase.openFileConnection();
        SchemaInitializer.apply(connection);
        GameStateRepository repository = new JdbcGameStateRepository(() -> connection);
        GameStatePersister persister = new GameStatePersister(repository);
        Runner runner = new Runner(new InputView(), new OutputView());
        runner.run(persister, Clock.systemDefaultZone());
    }
}
