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

class KingTest {

    @DisplayName("왕은 팀과 타입을 가진다.")
    @Test
    void kingBoardPosition() {
        //given //when
        final King king = new King(Team.HAN);

        //then
        assertThat(king.getPieceType()).isEqualTo(PieceType.KING);
        assertThat(king.getTeam()).isEqualTo(Team.HAN);
    }

    @DisplayName("왕은 궁성 안에서 좌우상하 대각선으로 한칸 움직일 수 있다.")
    @ParameterizedTest
    @MethodSource("kingPalaceMovePositionProvider")
    void palaceInMove(final Team team, final Position currentPosition, final Position targetPosition,
                      final Palace palace) {
        //given
        final King king = new King(team);

        //when //then
        assertThatCode(() -> king.canMoveBy(currentPosition, targetPosition, palace))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> kingPalaceMovePositionProvider() {
        final Palace palace = Palace.AREA;

        return Stream.of(
                Arguments.of(Team.HAN, new Position(1, 4), new Position(0, 3), palace),
                Arguments.of(Team.HAN, new Position(1, 4), new Position(0, 4), palace),
                Arguments.of(Team.HAN, new Position(1, 4), new Position(0, 5), palace),
                Arguments.of(Team.HAN, new Position(1, 4), new Position(1, 3), palace),
                Arguments.of(Team.HAN, new Position(1, 4), new Position(1, 5), palace),
                Arguments.of(Team.HAN, new Position(1, 4), new Position(2, 3), palace),
                Arguments.of(Team.HAN, new Position(1, 4), new Position(2, 4), palace),
                Arguments.of(Team.HAN, new Position(1, 4), new Position(2, 5), palace),

                Arguments.of(Team.CHU, new Position(8, 4), new Position(7, 3), palace),
                Arguments.of(Team.CHU, new Position(8, 4), new Position(7, 4), palace),
                Arguments.of(Team.CHU, new Position(8, 4), new Position(7, 5), palace),
                Arguments.of(Team.CHU, new Position(8, 4), new Position(8, 3), palace),
                Arguments.of(Team.CHU, new Position(8, 4), new Position(8, 5), palace),
                Arguments.of(Team.CHU, new Position(8, 4), new Position(9, 3), palace),
                Arguments.of(Team.CHU, new Position(8, 4), new Position(9, 4), palace),
                Arguments.of(Team.CHU, new Position(8, 4), new Position(9, 5), palace)
        );
    }

    @DisplayName("왕은 한번에 두칸 이상 이동하면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("kingMoveOverTwoStepPositionProvider")
    void kingMoveOverTwoStep(final Position currentPosition, final Position targetPosition, final Palace palace) {
        //given
        final King king = new King(Team.CHU);

        //when //then
        assertThatThrownBy(() -> king.canMoveBy(currentPosition, targetPosition, palace))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    private static Stream<Arguments> kingMoveOverTwoStepPositionProvider() {
        final Palace palace = Palace.AREA;

        return Stream.of(
                Arguments.of(new Position(0, 3), new Position(0, 5), palace),
                Arguments.of(new Position(0, 3), new Position(1, 5), palace),
                Arguments.of(new Position(0, 3), new Position(2, 5), palace),
                Arguments.of(new Position(0, 3), new Position(2, 2), palace),
                Arguments.of(new Position(0, 3), new Position(2, 3), palace)
        );
    }

    @DisplayName("왕은 궁성을 벗어나는 곳으로 이동하면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("kingMoveOutPositionProvider")
    void kingMoveOutPalace(final Position currentPosition, final Position targetPosition, final Palace palace) {
        //given
        final King king = new King(Team.CHU);

        //when //then
        assertThatThrownBy(() -> king.canMoveBy(currentPosition, targetPosition, palace))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    private static Stream<Arguments> kingMoveOutPositionProvider() {
        final Palace palace = Palace.AREA;

        return Stream.of(
                Arguments.of(new Position(0, 3), new Position(0, 2), palace),
                Arguments.of(new Position(1, 3), new Position(1, 2), palace),
                Arguments.of(new Position(2, 3), new Position(2, 2), palace),
                Arguments.of(new Position(2, 3), new Position(3, 2), palace),
                Arguments.of(new Position(2, 3), new Position(3, 3), palace),
                Arguments.of(new Position(2, 4), new Position(3, 4), palace),
                Arguments.of(new Position(2, 5), new Position(3, 5), palace),
                Arguments.of(new Position(2, 5), new Position(3, 6), palace),
                Arguments.of(new Position(2, 5), new Position(2, 6), palace),
                Arguments.of(new Position(1, 5), new Position(1, 6), palace),
                Arguments.of(new Position(0, 5), new Position(0, 6), palace)
        );
    }

    @DisplayName("왕은 제공된 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        final King king = new King(Team.HAN);
        final Position currentPosition = new Position(4, 5);
        final Position targetPosition = new Position(4, 5);

        //when
        final List<Position> actual = king.makeRoute(currentPosition, targetPosition);

        //then
        assertThat(actual.isEmpty()).isTrue();
    }
}
