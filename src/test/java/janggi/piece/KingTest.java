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

public class KingTest {
    @Test
    @DisplayName("왕 전진 테스트")
    void kingForwardTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        Position arrivedPosition = new Position(8, 5);
        //when
        king.move(arrivedPosition);
        //then
        assertThat(king.matchesPosition(new Position(8, 5))).isTrue();
    }

    @Test
    @DisplayName("왕 후진 테스트")
    void kingBackTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        Position arrivedPosition = new Position(10, 5);
        //when
        king.move(arrivedPosition);
        //then
        assertThat(king.matchesPosition(new Position(10, 5))).isTrue();
    }

    @Test
    @DisplayName("왕 오른쪽 이동 테스트")
    void kingRightStepTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        Position arrivedPosition = new Position(9, 6);
        //when
        king.move(arrivedPosition);
        //then
        assertThat(king.matchesPosition(new Position(9, 6))).isTrue();
    }

    @Test
    @DisplayName("왕 왼쪽 이동 테스트")
    void kingLeftStepTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        Position arrivedPosition = new Position(9, 4);
        //when
        king.move(arrivedPosition);
        //then
        assertThat(king.matchesPosition(new Position(9, 4))).isTrue();
    }

    @Test
    @DisplayName("왕 오른쪽 위 이동 테스트")
    void kingRightUpStepTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        Position arrivedPosition = new Position(8, 6);
        //when
        king.move(arrivedPosition);
        //then
        assertThat(king.matchesPosition(new Position(8, 6))).isTrue();
    }

    @Test
    @DisplayName("왕 오른쪽 아래 이동 테스트")
    void kingRightDownStepTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        Position arrivedPosition = new Position(10, 6);
        //when
        king.move(arrivedPosition);
        //then
        assertThat(king.matchesPosition(new Position(10, 6))).isTrue();
    }

    @Test
    @DisplayName("왕 왼쪽 위 이동 테스트")
    void kingLeftUpStepTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        Position arrivedPosition = new Position(8, 4);
        //when
        king.move(arrivedPosition);
        //then
        assertThat(king.matchesPosition(new Position(8, 4))).isTrue();
    }

    @Test
    @DisplayName("왕 왼쪽 아래 이동 테스트")
    void kingLeftDownStepTest() {
        //given
        King king = new King(Team.CHO, new Position(9, 5));
        Position arrivedPosition = new Position(10, 4);
        //when
        king.move(arrivedPosition);
        //then
        assertThat(king.matchesPosition(new Position(10, 4))).isTrue();
    }

    @Test
    @DisplayName("왕 장기판 밖으로 이동 시 예외 발생 테스트")
    void outOfBoardExceptionTest() {
        //given
        King king = new King(Team.CHO, new Position(10, 5));
        Position arrivedPosition = new Position(11, 5);
        //when & then
        assertThatThrownBy(() -> king.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("makeOutOfPalacePosition")
    @DisplayName("왕 궁성 밖으로 이동 시 예외 발생 테스트")
    void outOfPalaceExceptionTest(Piece king, Position arrivedPosition) {
        assertThatThrownBy(() -> king.move(arrivedPosition)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> makeOutOfPalacePosition() {
        return Stream.of(
                Arguments.arguments(new King(Team.CHO, new Position(10, 4)), new Position(10, 3)),
                Arguments.arguments(new King(Team.CHO, new Position(10, 4)), new Position(9, 3)),

                Arguments.arguments(new King(Team.CHO, new Position(9, 4)), new Position(10, 3)),
                Arguments.arguments(new King(Team.CHO, new Position(9, 4)), new Position(9, 3)),
                Arguments.arguments(new King(Team.CHO, new Position(9, 4)), new Position(8, 3)),

                Arguments.arguments(new King(Team.CHO, new Position(8, 4)), new Position(9, 3)),
                Arguments.arguments(new King(Team.CHO, new Position(8, 4)), new Position(8, 3)),
                Arguments.arguments(new King(Team.CHO, new Position(8, 4)), new Position(7, 3)),
                Arguments.arguments(new King(Team.CHO, new Position(8, 4)), new Position(7, 4)),
                Arguments.arguments(new King(Team.CHO, new Position(8, 4)), new Position(7, 5)),

                Arguments.arguments(new King(Team.CHO, new Position(8, 5)), new Position(7, 4)),
                Arguments.arguments(new King(Team.CHO, new Position(8, 5)), new Position(7, 5)),
                Arguments.arguments(new King(Team.CHO, new Position(8, 5)), new Position(7, 6)),

                Arguments.arguments(new King(Team.CHO, new Position(8, 6)), new Position(7, 5)),
                Arguments.arguments(new King(Team.CHO, new Position(8, 6)), new Position(7, 6)),
                Arguments.arguments(new King(Team.CHO, new Position(8, 6)), new Position(7, 7)),
                Arguments.arguments(new King(Team.CHO, new Position(8, 6)), new Position(8, 7)),
                Arguments.arguments(new King(Team.CHO, new Position(8, 6)), new Position(9, 7)),

                Arguments.arguments(new King(Team.CHO, new Position(9, 6)), new Position(8, 7)),
                Arguments.arguments(new King(Team.CHO, new Position(9, 6)), new Position(9, 7)),
                Arguments.arguments(new King(Team.CHO, new Position(9, 6)), new Position(10, 7)),

                Arguments.arguments(new King(Team.CHO, new Position(10, 6)), new Position(10, 7)),
                Arguments.arguments(new King(Team.CHO, new Position(10, 6)), new Position(9, 7)),

                Arguments.arguments(new King(Team.HAN, new Position(1, 4)), new Position(1, 3)),
                Arguments.arguments(new King(Team.HAN, new Position(1, 4)), new Position(2, 3)),

                Arguments.arguments(new King(Team.HAN, new Position(2, 4)), new Position(1, 3)),
                Arguments.arguments(new King(Team.HAN, new Position(2, 4)), new Position(2, 3)),
                Arguments.arguments(new King(Team.HAN, new Position(2, 4)), new Position(3, 3)),

                Arguments.arguments(new King(Team.HAN, new Position(3, 4)), new Position(2, 3)),
                Arguments.arguments(new King(Team.HAN, new Position(3, 4)), new Position(3, 3)),
                Arguments.arguments(new King(Team.HAN, new Position(3, 4)), new Position(4, 3)),
                Arguments.arguments(new King(Team.HAN, new Position(3, 4)), new Position(4, 4)),
                Arguments.arguments(new King(Team.HAN, new Position(3, 4)), new Position(4, 5)),

                Arguments.arguments(new King(Team.HAN, new Position(3, 5)), new Position(4, 4)),
                Arguments.arguments(new King(Team.HAN, new Position(3, 5)), new Position(4, 5)),
                Arguments.arguments(new King(Team.HAN, new Position(3, 5)), new Position(4, 6)),

                Arguments.arguments(new King(Team.HAN, new Position(3, 6)), new Position(4, 5)),
                Arguments.arguments(new King(Team.HAN, new Position(3, 6)), new Position(4, 6)),
                Arguments.arguments(new King(Team.HAN, new Position(3, 6)), new Position(4, 7)),
                Arguments.arguments(new King(Team.HAN, new Position(3, 6)), new Position(3, 7)),
                Arguments.arguments(new King(Team.HAN, new Position(3, 6)), new Position(2, 7)),

                Arguments.arguments(new King(Team.HAN, new Position(2, 6)), new Position(3, 7)),
                Arguments.arguments(new King(Team.HAN, new Position(2, 6)), new Position(2, 7)),
                Arguments.arguments(new King(Team.HAN, new Position(2, 6)), new Position(1, 7)),

                Arguments.arguments(new King(Team.HAN, new Position(1, 6)), new Position(1, 7)),
                Arguments.arguments(new King(Team.HAN, new Position(1, 6)), new Position(2, 7))
        );
    }

}
