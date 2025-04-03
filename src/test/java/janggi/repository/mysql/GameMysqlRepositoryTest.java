package janggi.repository.mysql;

import fixture.TestContainer;
import fixture.TestContainerSupport;
import janggi.GameId;
import janggi.GameStatus;
import janggi.player.Score;
import janggi.player.Turn;
import janggi.repository.dto.GameDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class GameMysqlRepositoryTest extends TestContainerSupport {

    private final GameMysqlRepository repository = new GameMysqlRepository();
    private Connection connection;

    @BeforeEach
    void setUp() throws Exception {
        connection = TestContainer.getConnection();
    }

    @Test
    @DisplayName("새 게임 저장 후 ID를 반환하고, 해당 게임을 조회할 수 있다")
    void saveWithoutId() {
        // given
        final Turn turn = Turn.start();
        final Score choScore = Score.from(3);
        final Score hanScore = Score.from(5);

        // when
        final GameId savedId = repository.save(connection, turn, choScore, hanScore);
        final Optional<GameDto> found = repository.findById(connection, savedId);

        // then
        assertThat(savedId.getValue()).isGreaterThan(0);
        assertThat(found).isPresent();
        final GameDto dto = found.get();
        assertThat(dto.turn()).isEqualTo(turn.getAccumulatedCount());
        assertThat(dto.choScore()).isEqualTo(choScore.value());
        assertThat(dto.hanScore()).isEqualTo(hanScore.value());
        assertThat(dto.status()).isEqualTo(GameStatus.RUNNING.name());
    }

    @Test
    @DisplayName("ID를 지정하여 게임을 저장하면, 이후 해당 ID로 게임을 조회할 수 있다")
    void saveWithId() {
        // given
        final GameId id = GameId.from(100L);
        final Turn turn = Turn.from(10);
        final Score choScore = Score.from(5);
        final Score hanScore = Score.from(6);

        // when
        final GameId result = repository.save(connection, id, turn, choScore, hanScore);
        final Optional<GameDto> found = repository.findById(connection, result);

        // then
        assertThat(result).isEqualTo(id);
        assertThat(found).isPresent();
        final GameDto dto = found.get();
        assertThat(dto.turn()).isEqualTo(turn.getAccumulatedCount());
        assertThat(dto.choScore()).isEqualTo(choScore.value());
        assertThat(dto.hanScore()).isEqualTo(hanScore.value());
    }

    @Test
    @DisplayName("저장된 게임을 조회할 수 있다")
    void findById() {
        // given
        final GameId gameId = repository.save(connection, Turn.start(), Score.from(3), Score.from(5));

        // when
        final Optional<GameDto> found = repository.findById(connection, gameId);

        // then
        assertThat(found).isPresent();
        final GameDto dto = found.get();
        assertThat(dto.turn()).isEqualTo(1);
        assertThat(dto.choScore()).isEqualTo(3);
        assertThat(dto.hanScore()).isEqualTo(5);
        assertThat(dto.status()).isEqualTo(GameStatus.RUNNING.name());
    }

    @Test
    @DisplayName("저장되지 않은 게임을 조회하면 결과가 비어있다")
    void findByInvalidId() {
        // given
        // when
        final Optional<GameDto> found = repository.findById(connection, GameId.from(123123));

        // then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("진행 중 게임 목록 조회")
    void findAllRunning() {
        // given
        repository.save(connection, Turn.start(), Score.from(1), Score.from(2));
        repository.save(connection, Turn.from(5), Score.from(2), Score.from(2));

        // when
        final List<GameDto> all = repository.findAllRunning(connection);

        // then
        assertThat(all).hasSize(2);
    }

    @Test
    @DisplayName("게임 상태 변경")
    void updateStatus() {
        // given
        repository.save(connection, Turn.start(), Score.from(1), Score.from(2));
        final GameDto game = repository.findAllRunning(connection).getFirst();
        final int id = game.id();

        // when
        repository.updateStatusById(connection, GameId.from(game.id()), GameStatus.FINISHED);

        // then
        final Optional<GameDto> savedGame = repository.findById(connection, GameId.from(id));
        assertThat(savedGame).isPresent();

        final String savedStatus = savedGame.get().status();
        assertThat(savedStatus.equals(GameStatus.FINISHED.name())).isTrue();
    }
}
