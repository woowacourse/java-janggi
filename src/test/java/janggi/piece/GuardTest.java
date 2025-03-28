package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GuardTest {
    @Test
    @DisplayName("사 전진 테스트")
    void guardForwardTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(10, 4));
        Position arrivedPosition = new Position(9, 4);
        //when
        guard.move(arrivedPosition);
        //then
        assertThat(guard.matchesPosition(new Position(9, 4))).isTrue();
    }

    @Test
    @DisplayName("사 후진 테스트")
    void guardBackTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(9, 4));
        Position arrivedPosition = new Position(10, 4);
        //when
        guard.move(arrivedPosition);
        //then
        assertThat(guard.matchesPosition(new Position(10, 4))).isTrue();
    }

    @Test
    @DisplayName("사 오른쪽 이동 테스트")
    void guardRightTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(10, 4));
        Position arrivedPosition = new Position(10, 5);
        //when
        guard.move(arrivedPosition);
        //then
        assertThat(guard.matchesPosition(new Position(10, 5))).isTrue();
    }

    @Test
    @DisplayName("사 왼쪽 이동 테스트")
    void guardLeftTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(10, 6));
        Position arrivedPosition = new Position(10, 5);
        //when
        guard.move(arrivedPosition);
        //then
        assertThat(guard.matchesPosition(new Position(10, 5))).isTrue();
    }

    @Test
    @DisplayName("사 오른쪽 위 이동 테스트")
    void guardRightUpTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(9, 5));
        Position arrivedPosition = new Position(8, 6);
        //when
        guard.move(arrivedPosition);
        //then
        assertThat(guard.matchesPosition(new Position(8, 6))).isTrue();
    }

    @Test
    @DisplayName("사 오른쪽 아래 이동 테스트")
    void guardRightDownTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(9, 5));
        Position arrivedPosition = new Position(10, 6);
        //when
        guard.move(arrivedPosition);
        //then
        assertThat(guard.matchesPosition(new Position(10, 6))).isTrue();
    }

    @Test
    @DisplayName("사 왼쪽 위 이동 테스트")
    void guardLeftUpTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(9, 5));
        Position arrivedPosition = new Position(8, 4);
        //when
        guard.move(arrivedPosition);
        //then
        assertThat(guard.matchesPosition(new Position(8, 4))).isTrue();
    }

    @Test
    @DisplayName("사 왼쪽 아래 이동 테스트")
    void guardLeftDownTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(9, 5));
        Position arrivedPosition = new Position(10, 4);
        //when
        guard.move(arrivedPosition);
        //then
        assertThat(guard.matchesPosition(new Position(10, 4))).isTrue();
    }

    @Test
    @DisplayName("사 장기판 밖으로 이동 시 예외 발생 테스트")
    void outOfBoardExceptionTest() {
        //given
        Guard guard = new Guard(Team.CHO, new Position(10, 5));
        Position arrivedPosition = new Position(11, 6);
        //when & then
        assertThatThrownBy(() -> guard.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("makeOutOfPalacePosition")
    @DisplayName("사 궁성 밖으로 이동 시 예외 발생 테스트")
    void outOfPalaceExceptionTest(Piece guard, Position arrivedPosition) {
        assertThatThrownBy(() -> guard.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> makeOutOfPalacePosition() {
        return Stream.of(
                Arguments.arguments(new Guard(Team.CHO, new Position(10, 4)), new Position(10, 3)),
                Arguments.arguments(new Guard(Team.CHO, new Position(10, 4)), new Position(9, 3)),

                Arguments.arguments(new Guard(Team.CHO, new Position(9, 4)), new Position(10, 3)),
                Arguments.arguments(new Guard(Team.CHO, new Position(9, 4)), new Position(9, 3)),
                Arguments.arguments(new Guard(Team.CHO, new Position(9, 4)), new Position(8, 3)),

                Arguments.arguments(new Guard(Team.CHO, new Position(8, 4)), new Position(9, 3)),
                Arguments.arguments(new Guard(Team.CHO, new Position(8, 4)), new Position(8, 3)),
                Arguments.arguments(new Guard(Team.CHO, new Position(8, 4)), new Position(7, 3)),
                Arguments.arguments(new Guard(Team.CHO, new Position(8, 4)), new Position(7, 4)),
                Arguments.arguments(new Guard(Team.CHO, new Position(8, 4)), new Position(7, 5)),

                Arguments.arguments(new Guard(Team.CHO, new Position(8, 5)), new Position(7, 4)),
                Arguments.arguments(new Guard(Team.CHO, new Position(8, 5)), new Position(7, 5)),
                Arguments.arguments(new Guard(Team.CHO, new Position(8, 5)), new Position(7, 6)),

                Arguments.arguments(new Guard(Team.CHO, new Position(8, 6)), new Position(7, 5)),
                Arguments.arguments(new Guard(Team.CHO, new Position(8, 6)), new Position(7, 6)),
                Arguments.arguments(new Guard(Team.CHO, new Position(8, 6)), new Position(7, 7)),
                Arguments.arguments(new Guard(Team.CHO, new Position(8, 6)), new Position(8, 7)),
                Arguments.arguments(new Guard(Team.CHO, new Position(8, 6)), new Position(9, 7)),

                Arguments.arguments(new Guard(Team.CHO, new Position(9, 6)), new Position(8, 7)),
                Arguments.arguments(new Guard(Team.CHO, new Position(9, 6)), new Position(9, 7)),
                Arguments.arguments(new Guard(Team.CHO, new Position(9, 6)), new Position(10, 7)),

                Arguments.arguments(new Guard(Team.CHO, new Position(10, 6)), new Position(10, 7)),
                Arguments.arguments(new Guard(Team.CHO, new Position(10, 6)), new Position(9, 7)),

                Arguments.arguments(new Guard(Team.HAN, new Position(1, 4)), new Position(1, 3)),
                Arguments.arguments(new Guard(Team.HAN, new Position(1, 4)), new Position(2, 3)),

                Arguments.arguments(new Guard(Team.HAN, new Position(2, 4)), new Position(1, 3)),
                Arguments.arguments(new Guard(Team.HAN, new Position(2, 4)), new Position(2, 3)),
                Arguments.arguments(new Guard(Team.HAN, new Position(2, 4)), new Position(3, 3)),

                Arguments.arguments(new Guard(Team.HAN, new Position(3, 4)), new Position(2, 3)),
                Arguments.arguments(new Guard(Team.HAN, new Position(3, 4)), new Position(3, 3)),
                Arguments.arguments(new Guard(Team.HAN, new Position(3, 4)), new Position(4, 3)),
                Arguments.arguments(new Guard(Team.HAN, new Position(3, 4)), new Position(4, 4)),
                Arguments.arguments(new Guard(Team.HAN, new Position(3, 4)), new Position(4, 5)),

                Arguments.arguments(new Guard(Team.HAN, new Position(3, 5)), new Position(4, 4)),
                Arguments.arguments(new Guard(Team.HAN, new Position(3, 5)), new Position(4, 5)),
                Arguments.arguments(new Guard(Team.HAN, new Position(3, 5)), new Position(4, 6)),

                Arguments.arguments(new Guard(Team.HAN, new Position(3, 6)), new Position(4, 5)),
                Arguments.arguments(new Guard(Team.HAN, new Position(3, 6)), new Position(4, 6)),
                Arguments.arguments(new Guard(Team.HAN, new Position(3, 6)), new Position(4, 7)),
                Arguments.arguments(new Guard(Team.HAN, new Position(3, 6)), new Position(3, 7)),
                Arguments.arguments(new Guard(Team.HAN, new Position(3, 6)), new Position(2, 7)),

                Arguments.arguments(new Guard(Team.HAN, new Position(2, 6)), new Position(3, 7)),
                Arguments.arguments(new Guard(Team.HAN, new Position(2, 6)), new Position(2, 7)),
                Arguments.arguments(new Guard(Team.HAN, new Position(2, 6)), new Position(1, 7)),

                Arguments.arguments(new Guard(Team.HAN, new Position(1, 6)), new Position(1, 7)),
                Arguments.arguments(new Guard(Team.HAN, new Position(1, 6)), new Position(2, 7))
        );
    }
}
