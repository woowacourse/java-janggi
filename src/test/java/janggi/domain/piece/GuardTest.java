package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Fixtures;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GuardTest {

    @DisplayName("사는 궁성 내부에서 상하좌우, 대각선 한칸만 이동가능하다.")
    @ParameterizedTest
    @MethodSource("provideMovablePositionInPalace")
    void movePath(Point from, Point to, List<Point> expectedMovePath) {
        //given
        Guard guard = new Guard(Dynasty.HAN);

        //when
        List<Point> movePaths = guard.movePath(from, to);

        //then
        Assertions.assertThat(movePaths).isEqualTo(expectedMovePath);
    }

    @DisplayName("사는 궁성 밖으로 이동하지 못한다.")
    @Test
    void movePath_canNotMoveOutPalace() {
        //given
        Point from = Fixtures.ONE_FOUR;
        Point to = Fixtures.ONE_THREE;
        Guard guard = new Guard(Dynasty.CHU);

        //when & then
        Assertions.assertThatThrownBy(() -> guard.movePath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 목적지입니다.");
    }

    @DisplayName("이동 경로에 기물이 있다면 갈 수 없다.")
    @ParameterizedTest
    @MethodSource("providePiecesOnPath")
    void canMove(PiecesOnPath piecesOnPath, boolean expected) {
        //given
        Guard guard = new Guard(Dynasty.CHU);

        //when
        boolean actual = guard.canMove(piecesOnPath);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideMovablePositionInPalace() {
        return Stream.of(
                Arguments.of(Fixtures.THREE_SIX, Fixtures.TWO_FIVE, List.of(Fixtures.TWO_FIVE)),
                Arguments.of(Fixtures.THREE_FOUR, Fixtures.TWO_FIVE, List.of(Fixtures.TWO_FIVE)),
                Arguments.of(Fixtures.TWO_FIVE, Fixtures.ONE_SIX, List.of(Fixtures.ONE_SIX)),
                Arguments.of(Fixtures.TWO_FIVE, Fixtures.ONE_FOUR, List.of(Fixtures.ONE_FOUR))
        );
    }

    private static Stream<Arguments> providePiecesOnPath() {
        return Stream.of(
                Arguments.of(new PiecesOnPath(), true),
                Arguments.of(new PiecesOnPath(new EmptyPiece()), true),
                Arguments.of(new PiecesOnPath(new Horse(Dynasty.HAN)), true),
                Arguments.of(new PiecesOnPath(new Horse(Dynasty.CHU)), false)
        );
    }
}