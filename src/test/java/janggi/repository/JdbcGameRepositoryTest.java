package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dto.GameSnapshot;
import janggi.dto.GameSummary;
import janggi.domain.status.Team;
import janggi.dto.PositionInfo;
import janggi.util.DatabaseInitializer;
import janggi.util.JdbcConnectionManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JdbcGameRepositoryTest {

    private GameRepository repository;
    private JdbcConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        String databaseName = "test" + System.nanoTime();
        connectionManager = new JdbcConnectionManager(
                "jdbc:h2:mem:" + databaseName + ";DB_CLOSE_DELAY=-1",
                "sa",
                ""
        );
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(connectionManager);
        databaseInitializer.init();
        repository = new JdbcGameRepository(connectionManager);
    }

    @Test
    @DisplayName("게임 저장 기능")
    void save() {
        // given
        GameSnapshot gameSnapshot = new GameSnapshot(
                null,
                Team.CHO,
                false,
                null,
                List.of(
                        PositionInfo.from(Team.CHO, "JANG", 4, 1),
                        PositionInfo.from(Team.HAN, "JANG", 4, 8)
                )
        );

        // when
        repository.save(gameSnapshot);

        // then
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("모든 게임 목록 조회")
    void findAll() {
        GameSnapshot firstGame = new GameSnapshot(
                null,
                Team.CHO,
                false,
                null,
                List.of(
                        PositionInfo.from(Team.CHO, "JANG", 4, 1),
                        PositionInfo.from(Team.HAN, "JANG", 4, 8)
                )
        );

        GameSnapshot secondGame = new GameSnapshot(
                null,
                Team.HAN,
                true,
                Team.HAN,
                List.of(
                        PositionInfo.from(Team.CHO, "JANG", 4, 1)
                )
        );

        repository.save(firstGame);
        repository.save(secondGame);

        List<GameSummary> gameSummaries = repository.findAll();

        assertThat(gameSummaries).hasSize(2);
        assertThat(gameSummaries.get(0).finished()).isFalse();
        assertThat(gameSummaries.get(1).finished()).isTrue();
    }

    @Test
    @DisplayName("게임 id로 저장된 게임 조회")
    void findById() {
        // given
        GameSnapshot gameSnapshot = new GameSnapshot(
                1L,
                Team.CHO,
                false,
                null,
                List.of(
                        PositionInfo.from(Team.CHO, "JANG", 4, 1),
                        PositionInfo.from(Team.HAN, "JANG", 4, 8)
                )
        );
        repository.save(gameSnapshot);

        // when
        Long gameId = repository.findAll().get(0).id();
        GameSnapshot foundGame = repository.findById(gameId).orElseThrow();


        // then
        assertThat(foundGame.id()).isEqualTo(gameId);
        assertThat(foundGame.currentTurn()).isEqualTo(Team.CHO);
        assertThat(foundGame.finished()).isFalse();
        assertThat(foundGame.winner()).isNull();
        assertThat(foundGame.positions()).hasSize(2);
    }

    @Test
    @DisplayName("게임 상태 수정")
    void update() {
        // given
        GameSnapshot savedGame = new GameSnapshot(
                1L,
                Team.CHO,
                false,
                null,
                List.of(
                        PositionInfo.from(Team.CHO, "JANG", 4, 1),
                        PositionInfo.from(Team.HAN, "JANG", 4, 8)
                )
        );
        repository.save(savedGame);
        Long gameId = repository.findAll().get(0).id();

        // when
        GameSnapshot updatedGame = new GameSnapshot(
                gameId,
                Team.HAN,
                true,
                Team.HAN,
                List.of(
                        PositionInfo.from(Team.HAN, "JANG", 4, 8)
                )
        );
        repository.update(updatedGame);

        // then
        GameSnapshot foundGame = repository.findById(gameId).orElseThrow();

        assertThat(foundGame.id()).isEqualTo(gameId);
        assertThat(foundGame.currentTurn()).isEqualTo(Team.HAN);
        assertThat(foundGame.finished()).isTrue();
        assertThat(foundGame.winner()).isEqualTo(Team.HAN);
        assertThat(foundGame.positions()).hasSize(1);

    }
}
