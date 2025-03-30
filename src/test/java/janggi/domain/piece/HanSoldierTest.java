package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Fixtures;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HanSoldierTest {

    @DisplayName("한나라 졸이 목적지까지 가는데 거치는 포인트를 알 수 있다")
    @ParameterizedTest
    @MethodSource("provideMovablePosition")
    void movePath(Point from, Point to, List<Point> expected) {
        //given
        HanSoldier soldier = new HanSoldier();

        //when
        List<Point> points = soldier.movePath(from, to);

        //then
        assertThat(points).isEqualTo(expected);
    }

    @DisplayName("이동 경로에 있는 기물들을 보고 이동할 수 있는지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("providePiecesOnPath")
    void canMove(PiecesOnPath piecesOnPath, boolean expected) {
        //given
        HanSoldier soldier = new HanSoldier();

        //when
        boolean actual = soldier.canMove(piecesOnPath);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("한나라 졸은 궁성에서 윗방향 대각선으로 한칸 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideMovablePositionInPalace")
    void canMoveDiagonalInPalace(Point from, Point to, List<Point> expectedMovePaths) {
        //given
        HanSoldier soldier = new HanSoldier();

        //when
        List<Point> movePaths = soldier.movePath(from, to);

        //then
        assertThat(movePaths).isEqualTo(expectedMovePaths);
    }

    private static Stream<Arguments> provideMovablePosition() {
        return Stream.of(
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.SIX_FOUR, List.of(Fixtures.SIX_FOUR)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.SIX_SIX, List.of(Fixtures.SIX_SIX)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.SEVEN_FIVE, List.of(Fixtures.SEVEN_FIVE))
        );
    }

    private static Stream<Arguments> providePiecesOnPath() {
        return Stream.of(
                Arguments.of(new PiecesOnPath(), true),
                Arguments.of(new PiecesOnPath(new EmptyPiece()), true),
                Arguments.of(new PiecesOnPath(new Horse(Dynasty.CHU)), true),
                Arguments.of(new PiecesOnPath(new Horse(Dynasty.HAN)), false)
        );
    }

    private static Stream<Arguments> provideMovablePositionInPalace() {
        return Stream.of(
                Arguments.of(Fixtures.EIGHT_FOUR, Fixtures.NINE_FIVE, List.of(Fixtures.NINE_FIVE)),
                Arguments.of(Fixtures.EIGHT_SIX, Fixtures.NINE_FIVE, List.of(Fixtures.NINE_FIVE)),
                Arguments.of(Fixtures.NINE_FIVE, Fixtures.TEN_FOUR, List.of(Fixtures.TEN_FOUR)),
                Arguments.of(Fixtures.NINE_FIVE, Fixtures.TEN_SIX, List.of(Fixtures.TEN_SIX))
        );
    }
}