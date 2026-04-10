package janggi.repository.game;

import static janggi.config.DatabaseManager.withTransaction;
import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.config.DatabaseManager;
import janggi.config.DdlAuto;
import janggi.config.TestConfig;
import janggi.domain.game.GameState;
import janggi.entity.GameEntity;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JdbcGameRepositoryTest {

    private final GameRepository gameRepository = new JdbcGameRepository();

    @BeforeAll
    static void setUp() {
        TestConfig.setUp();
    }

    @BeforeEach
    void setUpDatabase() {
        DatabaseManager.initTable(DdlAuto.CREATE_DROP);
    }

    @Test
    void 게임을_올바르게_저장한다() {
        // given
        Long gameId = withTransaction(connection ->
                gameRepository.save(connection, GameEntity.toEntity(CHO, GameState.PLAYING))
        );

        // when
        Optional<GameEntity> result = gameRepository.findById(gameId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().currentTurn()).isEqualTo(CHO.name());
        assertThat(result.get().gameState()).isEqualTo(GameState.PLAYING.name());
    }

    @Test
    void 게임의_차례와_상태를_올바르게_변경할_수_있다() {
        // given
        Long gameId = withTransaction(connection ->
                gameRepository.save(connection, GameEntity.toEntity(CHO, GameState.PLAYING))
        );

        // when
        DatabaseManager.withTransaction(connection -> {
            gameRepository.update(connection, gameId, GameEntity.toEntity(HAN, GameState.PLAYING));
            return null;
        });
        Optional<GameEntity> result = gameRepository.findById(gameId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().currentTurn()).isEqualTo(HAN.name());
        assertThat(result.get().gameState()).isEqualTo(GameState.PLAYING.name());
    }

    @Test
    void 게임_상태를_올바르게_변경하고_승자를_판단할_수_있다() {
        // given
        Long gameId = withTransaction(connection ->
                gameRepository.save(connection, GameEntity.toEntity(CHO, GameState.PLAYING))
        );

        // when
        DatabaseManager.withTransaction(connection -> {
            gameRepository.update(connection, gameId, GameEntity.toEntity(HAN, GameState.CHO_WIN));
            return null;
        });
        Optional<GameEntity> result = gameRepository.findById(gameId);

        // then
        assertThat(result).isPresent();
        assertThat(GameState.valueOf(result.get().gameState()).winner()).isEqualTo(CHO);
    }

    @Test
    void 특정_게임_상태를_가지는_게임들을_조회할_수_있다() {
        // given
        Long firstPlayingGameId = DatabaseManager.withTransaction(connection ->
                gameRepository.save(connection, GameEntity.toEntity(CHO, GameState.PLAYING))
        );

        Long secondPlayingGameId = DatabaseManager.withTransaction(connection ->
                gameRepository.save(connection, GameEntity.toEntity(HAN, GameState.PLAYING))
        );

        Long finishedGameId = DatabaseManager.withTransaction(connection ->
                gameRepository.save(connection, GameEntity.toEntity(CHO, GameState.CHO_WIN))
        );

        // when
        List<Long> playingGameIds = gameRepository.findAllByState(GameState.PLAYING);

        // then
        assertThat(playingGameIds).containsExactly(firstPlayingGameId, secondPlayingGameId);
        assertThat(playingGameIds).doesNotContain(finishedGameId);
    }

}
