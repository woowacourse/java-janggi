package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RowTest {

    @DisplayName("위로 이동한다.")
    @Test
    void previous() {
        Row row = Row.TWO;
        assertThat(row.previous())
                .isEqualTo(Row.ONE);
    }

    @DisplayName("1번 행에서 위로 이동하면 OUT으로 간다.")
    @Test
    void previous_OUT() {
        Row row = Row.ONE;
        assertThat(row.previous())
                .isEqualTo(Row.OUT);
    }

    @DisplayName("OUT에서 위로 이동하면 여전히 OUT이다.")
    @Test
    void previous_OUT_from_OUT() {
        Row row = Row.OUT;
        assertThat(row.previous())
                .isEqualTo(Row.OUT);
    }

    @DisplayName("아래로 이동한다.")
    @Test
    void next() {
        Row row = Row.TWO;
        assertThat(row.next())
                .isEqualTo(Row.THREE);
    }

    @DisplayName("0번 행에서 아래로 이동하면 OUT으로 간다.")
    @Test
    void next_OUT() {
        Row row = Row.ZERO;
        assertThat(row.next())
                .isEqualTo(Row.OUT);
    }

    @DisplayName("OUT에서 아래로 이동하면 여전히 OUT이다.")
    @Test
    void next_OUT_from_OUT() {
        Row row = Row.OUT;
        assertThat(row.next())
                .isEqualTo(Row.OUT);
    }

    @DisplayName("자신과 other 사이에 있는 row들을 반환한다.")
    @Test
    void to() {
        //given
        Row eight = Row.EIGHT;
        Row zero = Row.ZERO;

        //when & then
        assertThat(eight.to(zero))
                .containsExactly(
                        Row.EIGHT,
                        Row.NINE,
                        Row.ZERO
                );
    }

    @DisplayName("자신의 순서가 other보다 큰 경우에도 자신과 other 사이에 있는 row들을 반환한다.")
    @Test
    void to_smaller() {
        //given
        Row eight = Row.EIGHT;
        Row zero = Row.ZERO;

        //when & then
        assertThat(zero.to(eight))
                .containsExactly(
                        Row.ZERO,
                        Row.NINE,
                        Row.EIGHT
                );
    }

    @DisplayName("자신 또는 other이 out이면 예외가 발생한다.")
    @Test
    void to_out() {
        //given
        Row out = Row.OUT;
        Row zero = Row.ZERO;

        //when & then
        assertThatThrownBy(() -> out.to(zero))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드의 바깥 위치가 포함돼 있습니다.");
    }
}