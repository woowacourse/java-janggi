package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.dao.MemoryGameDao;
import domain.dao.MemoryGamesDao;
import domain.dao.MemoryPieceDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GameRoomsTest {

    @Test
    void 장기_게임방이_없는지_확인할_수_있다() {
        // given
        GameRooms gameRooms = new GameRooms(new MemoryGamesDao(new HashMap<>()));

        // when
        boolean isEmpty = gameRooms.isEmpty();

        // then
        assertThat(isEmpty).isTrue();
    }

    @Test
    void 장기_게임방이_있는지_확인할_수_있다() {
        // given
        Map<String, JanggiGame> games = new HashMap<>();
        games.put("방1", new JanggiGame(new MemoryGameDao(), JanggiBoard.of(new MemoryPieceDao(new ArrayList<>()))));
        GameRooms gameRooms = new GameRooms(new MemoryGamesDao(games));

        // when
        boolean isEmpty = gameRooms.isEmpty();

        // then
        assertThat(isEmpty).isFalse();
    }

    @Test
    void 이름으로_장기_게임방을_찾을_수_있다() {
        // given
        Map<String, JanggiGame> games = new HashMap<>();
        JanggiGame game = new JanggiGame(new MemoryGameDao(), JanggiBoard.of(new MemoryPieceDao(new ArrayList<>())));
        games.put("방1", game);
        GameRooms gameRooms = new GameRooms(new MemoryGamesDao(games));

        // when
        JanggiGame findGame = gameRooms.findByName("방1");

        // then
        assertThat(findGame).isEqualTo(game);
    }
}