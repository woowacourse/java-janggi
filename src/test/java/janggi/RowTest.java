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

    @DisplayName("1번 행에서 위로 이동하면 예외가 발생한다.")
    @Test
    void previous_OUT() {
        Row row = Row.ONE;
        assertThatThrownBy(row::previous)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("보드 밖으로는 이동할 수 없습니다.");
    }

    @DisplayName("아래로 이동한다.")
    @Test
    void next() {
        Row row = Row.TWO;
        assertThat(row.next())
                .isEqualTo(Row.THREE);
    }

    @DisplayName("0번 행에서 아래로 이동하면 예외가 발생한다.")
    @Test
    void next_OUT() {
        Row row = Row.ZERO;
        assertThatThrownBy(row::next)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("보드 밖으로는 이동할 수 없습니다.");
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
}