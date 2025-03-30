package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Fixtures;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChuSoldierTest {

    @DisplayName("추나라 졸이 목적지까지 가는데 거치는 포인트를 알 수 있다")
    @ParameterizedTest
    @MethodSource("provideMovablePosition")
    void movePath(Point from, Point to, List<Point> expected) {
        //given
        ChuSoldier soldier = new ChuSoldier();

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
        ChuSoldier soldier = new ChuSoldier();

        //when
        boolean actual = soldier.canMove(piecesOnPath);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("초나라 졸은 궁성에서 윗방향 대각선으로 한칸 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideMovablePositionInPalace")
    void canMoveDiagonalInPalace(Point from, Point to, List<Point> expectedMovePaths) {
        //given
        ChuSoldier soldier = new ChuSoldier();

        //when
        List<Point> movePaths = soldier.movePath(from, to);

        //then
        assertThat(movePaths).isEqualTo(expectedMovePaths);
    }

    private static Stream<Arguments> provideMovablePosition() {
        return Stream.of(
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.SIX_FOUR, List.of(Fixtures.SIX_FOUR)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.SIX_SIX, List.of(Fixtures.SIX_SIX)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.FIVE_FIVE, List.of(Fixtures.FIVE_FIVE))
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

    private static Stream<Arguments> provideMovablePositionInPalace() {
        return Stream.of(
                Arguments.of(Fixtures.THREE_SIX, Fixtures.TWO_FIVE, List.of(Fixtures.TWO_FIVE)),
                Arguments.of(Fixtures.THREE_FOUR, Fixtures.TWO_FIVE, List.of(Fixtures.TWO_FIVE)),
                Arguments.of(Fixtures.TWO_FIVE, Fixtures.ONE_SIX, List.of(Fixtures.ONE_SIX)),
                Arguments.of(Fixtures.TWO_FIVE, Fixtures.ONE_FOUR, List.of(Fixtures.ONE_FOUR))
        );
    }
}