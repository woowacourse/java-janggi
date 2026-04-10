package repository;

import config.TestDataSourceConfig;
import domain.piece.Side;
import janggigame.GameMetaData;
import janggigame.JanggiGameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.jdbc.JdbcJanggiGameRepository;
import util.SchemaInitializer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcJanggiGameRepositoryTest {

    JdbcJanggiGameRepository jdbcJanggiGameRepository;

    @BeforeEach
    void setUp() {
        DataSource dataSource = TestDataSourceConfig.testDataSource();
        SchemaInitializer.initialize(dataSource);
        jdbcJanggiGameRepository = new JdbcJanggiGameRepository(dataSource);
    }

    @Test
    @DisplayName("새 게임의 기본 status와 currentTurn은 각각 'WAITING_HAN_PLACEMENT'와 'CHO'이다.")
    void save_새_게임을_저장할_수_있다() {
        GameMetaData gameMetaData = GameMetaData.newGame();

        assertThat(gameMetaData).isNotNull();
        assertThat(gameMetaData.currentTurn()).isEqualTo(Side.CHO);
        assertThat(gameMetaData.status()).isEqualTo(JanggiGameStatus.WAITING_HAN_PLACEMENT);
    }

    @Test
    @DisplayName("최근에 저장된 종료되지 않은 게임을 불러올 수 있다.")
    void findLatestUnfinishedGame_테스트() {
        GameMetaData savedGameMetaData = jdbcJanggiGameRepository.save(GameMetaData.newGame());

        GameMetaData findedGameMetaData = jdbcJanggiGameRepository.findLatestUnfinishedGame().orElseThrow();

        assertThat(findedGameMetaData).isNotNull();
        assertThat(findedGameMetaData.id()).isEqualTo(savedGameMetaData.id());
        assertThat(findedGameMetaData.currentTurn()).isEqualTo(savedGameMetaData.currentTurn());
        assertThat(findedGameMetaData.status()).isEqualTo(savedGameMetaData.status());
    }

    @Test
    @DisplayName("게임의 진행 상태를 변경할 수 있다.")
    void updateGameStatusById_테스트() {
        GameMetaData savedGameMetaData = jdbcJanggiGameRepository.save(GameMetaData.newGame());

        jdbcJanggiGameRepository.updateGameStatusById(savedGameMetaData.id(), JanggiGameStatus.IN_PROGRESS);

        GameMetaData updatedGameMetaData = jdbcJanggiGameRepository.findLatestUnfinishedGame().orElseThrow();
        assertThat(updatedGameMetaData.status()).isEqualTo(JanggiGameStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("메모리에서 바뀐 양쪽 진영의 장군카운트의 값을 DB에 저장할 수 있다.")
    void updateJangGunCountById_테스트() throws SQLException {
        // given
        GameMetaData savedGameMetaData = jdbcJanggiGameRepository.save(GameMetaData.newGame());
        Map<Side, Integer> jangGunCount = new HashMap<>();
        jangGunCount.put(Side.CHO, 0);
        jangGunCount.put(Side.HAN, 2);

        // when
        jdbcJanggiGameRepository.updateJangGunCountById(jangGunCount, savedGameMetaData.id());

        // then
        try (Connection connection = TestDataSourceConfig.testDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT cho_janggun_count, han_janggun_count FROM game WHERE id = ?")) {

            statement.setLong(1, savedGameMetaData.id());

            try (ResultSet rs = statement.executeQuery()) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getInt("cho_janggun_count")).isEqualTo(0);
                assertThat(rs.getInt("han_janggun_count")).isEqualTo(2);
            }
        }
    }

    @Test
    @DisplayName("초기 상태의 턴은 'CHO'이며, 턴이 변경되면 DB에 'HAN'으로 수정할 수 있다.")
    void updateTurnById_테스트() {
        GameMetaData gameMetaData = jdbcJanggiGameRepository.save(GameMetaData.newGame());

        jdbcJanggiGameRepository.updateTurnById(Side.HAN, gameMetaData.id());
        GameMetaData updatedGameMetaData = jdbcJanggiGameRepository.findLatestUnfinishedGame().orElseThrow();

        assertThat(updatedGameMetaData.currentTurn()).isEqualTo(Side.HAN);
    }
}
