package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Dynasty;
import janggi.domain.Fixtures;
import janggi.domain.board.Point;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChariotTest {

    @DisplayName("차가 목적지까지 가는데 거치는 포인트를 알 수 있다")
    @ParameterizedTest
    @MethodSource("provideMovablePosition")
    void movePath(Point from, Point to, List<Point> expected) {
        //given
        Chariot chariot = new Chariot(Dynasty.HAN);

        //when
        List<Point> points = chariot.movePath(from, to);

        //then
        assertThat(points).isEqualTo(expected);
    }

    @DisplayName("이동 경로에 있는 기물들을 보고 이동할 수 있는지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("providePiecesOnPath")
    void canMove(PiecesOnPath piecesOnPath, boolean expected) {
        //given
        Chariot chariot = new Chariot(Dynasty.HAN);

        //when
        boolean actual = chariot.canMove(piecesOnPath);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideMovablePosition() {
        return Stream.of(
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.FOUR_FIVE, List.of(Fixtures.FIVE_FIVE, Fixtures.FOUR_FIVE)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.SIX_ONE,
                        List.of(Fixtures.SIX_FOUR, Fixtures.SIX_THREE, Fixtures.SIX_TWO, Fixtures.SIX_ONE)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.SIX_NINE,
                        List.of(Fixtures.SIX_SIX, Fixtures.SIX_SEVEN, Fixtures.SIX_EIGHT, Fixtures.SIX_NINE)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.EIGHT_FIVE, List.of(Fixtures.SEVEN_FIVE, Fixtures.EIGHT_FIVE)),
                Arguments.of(Fixtures.ONE_ONE, Fixtures.TEN_ONE,
                        List.of(Fixtures.TWO_ONE, Fixtures.THREE_ONE, Fixtures.FOUR_ONE, Fixtures.FIVE_ONE,
                                Fixtures.SIX_ONE, Fixtures.SEVEN_ONE, Fixtures.EIGHT_ONE, Fixtures.NINE_ONE,
                                Fixtures.TEN_ONE))
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
}