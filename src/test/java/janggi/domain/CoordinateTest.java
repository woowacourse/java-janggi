package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.movement.MoveUnit;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

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
    @DisplayName("움직임을 받아 현재 좌표에서 움직일 수 있는 지 알 수 있다.")
    void test7() {
        // given
        Coordinate coordinate = new Coordinate(5, 5);

        // when
        boolean canMove = coordinate.canMove(MoveUnit.LEFT);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    @DisplayName("움직임을 받아 현재 좌표에서 움직인 좌표를 반환한다.")
    void test8() {
        // given
        Coordinate coordinate = new Coordinate(5, 5);

        // when
        Coordinate moved = coordinate.move(MoveUnit.LEFT_UP);

        // then
        assertThat(moved).isEqualTo(new Coordinate(4, 4));
    }

    @Test
    @DisplayName("움직임을 받아 현재 좌표에서 움직였을 때 범위를 벗어나면 예외가 발생한다.")
    void test9() {
        // given
        Coordinate coordinate = new Coordinate(1, 1);

        // when
        assertThatThrownBy(() -> coordinate.move(MoveUnit.UP))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("현재 좌표가 궁성 내부인 지 알 수 있다.")
    @CsvSource({
        "4,1","5,1","6,1",
        "4,2","5,2","6,2",
        "4,3","5,3","6,3",

        "4,8","5,8","6,8",
        "4,9","5,9","6,9",
        "4,10","5,10","6,10",
    })
    void test10(int x, int y) {
        //given
        Coordinate coordinate = new Coordinate(x, y);

        //when
        boolean inCastle = coordinate.isInCastle();

        //then
        assertThat(inCastle).isTrue();
    }

    @ParameterizedTest
    @DisplayName("현재 좌표와 연결되어 있는 좌표들을 반환한다.")
    @MethodSource("provideCoordinatesAndConnections")
    void test11(Coordinate coordinate, Set<Coordinate> coordinates) {
        //when
        Set<Coordinate> connections = coordinate.findCastleConnections();

        //then
        assertThat(connections).containsExactlyElementsOf(coordinates);
    }

    public static Stream<Arguments> provideCoordinatesAndConnections() {
        return Stream.of(
            Arguments.of(new Coordinate(4, 1), Set.of(new Coordinate(5, 2))),
            Arguments.of(new Coordinate(4, 3), Set.of(new Coordinate(5, 2))),
            Arguments.of(new Coordinate(6, 1), Set.of(new Coordinate(5, 2))),
            Arguments.of(new Coordinate(6, 3), Set.of(new Coordinate(5, 2))),
            Arguments.of(new Coordinate(5, 2), Set.of(new Coordinate(4,1), new Coordinate(4, 3), new Coordinate(6, 1), new Coordinate(6, 3))),

            Arguments.of(new Coordinate(4, 8), Set.of(new Coordinate(5, 9))),
            Arguments.of(new Coordinate(4, 10), Set.of(new Coordinate(5, 9))),
            Arguments.of(new Coordinate(6, 8), Set.of(new Coordinate(5, 9))),
            Arguments.of(new Coordinate(6, 10), Set.of(new Coordinate(5, 9))),
            Arguments.of(new Coordinate(5, 9), Set.of(new Coordinate(4, 8), new Coordinate(4, 10), new Coordinate(6, 8), new Coordinate(6, 10))
        ));
    }
}
