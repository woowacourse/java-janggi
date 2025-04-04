package domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PositionTest {

    @ValueSource(strings = {"0,-1", "-1,0", "9,0", "0,10"})
    @ParameterizedTest
    @DisplayName("좌표 범위를 넘으면 예외를 발생시킨다")
    void test1(String str) {
        // given
        String[] split = str.split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);

        // when & then
        assertThatThrownBy(() -> Position.of(x, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 장기판 위치입니다.");
    }

    @Test
    @DisplayName("두 위치 사이의 거리를 반환한다")
    void test2() {
        // given
        Position current = Position.of(0, 0);

        // when
        double distance = current.calculateDistance(Position.of(3, 4));

        // then
        assertThat(distance).isEqualTo(5);
    }

    @ValueSource(strings = {"0,2", "2,0", "2,9", "8,2"})
    @ParameterizedTest
    @DisplayName("수평 혹은 수직 직선에 점이 위치해 있으면 참을 반환한다")
    void test3(String str) {
        // given
        String[] split = str.split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        Position position = Position.of(x, y);

        Position opposite = Position.of(2, 2);

        // when
        boolean b = position.isHorizontalOrVertical(opposite);

        // then
        assertThat(b).isTrue();
    }

    @ValueSource(strings = {"3,3", "1,1", "3,1", "1,3"})
    @ParameterizedTest
    @DisplayName("수평 혹은 수직 직선에 점이 위치해있지 않으면 거짓을 반환한다")
    void test4(String str) {
        // given
        String[] split = str.split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        Position position = Position.of(x, y);

        Position opposite = Position.of(2, 2);

        // when
        boolean b = position.isHorizontalOrVertical(opposite);

        // then
        assertThat(b).isFalse();
    }
}
