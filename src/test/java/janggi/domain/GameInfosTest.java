package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GameInfosTest {
    @Test
    void 특정_게임_정보를_정확히_가져온다() {
        GameInfo game1 = new GameInfo(1, "테스트1", "날짜", "날짜", Side.CHO, 2);
        GameInfo game2 = new GameInfo(2, "테스트2", "날짜", "날짜", Side.HAN, 7);
        GameInfos gameInfos = new GameInfos(List.of(game1, game2));

        assertThat(gameInfos.getGameInfo(0)).isEqualTo(game1);
        assertThat(gameInfos.getGameInfo(1)).isEqualTo(game2);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 2, 10})
    void 잘못된_인덱스로_게임_정보를_가져오려고_하면_예외_발생(int index) {
        GameInfo game1 = new GameInfo(1, "테스트1", "날짜", "날짜", Side.CHO, 2);
        GameInfo game2 = new GameInfo(2, "테스트2", "날짜", "날짜", Side.HAN, 7);
        GameInfos gameInfos = new GameInfos(List.of(game1, game2));

        assertThatThrownBy(() -> gameInfos.getGameInfo(index))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 게임 번호입니다.");
    }
}
