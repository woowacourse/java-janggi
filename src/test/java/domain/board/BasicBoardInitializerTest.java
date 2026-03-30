package domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.board.strategy.MaSangSangMaStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasicBoardInitializerTest {

    @Test
    @DisplayName("시작시 장기 전체 기물 개수는 32개이다.")
    void boardInitializeTest() {
        // given
        BasicBoardInitializer basicBoardInitializer = new BasicBoardInitializer(new MaSangSangMaStrategy(Side.HAN), new MaSangSangMaStrategy(Side.CHU));

        // when - then
        assertThat(basicBoardInitializer.initialize().size()).isEqualTo(32);
    }
}
