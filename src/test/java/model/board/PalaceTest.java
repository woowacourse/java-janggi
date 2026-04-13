package model.board;

import model.move.Move;
import model.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceTest {
    @Test
    void 초나라_궁성의_범위를_판단한다(){
        Palace palace = Palace.from(Country.CHO);

        assertThat(palace.contains(Position.of(9, 5))).isTrue();
        assertThat(palace.contains(Position.of(8, 4))).isTrue();
        assertThat(palace.contains(Position.of(10, 6))).isTrue();

        assertThat(palace.contains(Position.of(7, 5))).isFalse();
        assertThat(palace.contains(Position.of(9, 3))).isFalse();
        assertThat(palace.contains(Position.of(9, 7))).isFalse();
    }

    @Test
    void 한나라_궁성의_범위를_판단한다(){
        Palace palace = Palace.from(Country.HAN);

        assertThat(palace.contains(Position.of(2, 5))).isTrue();
        assertThat(palace.contains(Position.of(1, 4))).isTrue();
        assertThat(palace.contains(Position.of(3, 6))).isTrue();

        assertThat(palace.contains(Position.of(4, 5))).isFalse();
        assertThat(palace.contains(Position.of(2, 3))).isFalse();
        assertThat(palace.contains(Position.of(2, 7))).isFalse();
    }

    @Test
    void 궁성의_중앙과_꼭짓점_사이의_대각선_이동은_가능하다(){
        Palace palace = Palace.from(Country.CHO);
        Move move = Move.of(Position.of(9, 5), Position.of(8, 4));
        assertThat(palace.isDiagonalMove(move)).isTrue();
    }

    @Test
    void 궁성의_선으로_이어지지_않은_대각선_이동은_불가능(){
        Palace palace = Palace.from(Country.CHO);
        Move move = Move.of(Position.of(9, 4), Position.of(8, 5));
    }
}
