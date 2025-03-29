package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Fixtures;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseTest {

    @DisplayName("말이 목적지까지 가는데 거치는 포인트를 알 수 있다")
    @ParameterizedTest
    @MethodSource("provideMovablePosition")
    void movePath(Point from, Point to, List<Point> expected) {
        //given
        Horse horse = new Horse(Dynasty.CHU);

        //when
        List<Point> points = horse.movePath(from, to);

        //then
        assertThat(points).isEqualTo(expected);
    }

    @DisplayName("이동 경로에 있는 기물들을 보고 이동할 수 있는지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("providePiecesOnPath")
    void canMove(PiecesOnPath piecesOnPath, boolean expected) {
        //given
        Horse horse = new Horse(Dynasty.CHU);

        //when
        boolean actual = horse.canMove(piecesOnPath);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideMovablePosition() {
        return Stream.of(
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.FOUR_FOUR, List.of(Fixtures.FIVE_FIVE, Fixtures.FOUR_FOUR)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.FOUR_SIX, List.of(Fixtures.FIVE_FIVE, Fixtures.FOUR_SIX)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.EIGHT_FOUR, List.of(Fixtures.SEVEN_FIVE, Fixtures.EIGHT_FOUR)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.EIGHT_SIX, List.of(Fixtures.SEVEN_FIVE, Fixtures.EIGHT_SIX))
        );
    }

    private static Stream<Arguments> providePiecesOnPath() {
        return Stream.of(
                Arguments.of(new PiecesOnPath(), true),
                Arguments.of(new PiecesOnPath(new EmptyPiece(), new EmptyPiece()), true),
                Arguments.of(new PiecesOnPath(new EmptyPiece(), new Horse(Dynasty.HAN)), true),
                Arguments.of(new PiecesOnPath(new EmptyPiece(), new Horse(Dynasty.CHU)), false),
                Arguments.of(new PiecesOnPath(new HanSoldier(), new EmptyPiece()), false)
        );
    }
}