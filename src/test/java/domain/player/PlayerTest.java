package domain.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import common.exception.JanggiException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PlayerTest {

    @ParameterizedTest
    @ValueSource(strings = {"janggiPlayer"})
    void 이름이_비어있지_않으면_플레이어가_정상_생성된다(String name) {
        Player player = new Player(new Name(name), Team.CHO);

        assertEquals(name, player.getProfile().nameValue());
        assertEquals(Team.CHO, player.getTeam());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void 이름이_빈값이면_예외가_발생한다(String name) {
        assertThrows(JanggiException.class, () -> new Player(new Name(name), Team.HAN));
    }
}
