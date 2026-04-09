package janggi.repository.game;

import static janggi.config.DatabaseManager.withTransaction;
import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.config.DatabaseManager;
import janggi.config.DdlAuto;
import janggi.config.TestConfig;
import janggi.domain.game.GameState;
import janggi.entity.TurnEntity;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JdbcGameRepositoryTest {

    private final JdbcGameRepository gameRepository = new JdbcGameRepository();

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
                gameRepository.save(connection, TurnEntity.toEntity(CHO.name()))
        );

        // when
        Optional<TurnEntity> result = gameRepository.findByCurrentTurnById(gameId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().currentTurn()).isEqualTo(CHO.name());
    }

    @Test
    void 게임_차례를_올바르게_변경할_수_있다() {
        // given
        Long gameId = withTransaction(connection ->
                gameRepository.save(connection, TurnEntity.toEntity(CHO.name()))
        );

        // when
        DatabaseManager.withTransaction(connection -> {
            gameRepository.updateTurn(connection, gameId, TurnEntity.toEntity(HAN.name()));
            return null;
        });
        Optional<TurnEntity> result = gameRepository.findByCurrentTurnById(gameId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().currentTurn()).isEqualTo(HAN.name());
    }

    @Test
    void 게임_상태를_올바르게_변경하고_승자를_판단할_수_있다() {
        // given
        Long gameId = withTransaction(connection ->
                gameRepository.save(connection, TurnEntity.toEntity(CHO.name()))
        );

        // when
        DatabaseManager.withTransaction(connection -> {
            gameRepository.updateState(connection, gameId, GameState.CHO_WIN);
            return null;
        });
        Optional<GameState> result = gameRepository.findGameStateById(gameId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().winner()).isEqualTo(CHO);
    }

    @Test
    public void 특정_게임_상태를_가지는_게임들을_조회할_수_있다() {
        // given
        Long firstPlayingGameId = DatabaseManager.withTransaction(connection ->
                gameRepository.save(connection, TurnEntity.toEntity("CHO"))
        );

        Long secondPlayingGameId = DatabaseManager.withTransaction(connection ->
                gameRepository.save(connection, TurnEntity.toEntity("HAN"))
        );

        Long finishedGameId = DatabaseManager.withTransaction(connection -> {
            Long gameId = gameRepository.save(connection, TurnEntity.toEntity("CHO"));
            gameRepository.updateState(connection, gameId, GameState.CHO_WIN);
            return gameId;
        });

        // when
        List<Long> playingGameIds = gameRepository.findAllByState(GameState.PLAYING);

        // then
        assertThat(playingGameIds).containsExactly(firstPlayingGameId, secondPlayingGameId);
        assertThat(playingGameIds).doesNotContain(finishedGameId);
    }

}
