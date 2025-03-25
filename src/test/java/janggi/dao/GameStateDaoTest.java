package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.TeamColor;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameStateDaoTest {

    private final GameStateDao gameStateDao = new GameStateDao();

    @DisplayName("진행중인 게임 중 가장 최근 게임id를 찾을 수 있다")
    @Test
    void getId_inProgressGame() {
        // given

        // when
        Optional<Integer> gameId = gameStateDao.getInProgressGameId();

        // then
        assertThat(gameId.get()).isEqualTo(1);
    }

    @DisplayName("처음 게임 상태를 저장할 수 있다")
    @Test
    void save_startGameState() {
        // given

        // when
        gameStateDao.saveStartGameState(TeamColor.RED);

        // then

    }
}
