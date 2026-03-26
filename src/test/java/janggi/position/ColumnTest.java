package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColumnTest {

    @DisplayName("오른쪽으로 이동한다.")
    @Test
    void next() {
        Column column = Column.TWO;
        assertThat(column.next())
                .isEqualTo(Column.THREE);
    }

    @DisplayName("9번 열에서 오른쪽으로 예외가 발생한다.")
    @Test
    void next_OUT() {
        Column column = Column.NINE;
        assertThatThrownBy(column::next)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("보드 밖으로는 이동할 수 없습니다.");

    }

    @DisplayName("왼쪽으로 이동한다.")
    @Test
    void previous() {
        Column column = Column.TWO;
        assertThat(column.previous())
                .isEqualTo(Column.ONE);
    }

    @DisplayName("1번 열에서 왼쪽으로 이동하면 예외가 발생한다.")
    @Test
    void previous_OUT() {
        Column column = Column.ONE;
        assertThatThrownBy(column::previous)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("보드 밖으로는 이동할 수 없습니다.");
    }

    @DisplayName("자신과 other 사이에 있는 column들을 반환한다.")
    @Test
    void to() {
        //given
        Column seven = Column.SEVEN;
        Column nine = Column.NINE;

        //when & then
        assertThat(seven.to(nine))
                .containsExactly(
                        Column.SEVEN,
                        Column.EIGHT,
                        Column.NINE
                );
    }

    @DisplayName("자신의 순서가 other보다 큰 경우에도 자신과 other 사이에 있는 column들을 반환한다.")
    @Test
    void to_smaller() {
        //given
        Column nine = Column.NINE;
        Column seven = Column.SEVEN;

        //when & then
        assertThat(nine.to(seven))
                .containsExactly(
                        Column.NINE,
                        Column.EIGHT,
                        Column.SEVEN
                );
    }

    @DisplayName("두 열 사이의 거리를 반환한다.")
    @Test
    void getDistance() {
        //given
        Column eight = Column.EIGHT;
        Column zero = Column.SEVEN;

        //when & then
        assertThat(eight.getDistance(zero))
                .isEqualTo(1);
    }
}