package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.space.Direction;
import janggi.domain.space.Palace;
import janggi.domain.space.Position;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PalaceTest {

    @DisplayName("특정 좌표가 초나라 또는 한나라의 궁성 영역 내부인지 올바르게 판별한다.")
    @ParameterizedTest
    @MethodSource("providePalacePositions")
    void isInsideTrue(Position position, Side side) {
        assertThat(Palace.isInside(position, side)).isTrue();
    }

    private static Stream<Arguments> providePalacePositions() {
        return Stream.of(
                // 초
                Arguments.of(Position.of(3, 0), Side.CHO),
                Arguments.of(Position.of(4, 0), Side.CHO),
                Arguments.of(Position.of(5, 0), Side.CHO),
                Arguments.of(Position.of(3, 1), Side.CHO),
                Arguments.of(Position.of(4, 1), Side.CHO),
                Arguments.of(Position.of(5, 1), Side.CHO),
                Arguments.of(Position.of(3, 2), Side.CHO),
                Arguments.of(Position.of(4, 2), Side.CHO),
                Arguments.of(Position.of(5, 2), Side.CHO),

                // 한
                Arguments.of(Position.of(3, 7), Side.HAN),
                Arguments.of(Position.of(4, 8), Side.HAN),
                Arguments.of(Position.of(5, 9), Side.HAN),
                Arguments.of(Position.of(3, 7), Side.HAN),
                Arguments.of(Position.of(4, 8), Side.HAN),
                Arguments.of(Position.of(5, 9), Side.HAN),
                Arguments.of(Position.of(3, 7), Side.HAN),
                Arguments.of(Position.of(4, 8), Side.HAN),
                Arguments.of(Position.of(5, 9), Side.HAN)
        );
    }

    @DisplayName("궁성 경계 바로 밖의 좌표는 궁성 내부가 아닌 것으로 판별한다.")
    @ParameterizedTest
    @MethodSource("provideOutsideBoundaryPositions")
    void isInsideFalse(Position position, Side side) {
        assertThat(Palace.isInside(position, side)).isFalse();
    }

    private static Stream<Arguments> provideOutsideBoundaryPositions() {
        return Stream.of(
                // 초
                Arguments.of(Position.of(2, 0), Side.CHO),
                Arguments.of(Position.of(2, 1), Side.CHO),
                Arguments.of(Position.of(2, 2), Side.CHO),
                Arguments.of(Position.of(2, 3), Side.CHO),
                Arguments.of(Position.of(3, 3), Side.CHO),
                Arguments.of(Position.of(4, 3), Side.CHO),
                Arguments.of(Position.of(5, 3), Side.CHO),
                Arguments.of(Position.of(6, 3), Side.CHO),
                Arguments.of(Position.of(6, 2), Side.CHO),
                Arguments.of(Position.of(6, 1), Side.CHO),
                Arguments.of(Position.of(6, 0), Side.CHO),
                // 한
                Arguments.of(Position.of(2, 9), Side.HAN),
                Arguments.of(Position.of(2, 8), Side.HAN),
                Arguments.of(Position.of(2, 7), Side.HAN),
                Arguments.of(Position.of(2, 6), Side.HAN),
                Arguments.of(Position.of(3, 6), Side.HAN),
                Arguments.of(Position.of(4, 6), Side.HAN),
                Arguments.of(Position.of(5, 6), Side.HAN),
                Arguments.of(Position.of(6, 6), Side.HAN),
                Arguments.of(Position.of(6, 7), Side.HAN),
                Arguments.of(Position.of(6, 8), Side.HAN),
                Arguments.of(Position.of(6, 9), Side.HAN)
        );
    }

    @DisplayName("궁성 경계 안이지만 다른 진영의 경계 안일 경우 궁성 내부가 아닌 것으로 판별한다.")
    @Test
    void isInsideFalse_whenOtherSide() {
        assertThat(Palace.isInside(Position.of(3, 0), Side.HAN)).isFalse();
        assertThat(Palace.isInside(Position.of(3, 7), Side.CHO)).isFalse();
    }

    @DisplayName("궁성 내의 특정 좌표가 주어졌을 때, 해당 위치에서 이동 가능한 대각선 방향(Direction)들을 반환한다.")
    @ParameterizedTest
    @MethodSource("provideIsInPalacePositionsAndDirections")
    void getDiagonalsInsideTest(Position position, List<Direction> expected) {
        // when
        List<Direction> actual = Palace.getDiagonals(position);

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> provideIsInPalacePositionsAndDirections() {
        return Stream.of(
                // 초
                Arguments.of(Position.of(3, 0), List.of(Direction.NORTH_EAST)),
                Arguments.of(Position.of(5, 0), List.of(Direction.NORTH_WEST)),
                Arguments.of(Position.of(3, 2), List.of(Direction.SOUTH_EAST)),
                Arguments.of(Position.of(5, 2), List.of(Direction.SOUTH_WEST)),
                Arguments.of(Position.of(4, 1), List.of(
                                Direction.NORTH_WEST, Direction.NORTH_EAST,
                                Direction.SOUTH_WEST, Direction.SOUTH_EAST)),
                Arguments.of(Position.of(4, 0), Collections.emptyList()),
                Arguments.of(Position.of(3, 1), Collections.emptyList()),
                Arguments.of(Position.of(5, 1), Collections.emptyList()),
                Arguments.of(Position.of(4, 2), Collections.emptyList()),

                // 한
                Arguments.of(Position.of(3, 7), List.of(Direction.NORTH_EAST)),
                Arguments.of(Position.of(5, 7), List.of(Direction.NORTH_WEST)),
                Arguments.of(Position.of(3, 9), List.of(Direction.SOUTH_EAST)),
                Arguments.of(Position.of(5, 9), List.of(Direction.SOUTH_WEST)),
                Arguments.of(Position.of(4, 8), List.of(
                                        Direction.NORTH_WEST, Direction.NORTH_EAST,
                                        Direction.SOUTH_WEST, Direction.SOUTH_EAST)),
                Arguments.of(Position.of(4, 7), Collections.emptyList()),
                Arguments.of(Position.of(3, 8), Collections.emptyList()),
                Arguments.of(Position.of(5, 8), Collections.emptyList()),
                Arguments.of(Position.of(4, 9), Collections.emptyList())
        );
    }

    @DisplayName("궁성 영역 밖의 좌표에 대해서는 대각선 방향을 반환하지 않는다(빈 리스트 반환).")
    @ParameterizedTest
    @MethodSource("provideIsNotInPalacePositionsAndDirections")
    void getDiagonalsOutsideTest(Position position, List<Direction> expected) {
        // when
        List<Direction> actual = Palace.getDiagonals(position);

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> provideIsNotInPalacePositionsAndDirections() {
        return Stream.of(
                Arguments.of(Position.of(4, 0), Collections.emptyList()),
                Arguments.of(Position.of(3, 1), Collections.emptyList()),
                Arguments.of(Position.of(5, 1), Collections.emptyList()),
                Arguments.of(Position.of(4, 2), Collections.emptyList()),

                Arguments.of(Position.of(4, 7), Collections.emptyList()),
                Arguments.of(Position.of(3, 8), Collections.emptyList()),
                Arguments.of(Position.of(5, 8), Collections.emptyList()),
                Arguments.of(Position.of(4, 9), Collections.emptyList())
        );
    }
}
