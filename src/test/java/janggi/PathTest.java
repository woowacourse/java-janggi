package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PathTest {

    @DisplayName("빈 경로는 예외가 발생한다.")
    @Test
    void emptyPath() {
        assertThatThrownBy(() -> new Path(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 경로입니다.");
    }

    @DisplayName("가장 마지막에 있는 위치를 반환한다.")
    @Test
    void getDestination() {
        //given
        Path path = new Path(List.of(
                new Position(Row.ZERO, Column.EIGHT),
                new Position(Row.ONE, Column.EIGHT),
                new Position(Row.ONE, Column.SEVEN)
        ));

        //when & then
        assertThat(path.getDestination())
                .isEqualTo(new Position(Row.ONE, Column.SEVEN));
    }

    @DisplayName("가장 마지막 위치를 제한 나머지 위치들을 반환한다.")
    @Test
    void getCourse() {
        //given
        Path path = new Path(List.of(
                new Position(Row.ZERO, Column.EIGHT),
                new Position(Row.ONE, Column.EIGHT),
                new Position(Row.ONE, Column.SEVEN)
        ));

        //when & then
        assertThat(path.getCourse().getDestination())
                .isEqualTo(new Position(Row.ONE, Column.EIGHT));
    }
}