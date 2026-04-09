package infrastructure;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String CREATE_GAME_INFO_SQL =
            """
                    CREATE TABLE IF NOT EXISTS game_info (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        turn VARCHAR(10) NOT NULL
                    );
                    """;

    private static final String CREATE_POSITION_STATE_SQL =
            """
                    CREATE TABLE IF NOT EXISTS position_state (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        position_x INT NOT NULL,
                        position_y INT NOT NULL,
                        piece_type VARCHAR(20) NOT NULL,
                        piece_country VARCHAR(10) NOT NULL,
                        game_info_id INT NOT NULL,
                        CONSTRAINT fk_position_state_game_info FOREIGN KEY (game_info_id)
                            REFERENCES game_info (id) ON DELETE CASCADE 
                    );
                    """;

    private static final String CREATE_POSITION_HISTORY_SQL =
            """
                    CREATE TABLE IF NOT EXISTS position_history (
                        id INT NOT NULL,
                        position_x INT NOT NULL,
                        position_y INT NOT NULL,
                        piece_type VARCHAR(20) NOT NULL,
                        piece_country VARCHAR(10) NOT NULL,
                        game_info_id INT NOT NULL,
                        turn VARCHAR(10) NOT NULL,
                        PRIMARY KEY (id, game_info_id, position_x, position_y),
                        CONSTRAINT fk_position_history_game_info FOREIGN KEY (game_info_id)
                            REFERENCES game_info (id) ON DELETE CASCADE
                    );
                    """;

    private final JdbcConnectionManager jdbcConnectionManager;

    public DatabaseInitializer(JdbcConnectionManager jdbcConnectionManager) {
        this.jdbcConnectionManager = jdbcConnectionManager;
    }

    public void init() {
        try (
                Connection connection = jdbcConnectionManager.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(CREATE_GAME_INFO_SQL);
            statement.executeUpdate(CREATE_POSITION_STATE_SQL);
            statement.executeUpdate(CREATE_POSITION_HISTORY_SQL);
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 테이블을 생성하는 데 실패했습니다.");
        }
    }
}