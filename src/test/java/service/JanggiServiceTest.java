package service;

import config.db.DatabaseConfig;
import model.coordinate.Position;
import model.game.Team;
import model.piece.Soldier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.JanggiRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiServiceTest {

    private Connection connection;
    private JanggiService service;

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseConfig.initSchema();
        connection = DatabaseConfig.getConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM piece");
            stmt.execute("DELETE FROM game");
        }
        service = new JanggiService(new JanggiRepositoryImpl(connection));
    }

    @Test
    void 진행중인_게임이_있으면_재개한다() {
        // given
        saveGameDirectly(Team.HAN);

        // when
        boolean resumed = service.tryResumeGame();

        // then
        assertThat(resumed).isTrue();
    }

    private void saveGameDirectly(Team turn) {
        JanggiRepositoryImpl repository = new JanggiRepositoryImpl(connection);
        repository.saveGame(turn, Map.of(
                new Position(6, 0), new Soldier(Team.HAN),
                new Position(3, 0), new Soldier(Team.CHO)
        ));
    }

    @Test
    void 재개시_저장된_턴이_복원된다() {
        // given
        saveGameDirectly(Team.CHO);

        // when
        service.tryResumeGame();

        // then
        assertThat(service.getTurn()).isEqualTo(Team.CHO);
    }

    @Test
    void 재개시_저장된_기물이_복원된다() {
        // given
        saveGameDirectly(Team.HAN);

        // when
        service.tryResumeGame();

        // then
        assertThat(service.getBoard()).hasSize(2);
    }

    @Test
    void 진행중인_게임이_없으면_재개하지_않는다() {
        // when
        boolean resumed = service.tryResumeGame();

        // then
        assertThat(resumed).isFalse();
    }
}
