package repository.jdbc;

import config.TestDataSourceConfig;
import domain.piece.Side;
import domain.janggigame.Game;
import domain.janggigame.JangGunCount;
import domain.janggigame.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.SchemaInitializer;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcJanggiGameControllerRepositoryTest {

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
        Game game = Game.newGame();

        assertThat(game).isNotNull();
        assertThat(game.getCurrentTurnSide()).isEqualTo(Side.CHO);
        assertThat(game.getStatus()).isEqualTo(GameStatus.WAITING_HAN_PLACEMENT);
    }

    @Test
    @DisplayName("최근에 저장된 종료되지 않은 게임을 불러올 수 있다.")
    void findLatestUnfinishedGame_테스트() {
        Game savedGame = jdbcJanggiGameRepository.save(Game.newGame());

        Game findedGame = jdbcJanggiGameRepository.findLatestUnfinishedGame().orElseThrow();

        assertThat(findedGame).isNotNull();
        assertThat(findedGame.getId()).isEqualTo(savedGame.getId());
        assertThat(findedGame.getCurrentTurnSide()).isEqualTo(savedGame.getCurrentTurnSide());
        assertThat(findedGame.getStatus()).isEqualTo(savedGame.getStatus());
    }

    @Test
    @DisplayName("게임의 진행 상태를 변경할 수 있다.")
    void updateGameStatusById_테스트() {
        Game savedGame = jdbcJanggiGameRepository.save(Game.newGame());

        jdbcJanggiGameRepository.updateGameStatusById(savedGame.getId(), GameStatus.IN_PROGRESS);

        Game updatedGame = jdbcJanggiGameRepository.findLatestUnfinishedGame().orElseThrow();
        assertThat(updatedGame.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("메모리에서 바뀐 양쪽 진영의 장군카운트의 값을 DB에 저장할 수 있다.")
    void updateJangGunCountById_테스트() throws SQLException {
        // given
        Game savedGame = jdbcJanggiGameRepository.save(Game.newGame());
        JangGunCount jangGunCount = savedGame.getJangGunCount();
        jangGunCount.increment(Side.CHO);
        jangGunCount.increment(Side.CHO);

        // when
        jdbcJanggiGameRepository.updateJangGunCountById(savedGame.getId(), jangGunCount);

        // then
        int choJangGunCount = jangGunCount.getCount(Side.CHO);
        int hanJangGunCount = jangGunCount.getCount(Side.HAN);
        assertThat(choJangGunCount).isEqualTo(2);
        assertThat(hanJangGunCount).isEqualTo(0);
    }

    @Test
    @DisplayName("초기 상태의 턴은 'CHO'이며, 턴이 변경되면 DB에 'HAN'으로 수정할 수 있다.")
    void updateTurnById_테스트() {
        Game game = jdbcJanggiGameRepository.save(Game.newGame());

        jdbcJanggiGameRepository.updateTurnById(game.getId(), Side.HAN);
        Game updatedGame = jdbcJanggiGameRepository.findLatestUnfinishedGame().orElseThrow();

        assertThat(updatedGame.getCurrentTurnSide()).isEqualTo(Side.HAN);
    }
}
