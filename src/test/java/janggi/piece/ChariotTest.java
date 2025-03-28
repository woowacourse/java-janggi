package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChariotTest {
    @Test
    @DisplayName("차 전진 테스트")
    void chariotUpTest() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(7, 3));
        Position arrivedPosition = new Position(3, 3);
        //when
        chariot.move(arrivedPosition);
        //then
        assertThat(chariot.matchesPosition(new Position(3, 3))).isTrue();
    }

    @Test
    @DisplayName("차 전진2 테스트")
    void chariotUp2Test() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(10, 1));
        Position arrivedPosition = new Position(7, 1);
        //when
        chariot.move(arrivedPosition);
        //then
        assertThat(chariot.matchesPosition(new Position(7, 1))).isTrue();
    }

    @Test
    @DisplayName("차 후진 테스트")
    void chariotDownTest() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(7, 3));
        Position arrivedPosition = new Position(9, 3);
        //when
        chariot.move(arrivedPosition);
        //then
        assertThat(chariot.matchesPosition(new Position(9, 3))).isTrue();
    }

    @Test
    @DisplayName("차 우측 테스트")
    void chariotRightTest() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(7, 3));
        Position arrivedPosition = new Position(7, 8);
        //when
        chariot.move(arrivedPosition);
        //then
        assertThat(chariot.matchesPosition(new Position(7, 8))).isTrue();
    }

    @Test
    @DisplayName("차 좌측 테스트")
    void chariotLeftTest() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(7, 3));
        Position arrivedPosition = new Position(7, 1);
        //when
        chariot.move(arrivedPosition);
        //then
        assertThat(chariot.matchesPosition(new Position(7, 1))).isTrue();
    }

    @Test
    @DisplayName("차가 장기판 범위 밖 좌표로 이동할 경우 예외 발생")
    void outOfBoardTest() {
        //given
        Chariot chariot = new Chariot(Team.CHO, new Position(7, 6));
        Position arrivedPosition = new Position(7, 11);
        //when & then
        assertThatThrownBy(() -> chariot.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("makeChariotInPalaceForLeftUpCrossTest")
    @DisplayName("차 궁상 내 좌측 상단 대각선 이동 테스트")
    void moveChariotWithinPalaceToLeftUpCrossTest(Position chariotPosition, Position arrivedPosition) {
        //given
        Chariot chariot = new Chariot(Team.CHO, chariotPosition);
        //when
        chariot.move(arrivedPosition);
        //then
        assertThat(chariot.matchesPosition(arrivedPosition)).isTrue();
    }

    static Stream<Arguments> makeChariotInPalaceForLeftUpCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(10, 6), new Position(8,4)),
                Arguments.arguments(new Position(9, 5), new Position(8,4))
        );
    }

    @ParameterizedTest
    @MethodSource("makeExceptionChariotInPalaceForLeftUpCrossTest")
    @DisplayName("차 궁상 내 좌측 상단 대각선 이동 예외 테스트")
    void moveChariotWithinPalaceToLeftUpCrossExceptionTest(Position chariotPosition, Position arrivedPosition) {
        //given
        Chariot chariot = new Chariot(Team.CHO, chariotPosition);
        //when & then
        assertThatThrownBy(() -> chariot.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> makeExceptionChariotInPalaceForLeftUpCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(10, 4), new Position(9,3)),
                Arguments.arguments(new Position(10, 6), new Position(7,3))
        );
    }

    @ParameterizedTest
    @MethodSource("makeChariotInPalaceForRightUpCrossTest")
    @DisplayName("차 궁상 내 우측 상단 대각선 이동 테스트")
    void moveChariotWithinPalaceToRightUpCrossTest(Position chariotPosition, Position arrivedPosition) {
        //given
        Chariot chariot = new Chariot(Team.CHO, chariotPosition);
        //when
        chariot.move(arrivedPosition);
        //then
        assertThat(chariot.matchesPosition(arrivedPosition)).isTrue();
    }

    static Stream<Arguments> makeChariotInPalaceForRightUpCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(10, 4), new Position(8,6)),
                Arguments.arguments(new Position(9, 5), new Position(8,6))
        );
    }

    @ParameterizedTest
    @MethodSource("makeExceptionChariotInPalaceForRightUpCrossTest")
    @DisplayName("차 궁상 내 우측 상단 대각선 이동 예외 테스트")
    void moveChariotWithinPalaceToRightCrossExceptionTest(Position chariotPosition, Position arrivedPosition) {
        //given
        Chariot chariot = new Chariot(Team.CHO, chariotPosition);
        //when & then
        assertThatThrownBy(() -> chariot.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> makeExceptionChariotInPalaceForRightUpCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(10, 4), new Position(9, 3)),
                Arguments.arguments(new Position(10, 4), new Position(7, 7))
        );
    }


    @ParameterizedTest
    @MethodSource("makeChariotInPalaceForLeftDownCrossTest")
    @DisplayName("차 궁상 내 좌측 하단 대각선 이동 테스트")
    void moveChariotWithinPalaceToLeftDownCrossTest(Position chariotPosition, Position arrivedPosition) {
        //given
        Chariot chariot = new Chariot(Team.CHO, chariotPosition);
        //when
        chariot.move(arrivedPosition);
        //then
        assertThat(chariot.matchesPosition(arrivedPosition)).isTrue();
    }

    static Stream<Arguments> makeChariotInPalaceForLeftDownCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(8, 6), new Position(10,6)),
                Arguments.arguments(new Position(9, 5), new Position(10,6))
        );
    }

    @ParameterizedTest
    @MethodSource("makeExceptionChariotInPalaceForLeftDownCrossTest")
    @DisplayName("차 궁상 내 좌측 하단 대각선 이동 예외 테스트")
    void moveChariotWithinPalaceToLeftDownCrossExceptionTest(Position chariotPosition, Position arrivedPosition) {
        //given
        Chariot chariot = new Chariot(Team.CHO, chariotPosition);
        //when & then
        assertThatThrownBy(() -> chariot.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> makeExceptionChariotInPalaceForLeftDownCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(8, 6), new Position(9,3)),
                Arguments.arguments(new Position(10, 6), new Position(11,3))
        );
    }

    @ParameterizedTest
    @MethodSource("makeChariotInPalaceForRightDownCrossTest")
    @DisplayName("차 궁상 내 우측 하단 대각선 이동 테스트")
    void moveChariotWithinPalaceToRightDownCrossTest(Position chariotPosition, Position arrivedPosition) {
        //given
        Chariot chariot = new Chariot(Team.CHO, chariotPosition);
        //when
        chariot.move(arrivedPosition);
        //then
        assertThat(chariot.matchesPosition(arrivedPosition)).isTrue();
    }

    static Stream<Arguments> makeChariotInPalaceForRightDownCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(8, 4), new Position(10,6)),
                Arguments.arguments(new Position(9, 5), new Position(10,6))
        );
    }

    @ParameterizedTest
    @MethodSource("makeExceptionChariotInPalaceForRightDownCrossTest")
    @DisplayName("차 궁상 내 우측 하단 대각선 이동 예외 테스트")
    void moveChariotWithinPalaceToRightDownCrossExceptionTest(Position chariotPosition, Position arrivedPosition) {
        //given
        Chariot chariot = new Chariot(Team.CHO, chariotPosition);
        //when & then
        assertThatThrownBy(() -> chariot.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> makeExceptionChariotInPalaceForRightDownCrossTest() {
        return Stream.of(
                Arguments.arguments(new Position(8, 6), new Position(9, 7)),
                Arguments.arguments(new Position(8, 4), new Position(11, 7))
        );
    }

}
