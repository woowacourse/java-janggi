package domain.position;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void 특정_좌표의_포지션을_생성한다() {
        // given
        Position position = new Position(1, 1);
        // when & then
        assertThat(position.row()).isEqualTo(1);
        assertThat(position.column()).isEqualTo(1);
    }

    @Test
    void 왼쪽_끝_열에서는_왼쪽으로_이동할_수_없다() {
        // given
        Position position = new Position(3, 0);
        // when & then
        assertThat(position.canMoveLeft()).isFalse();
    }

    @Test
    void 오른쪽_끝_열에서는_오른쪽으로_이동할_수_없다() {
        // given
        Position position = new Position(3, 8);
        // when & then
        assertThat(position.canMoveRight()).isFalse();
    }

    @Test
    void 맨_위_행에서는_위로_이동할_수_없다() {
        // given
        Position position = new Position(9, 4);
        // when & then
        assertThat(position.canMoveUp()).isFalse();
    }

    @Test
    void 맨_아래_행에서는_아래로_이동할_수_없다() {
        // given
        Position position = new Position(0, 4);
        // when & then
        assertThat(position.canMoveDown()).isFalse();
    }

    @Test
    void 맨_위_행이_아니면_위로_이동할_수_있다() {
        // given
        Position position = new Position(8, 4);
        // when & then
        assertThat(position.canMoveUp()).isTrue();
    }

    @Test
    void 맨_아래_행이_아니면_아래로_이동할_수_있다() {
        // given
        Position position = new Position(1, 4);
        // when & then
        assertThat(position.canMoveDown()).isTrue();
    }

    @Test
    void 왼쪽_끝_열이_아니면_왼쪽으로_이동할_수_있다() {
        // given
        Position position = new Position(3, 1);
        // when & then
        assertThat(position.canMoveLeft()).isTrue();
    }

    @Test
    void 오른쪽_끝_열이_아니면_오른쪽으로_이동할_수_있다() {
        // given
        Position position = new Position(3, 7);
        // when & then
        assertThat(position.canMoveRight()).isTrue();
    }
}
