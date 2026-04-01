package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    @DisplayName("게임 정상 생성")
    void shouldCreateGameCorrectly() {
        // given
        Board board = BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT, Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT);

        // when
        Game game = Game.of(board);

        // then
        Assertions.assertEquals(game.getBoard(), board);
    }
}
