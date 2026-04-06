package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PalaceTest {
    /**
     * CENTER: 중궁 (대각선 4방향 모두 이동 가능)
     * CORNER: 귀 (대각선 1방향 또는 중앙을 향하는 1방향 이동 가능)
     * EDGE: 귀와 중궁 사이의 십자(+) 위치 (대각선 이동 불가, 상하좌우만 가능)
     * 초
     * [3,0]: NE
     * [4,0]:
     * [5,0]: NW
     * [3,1]:
     * [4,1]: NW, NE, SW, SE
     * [5,1]:
     * [3,2]: SE
     * [4,2]:
     * [5,2]: SW
     * 한
     * [3, 7]: NE
     * [4, 8]:
     * [5, 9]: NW
     * [3, 7]:
     * [4, 8]: NW, NE, SW, SE
     * [5, 9]:
     * [3, 7]: SE
     * [4, 8]:
     * [5, 9]: SW
     **/

    @DisplayName("특정 좌표가 초나라 또는 한나라의 궁성 영역 내부인지 올바르게 판별한다.")
    @ParameterizedTest
    @MethodSource("providePalacePositions")
    void containsTrue(Position position, Side side) {
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
    void contains_False_OutsideBoundary(Position position, Side side) {
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
                Arguments.of(Position.of(2, 9), Side.CHO),
                Arguments.of(Position.of(2, 8), Side.CHO),
                Arguments.of(Position.of(2, 7), Side.CHO),
                Arguments.of(Position.of(2, 6), Side.CHO),
                Arguments.of(Position.of(3, 6), Side.CHO),
                Arguments.of(Position.of(4, 6), Side.CHO),
                Arguments.of(Position.of(5, 6), Side.CHO),
                Arguments.of(Position.of(6, 6), Side.CHO),
                Arguments.of(Position.of(6, 7), Side.CHO),
                Arguments.of(Position.of(6, 8), Side.CHO),
                Arguments.of(Position.of(6, 9), Side.CHO)
        );
    }

//    @Test
//    void 궁성_내의_특정_좌표가_주어졌을_때_해당_위치에서_이동_가능한_대각선_방향들을_반환한다() {
//
//    }
//
//    @Test
//    void 궁성_영역_밖의_좌표에_대해서는_대각선_방향을_반환하지_않는다() {
//
//    }
}
