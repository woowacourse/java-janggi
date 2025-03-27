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

class PawnTest {

    @DisplayName("졸은 자신의 팀과 위치를 가진다.")
    @Test
    void pawnBoardPosition() {
        //given
        final Position position = new Position(4, 5);

        //when
        final Pawn pawn = new Pawn(Team.HAN, position);

        //then
        assertThat(pawn.getBoardPosition()).isEqualTo(new Position(4, 5));
    }

    @DisplayName("자신의 위치를 기준으로 이동할 수 없다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("pawnNonIsMovePositionProvider")
    void nonIsMove(final Position position) {
        //given
        final Pawn pawn = new Pawn(Team.HAN, new Position(5, 5));

        //when //then
        assertThatThrownBy(() -> pawn.isMove(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("자신의 위치를 기준으로 뒤를 제외한 가로,세로 한칸 이동이 가능하다면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("pawnIsMovePositionProvider")
    void isMove(final Position position) {
        //given
        final Pawn pawn = new Pawn(Team.HAN, new Position(5, 5));

        //when
        final boolean actual = pawn.isMove(position);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("졸은 자신의 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        final Pawn pawn = new Pawn(Team.HAN, new Position(5, 5));
        final Position futurePosition = new Position(4, 5);

        //when
        final List<Position> actual = pawn.makeRoute(futurePosition);

        //then
        assertThat(actual.isEmpty()).isTrue();
    }

    private static Stream<Arguments> pawnNonIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(6, 5)),
                Arguments.of(new Position(6, 3)),
                Arguments.of(new Position(6, 6)));
    }

    private static Stream<Arguments> pawnIsMovePositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 4)),
                Arguments.of(new Position(5, 6)),
                Arguments.of(new Position(4, 5)));
    }
}
