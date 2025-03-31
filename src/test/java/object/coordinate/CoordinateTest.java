package object.coordinate;

import static object.coordinate.CrossMoveVector.LEFT;
import static object.coordinate.CrossMoveVector.UP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CoordinateTest {

    @Test
    @DisplayName("가로, 세로 위치를 가진 좌표를 생성할 수 있다.")
    void test1() {
        // given

        // when
        Coordinate coordinate = new Coordinate(1, 1);

        // then
        assertThat(coordinate).isEqualTo(new Coordinate(1, 1));
    }

    @Test
    @DisplayName("가로 좌표가 0 이하이면 예외가 발생한다.")
    void test3() {
        // given

        // when & then
        assertThatThrownBy(() -> new Coordinate(0, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가로 좌표는 1에서 9사이여야 합니다.");
    }

    @Test
    @DisplayName("가로 좌표가 10 이상이면 예외가 발생한다.")
    void test4() {
        // given

        // when & then
        assertThatThrownBy(() -> new Coordinate(10, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가로 좌표는 1에서 9사이여야 합니다.");
    }

    @Test
    @DisplayName("세로 좌표가 0 이하이면 예외가 발생한다.")
    void test5() {
        // given

        // when & then
        assertThatThrownBy(() -> new Coordinate(1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("세로 좌표는 1에서 10사이여야 합니다.");
    }

    @Test
    @DisplayName("세로 좌표가 11 이상이면 예외가 발생한다.")
    void test6() {
        // given

        // when & then
        assertThatThrownBy(() -> new Coordinate(1, 11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("세로 좌표는 1에서 10사이여야 합니다.");
    }

    @Test
    @DisplayName("현재 좌표 기준으로 변화된 좌표를 구할 수 있다. (벡터 한 개)")
    void test7() {
        // given
        Coordinate coordinate = new Coordinate(5, 5);

        // when
        Coordinate changedCoordinate = coordinate.moveBy(UP);

        // then
        assertThat(changedCoordinate).isEqualTo(new Coordinate(5, 4));
    }

    @Test
    @DisplayName("현재 좌표 기준으로 변화된 좌표를 구할 수 있다. (벡터 여러 개)")
    void test8() {
        // given
        Coordinate coordinate = new Coordinate(5, 5);

        // when
        Coordinate changedCoordinate = coordinate.moveBy(List.of(UP, UP, LEFT));

        // then
        assertThat(changedCoordinate).isEqualTo(new Coordinate(4, 3));
    }

    @Test
    @DisplayName("현재 좌표가 궁성 내 좌표라면 true를 반환한다.")
    void test9() {
        // given
        Coordinate coordinate = new Coordinate(4, 1);

        // when
        boolean result = coordinate.isInCastle();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("현재 좌표가 궁성 내 좌표가 아니라면 false를 반환한다.")
    void test10() {
        // given
        Coordinate coordinate = new Coordinate(5, 5);

        // when
        boolean result = coordinate.isInCastle();

        // then
        assertThat(result).isFalse();
    }
}
