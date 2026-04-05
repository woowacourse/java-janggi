package model.board;

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
}
