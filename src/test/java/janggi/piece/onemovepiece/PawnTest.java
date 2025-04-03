package janggi.piece.onemovepiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.palace.Palace;
import janggi.piece.PieceType;
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

    @DisplayName("졸은 팀과 타입을 가진다.")
    @Test
    void pawnBoardPosition() {
        //given //when
        final Pawn pawn = new Pawn();

        //then
        assertThat(pawn.getPieceType()).isEqualTo(PieceType.PAWN);
        assertThat(pawn.getTeam()).isEqualTo(Team.CHU);
    }

    @DisplayName("궁성의 영역이 아닐때 이동할 수 없다면(자신의 위치 기준, 뒤로 가는 경우, 자신의 위치 기준 대각선으로 가는 경우) 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("pawnNonCanMoveByPositionProvider")
    void nonCanMoveBy(final Position currentPosition, final Position targetPosition, final Palace palace) {
        //given
        final Pawn pawn = new Pawn();

        //when
        assertThatThrownBy(() -> pawn.canMoveBy(currentPosition, targetPosition, palace))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    private static Stream<Arguments> pawnNonCanMoveByPositionProvider() {
        final Palace palace = Palace.AREA;

        return Stream.of(
                Arguments.of(new Position(4, 5), new Position(5, 5), palace),
                Arguments.of(new Position(4, 5), new Position(5, 6), palace),
                Arguments.of(new Position(4, 5), new Position(5, 4), palace)
        );
    }

    @DisplayName("궁성의 영역이 아닐 때 뒤를 제외한 가로,세로 한칸 이동을 할 수 있다면 예외를 던지지 않는다.")
    @ParameterizedTest
    @MethodSource("pawnCanMoveByPositionProvider")
    void canMoveBy(final Position currentPosition, final Position targetPosition, final Palace palace) {
        //given
        final Pawn pawn = new Pawn();

        //when //then
        assertThatCode(() -> pawn.canMoveBy(currentPosition, targetPosition, palace))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> pawnCanMoveByPositionProvider() {
        final Palace palace = Palace.AREA;

        return Stream.of(
                Arguments.of(new Position(4, 5), new Position(3, 5), palace),
                Arguments.of(new Position(4, 5), new Position(4, 4), palace),
                Arguments.of(new Position(4, 5), new Position(4, 6), palace));
    }

    @DisplayName("상대편의 궁성에 진입하면 전진, 좌우, 대각선(자신의 위치 기준으로 10시, 1시) 이동이 가능하다.")
    @ParameterizedTest
    @MethodSource("pawnPalaceMovePositionProvider")
    void inPalaceMove(final Position currentPosition, final Position targetPosition, final Palace palace) {
        //given
        final Pawn pawn = new Pawn();

        //when //then
        assertThatCode(() -> pawn.canMoveBy(currentPosition, targetPosition, palace))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> pawnPalaceMovePositionProvider() {
        final Palace palace = Palace.AREA;

        return Stream.of(
                Arguments.of(new Position(1, 4), new Position(1, 3), palace),
                Arguments.of(new Position(1, 4), new Position(0, 3), palace),
                Arguments.of(new Position(1, 4), new Position(0, 4), palace),
                Arguments.of(new Position(1, 4), new Position(0, 5), palace),
                Arguments.of(new Position(1, 4), new Position(1, 5), palace)
        );
    }

    @DisplayName("상대편의 궁성에 진입했을 때 후퇴를 할 수는 없다. (대각선 포함)")
    @ParameterizedTest
    @MethodSource("pawnPalaceBehindMovePositionProvider")
    void inPalaceNotBehindMove(final Position currentPosition, final Position targetPosition, final Palace palace) {
        //given
        final Pawn pawn = new Pawn();

        //when //then
        assertThatThrownBy(() -> pawn.canMoveBy(currentPosition, targetPosition, palace))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    private static Stream<Arguments> pawnPalaceBehindMovePositionProvider() {
        final Palace palace = Palace.AREA;

        return Stream.of(
                Arguments.of(new Position(1, 4), new Position(2, 3), palace),
                Arguments.of(new Position(1, 4), new Position(2, 4), palace),
                Arguments.of(new Position(1, 4), new Position(2, 5), palace)
        );
    }

    @DisplayName("졸은 제공된 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        final Pawn pawn = new Pawn();

        final Position currentPosition = new Position(3, 5);
        final Position targetPosition = new Position(4, 5);

        //when
        final List<Position> actual = pawn.makeRoute(currentPosition, targetPosition);

        //then
        assertThat(actual.isEmpty()).isTrue();
    }
}
