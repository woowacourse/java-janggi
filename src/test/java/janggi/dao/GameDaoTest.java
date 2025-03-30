package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

import janggi.dto.GameDto;
import janggi.exception.GameNotDeletedException;
import janggi.game.Game;
import janggi.game.Team;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameDaoTest {
    private Game createdGame;

    @BeforeEach
    void setUp() {
        createdGame = new Game();
        GameDao.createGame(createdGame);
    }
    @AfterEach
    void cleanUp() {
        try {
            GameDao.deleteGame(createdGame);
        } catch (GameNotDeletedException ignore) {
        }
    }

    @Test
    @DisplayName("데이터베이스 연결을 확인한다.")
    public void connection() {
        try (final var connection = JangiDatabase.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("하나의 게임 투플을 추가한다.")
    void createGameTuple() {
        GameDao.createGame(createdGame);
    }

    @Test
    @DisplayName("가장 최근에 만들어진 게임 투플을 조회한다.")
    void findGameLastCreated() {
        GameDto lastCreatedGame = GameDao.findLastCreated();

        assertThat(lastCreatedGame.createdAt())
                .isCloseTo(createdGame.getCreatedAt(), within(1, ChronoUnit.SECONDS));
    }


    @Test
    @DisplayName("게임의 턴을 수정한다.")
    void updateGameTurn() {
        createdGame.reverseTurn();

        GameDao.updateTurn(createdGame);

        assertThat(GameDao.findLastCreated().turn()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("게임 객체를 삭제한다.")
    void deleteGame() {
        GameDao.createGame(createdGame);

        GameDao.deleteGame(createdGame);

        assertThatThrownBy(() -> GameDao.deleteGame(createdGame))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("게임이 삭제되지 않았습니다.");
    }
}