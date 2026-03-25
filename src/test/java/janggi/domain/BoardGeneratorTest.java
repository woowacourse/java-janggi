package janggi.domain;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardGeneratorTest {

    @Test
    @DisplayName("보드판 기물 정상 테스트")
    void success() {
        // given
        final int expected = 32;

        // when
        Board board = BoardGenerator.generate(new InnerElephantSetupPolicy());

        // then
        assertThat(board).extracting("positionPieceMap")
                .asInstanceOf(InstanceOfAssertFactories.MAP)
                .hasSize(expected);
    }
}
