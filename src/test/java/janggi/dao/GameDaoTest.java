package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.game.Game;
import janggi.game.Team;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameDaoTest {
    private Game createdGame = new Game();

    @AfterEach
    void cleanUp() {
        //TODO 데이터 정리
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
        Game game = GameDao.findLastCreated();
        assertThat(game.getTurn()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("게임 객체를 삭제한다.")
    void deleteGame() {
        GameDao gameDao = GameDao.createGame(createdGame);

        gameDao.deleteGame();
        assertThatThrownBy(gameDao::deleteGame)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("게임이 삭제되지 않았습니다.");
    }
}