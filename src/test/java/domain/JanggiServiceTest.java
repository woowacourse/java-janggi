package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.dao.MemoryGameDao;
import domain.dao.MemoryPieceDao;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

public class JanggiServiceTest {

    @Test
    void 게임방_이름으로_게임방을_찾을_수_있다() {
        // given
        HashMap<String, JanggiGame> games = new HashMap<>();
        JanggiGame game = JanggiGame.init(1L, JanggiBoard.create(List.of()));
        games.put("room1", game);
        JanggiService janggiService = new JanggiService(new MemoryGameDao(games), new MemoryPieceDao(new HashMap<>()));
        // when
        JanggiGame findGame = janggiService.findGameByName("room1");
        // then
        assertThat(findGame).isEqualTo(game);
    }
}
