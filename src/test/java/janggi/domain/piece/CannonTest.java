package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Dynasty;
import janggi.domain.Fixtures;
import janggi.domain.board.Point;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CannonTest {
    @DisplayName("포가 목적지까지 가는데 거치는 포인트를 알 수 있다")
    @ParameterizedTest
    @MethodSource("provideMovablePosition")
    void movePath(Point from, Point to, List<Point> expected) {
        //given
        Chariot ch = new Chariot(Dynasty.HAN);

        //when
        List<Point> points = ch.movePath(from, to);

        //then
        assertThat(points).isEqualTo(expected);
    }

    @DisplayName("이동 경로에 아무 기물도 없다면 이동 할 수 없다.")
    @Test
    void canMove_whenEmptyPiece() {
        //given
        PiecesOnPath piecesOnPath = new PiecesOnPath();
        Cannon cannon = new Cannon(Dynasty.HAN);

        //when
        boolean actual = cannon.canMove(piecesOnPath);

        //then
        assertThat(actual).isEqualTo(false);
    }

    @DisplayName("목적지가 아닌 이동 경로에 포가 아닌 기물이 하나 있다면 이동할 수 있다.")
    @Test
    void canMove_whenExistOnePieceWithoutDestination() {
        //given
        PiecesOnPath piecesOnPath = new PiecesOnPath(new HanSoldier(), new EmptyPiece());
        Cannon cannon = new Cannon(Dynasty.HAN);

        //when
        boolean actual = cannon.canMove(piecesOnPath);

        //then
        assertThat(actual).isEqualTo(true);
    }

    @DisplayName("목적지에 다른나라의 기물이 있고 중간 지점에 하나의 기물이 있다면 움직일 수 있다.")
    @Test
    void canMove_whenExistOnePieceWithoutDestination_and_notSameDynastyAtDestination() {
        //given
        PiecesOnPath piecesOnPath = new PiecesOnPath(new HanSoldier(), new Horse(Dynasty.CHU));
        Cannon cannon = new Cannon(Dynasty.HAN);

        //when
        boolean actual = cannon.canMove(piecesOnPath);

        //then
        assertThat(actual).isEqualTo(true);
    }

    @DisplayName("중간지점에 포가 있다면 움직일 수 없다.")
    @Test
    void cannotMove_whenExistCannonInMiddlePath() {
        //given
        PiecesOnPath piecesOnPath = new PiecesOnPath(new Cannon(Dynasty.HAN), new Horse(Dynasty.CHU));
        Cannon cannon = new Cannon(Dynasty.HAN);

        //when
        boolean actual = cannon.canMove(piecesOnPath);

        //then
        assertThat(actual).isEqualTo(false);
    }

    @DisplayName("목적지에 포가 있다면 움직일 수 없다.")
    @Test
    void cannotMove_whenExistCannonAtDestination() {
        //given
        PiecesOnPath piecesOnPath = new PiecesOnPath(new HanSoldier(), new Cannon(Dynasty.CHU));
        Cannon cannon = new Cannon(Dynasty.HAN);

        //when
        boolean actual = cannon.canMove(piecesOnPath);

        //then
        assertThat(actual).isEqualTo(false);
    }

    private static Stream<Arguments> provideMovablePosition() {
        return Stream.of(
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.FOUR_FIVE, List.of(Fixtures.FIVE_FIVE, Fixtures.FOUR_FIVE)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.SIX_ONE,
                        List.of(Fixtures.SIX_FOUR, Fixtures.SIX_THREE, Fixtures.SIX_TWO, Fixtures.SIX_ONE)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.SIX_NINE,
                        List.of(Fixtures.SIX_SIX, Fixtures.SIX_SEVEN, Fixtures.SIX_EIGHT, Fixtures.SIX_NINE)),
                Arguments.of(Fixtures.SIX_FIVE, Fixtures.EIGHT_FIVE, List.of(Fixtures.SEVEN_FIVE, Fixtures.EIGHT_FIVE))
        );
    }
}