package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import janggi.TestDataInitializer;
import janggi.config.TestDataSourceConfig;
import janggi.domain.game.GameRoom;
import janggi.domain.game.GameStatus;
import janggi.domain.piece.camp.CampType;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameRoomRepositoryTest {

    private GameRoomRepository gameRoomRepository;

    @BeforeEach
    void setUp() {
        DataSource dataSource = TestDataSourceConfig.getDataSource();
        TestDataInitializer.initialize(dataSource);
        gameRoomRepository = new GameRoomRepository(dataSource);
    }

    @Test
    void 새_게임방을_저장한다() {
        // given
        GameRoom gameRoom = GameRoom.create();
        // when
        long gameRoomId = gameRoomRepository.save(gameRoom);
        // then
        GameRoom found = gameRoomRepository.findById(gameRoomId);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(found.getCurrentTurn()).isEqualTo(CampType.CHO);
            softly.assertThat(found.getGameStatus()).isEqualTo(GameStatus.PLAYING);
        });
    }

    @Test
    void 특정_게임_상태를_가진_게임_아이디를_조회한다() {
        // when
        List<Long> result = gameRoomRepository.findAllByGameStatus(GameStatus.PLAYING);
        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void 게임_아이디를_통해_게임을_조회한다() {
        // given
        GameRoom gameRoom = GameRoom.create();
        long gameRoomId = gameRoomRepository.save(gameRoom);
        // when
        GameRoom result = gameRoomRepository.findById(gameRoomId);
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.getCurrentTurn()).isEqualTo(CampType.CHO);
            softly.assertThat(result.getGameStatus()).isEqualTo(GameStatus.PLAYING);
        });
    }

    @Test
    void 게임_데이터를_변경한다() {
        // given
        GameRoom gameRoom = GameRoom.create();
        long gameRoomId = gameRoomRepository.save(gameRoom);
        GameRoom savedGameRoom = gameRoomRepository.findById(gameRoomId);
        // when
        savedGameRoom.changeTurn(CampType.HAN);
        gameRoomRepository.update(savedGameRoom);
        // then
        GameRoom result = gameRoomRepository.findById(gameRoomId);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.getCurrentTurn()).isEqualTo(CampType.HAN);
            softly.assertThat(result.getGameStatus()).isEqualTo(GameStatus.PLAYING);
        });
    }
}
