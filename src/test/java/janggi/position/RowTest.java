package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.position.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RowTest {

    @DisplayName("displacement만큼 이동한다.")
    @Test
    void moved() {
        assertThat(Row.ONE.moved(3))
                .isEqualTo(Row.FOUR);

        assertThat(Row.FOUR.moved(-3))
                .isEqualTo(Row.ONE);
    }

    @DisplayName("displacement만큼 이동했을 때 보드 밖으로 나가면 예외가 발생한다.")
    @Test
    void moved_OUT() {
        assertThatThrownBy(() -> Row.NINE.moved(3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드 밖으로는 이동할 수 없습니다.");

        assertThatThrownBy(() -> Row.ONE.moved(-3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드 밖으로는 이동할 수 없습니다.");
    }


    @DisplayName("자신과 other 사이에 있는 row들을 반환한다.")
    @Test
    void to() {
        //given
        Row eight = Row.EIGHT;
        Row zero = Row.HAN_BACK;

        //when & then
        assertThat(eight.to(zero))
                .containsExactly(
                        Row.EIGHT,
                        Row.NINE,
                        Row.HAN_BACK
                );
    }

    @DisplayName("자신의 순서가 other보다 큰 경우에도 자신과 other 사이에 있는 row들을 반환한다.")
    @Test
    void to_smaller() {
        //given
        Row eight = Row.EIGHT;
        Row zero = Row.HAN_BACK;

        //when & then
        assertThat(zero.to(eight))
                .containsExactly(
                        Row.HAN_BACK,
                        Row.NINE,
                        Row.EIGHT
                );
    }

    @DisplayName("두 행 사이의 거리를 반환한다.")
    @Test
    void getDistance() {
        //given
        Row eight = Row.EIGHT;
        Row zero = Row.HAN_BACK;

        //when & then
        assertThat(eight.getDistance(zero))
                .isEqualTo(-2);
    }
}
