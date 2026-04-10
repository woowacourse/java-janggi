package domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {

    @Test
    void 같은_행이면_가로직선상에_있다고_판단한다() {
        Position a = Position.of(1, 3);
        Position b = Position.of(1, 5);

        assertThat(a.sharesRowOrColumnWith(b)).isTrue();
    }

    @Test
    void 같은_열이면_세로직선상에_있다고_판단한다() {
        Position a = Position.of(0, 4);
        Position b = Position.of(2, 4);

        assertThat(a.sharesRowOrColumnWith(b)).isTrue();
    }

    @Test
    void 행과_열이_모두_다르면_가로세로_직선상에_있지_않다고_판단한다() {
        Position corner = Position.of(0, 3);
        Position center = Position.of(1, 4);

        assertThat(corner.sharesRowOrColumnWith(center)).isFalse();
    }

    @Test
    void 같은_칸은_행과_열을_공유하므로_직선상에_있다고_판단한다() {
        Position one = Position.of(1, 4);
        Position same = Position.of(1, 4);

        assertThat(one.sharesRowOrColumnWith(same)).isTrue();
    }
}
