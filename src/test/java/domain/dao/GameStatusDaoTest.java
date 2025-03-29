package domain.dao;

import static org.assertj.core.api.Assertions.assertThat;

import domain.GameStatus;
import domain.piece.TeamType;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameStatusDaoTest {

    @Test
    @DisplayName("턴을 저장한다.")
    void saveTest() {
        GameStatusDao gameStatusDao = new GameStatusDao();
        GameStatus gameStatus = new GameStatus("a");
        gameStatusDao.save(gameStatus);
    }

    @Test
    @DisplayName("방 이름으로 조회한다.")
    void findGameStatusByRoomNameTest() {
        GameStatusDao gameStatusDao = new GameStatusDao();
        Optional<GameStatus> gameStatusOptional = gameStatusDao.findGameStatusByRoomName("a");
        assertThat(gameStatusOptional).isPresent();
        GameStatus gameStatus = gameStatusOptional.get();
        assertThat(gameStatus.getRoomName()).isEqualTo("a");
    }

    @Test
    @DisplayName("턴 변경 테스트")
    void updateTest(){
        GameStatusDao gameStatusDao = new GameStatusDao();
        gameStatusDao.updateTurn("a", TeamType.HAN);
    }
}