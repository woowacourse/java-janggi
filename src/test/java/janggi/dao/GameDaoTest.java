package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.dto.GameDto;
import janggi.game.Game;
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
        GameDao.createGame(createdGame);

        GameDto sameGame = GameDao.findLastCreated();
    }


    @Test
    @DisplayName("게임의 턴을 수정한다.")
    void updateGameTurn() {
        GameDao.createGame(createdGame);
        createdGame.reverseTurn();

        GameDao.updateTurn(createdGame);
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