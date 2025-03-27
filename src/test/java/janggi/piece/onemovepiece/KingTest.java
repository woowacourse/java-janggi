package janggi.piece.onemovepiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.piece.Team;
import janggi.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KingTest {

    @DisplayName("왕은 자신의 팀과 위치를 가진다.")
    @Test
    void kingBoardPosition() {
        //given
        final Position position = new Position(4, 5);

        //when
        final King king = new King(Team.HAN, position);

        //then
        assertThat(king.getBoardPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("kingNonIsMovePositionProvider")
    void isMoveValidate(final Position position) {
        //given
        final King king = new King(Team.HAN, new Position(5, 5));

        //when //then
        assertThatThrownBy(() -> king.isMove(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("왕은 상하좌우 한칸을 움직일 수 있다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("kingIsMovePositionProvider")
    void isMove(final Position position) {
        //given
        final King king = new King(Team.HAN, new Position(5, 5));

        //when
        final boolean actual = king.isMove(position);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("왕은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        final King king = new King(Team.HAN, new Position(5, 5));
        final Position futurePosition = new Position(4, 5);

        //when
        final List<Position> actual = king.makeRoute(futurePosition);

        //then
        assertThat(actual.isEmpty()).isTrue();
    }

    private static Stream<Arguments> kingNonIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(7, 5)),
                Arguments.of(new Position(3, 5)),
                Arguments.of(new Position(5, 7)),
                Arguments.of(new Position(5, 3)),
                Arguments.of(new Position(6, 6)),
                Arguments.of(new Position(4, 6)),
                Arguments.of(new Position(6, 4)),
                Arguments.of(new Position(4, 4))
        );
    }

    private static Stream<Arguments> kingIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(6, 5)),
                Arguments.of(new Position(5, 6)),
                Arguments.of(new Position(5, 4)),
                Arguments.of(new Position(4, 5))
        );
    }
}
