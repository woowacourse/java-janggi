package piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieceProperty.JanggiPieceInitializer;
import player.Pieces;

class PieceInitializerTest {

    @DisplayName("초나라 기물의 위치를 초기화 할 수 있다.")
    @Test
    void choInit() {
        //given
        JanggiPieceInitializer janggiPieceInitializer = new JanggiPieceInitializer();

        //when
        Pieces actual = janggiPieceInitializer.choInit();

        //then
        assertThat(actual.getPieces()).hasSize(16);
    }

    @DisplayName("한나라 기물의 위치를 초기화 할 수 있다.")
    @Test
    void hanInit() {
        //given
        JanggiPieceInitializer janggiPieceInitializer = new JanggiPieceInitializer();

        //when
        Pieces actual = janggiPieceInitializer.hanInit();

        //then
        assertThat(actual.getPieces()).hasSize(16);
    }
}
