package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceInitializerTest {

    @DisplayName("게임에 필요한 32개의 기물을 생성한다.")
    @Test
    void generate() {
        //given
        final PieceInitializer pieceInitializer = new PieceInitializer();

        //when
        final Board actual = pieceInitializer.generate();

        //then
        assertThat(actual.getJanggiBoard()).hasSize(32);

    }
}
