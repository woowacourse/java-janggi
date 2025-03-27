package janggi.piece.onemovepiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("제공된 위치를 기준으로 이동할 수 없다면 예외를 반환한다.")
    @ParameterizedTest
    @MethodSource("kingNonCanMoveByPositionProvider")
    void canMoveByValidate(final Position currentPosition, final Position targetPosition) {
        //given
        final King king = new King(Team.HAN);

        //when //then
        assertThatThrownBy(() -> king.canMoveBy(currentPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    private static Stream<Arguments> kingNonCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(7, 5)),
                Arguments.of(new Position(5, 5), new Position(3, 5)),
                Arguments.of(new Position(5, 5), new Position(5, 7)),
                Arguments.of(new Position(5, 5), new Position(5, 3)),
                Arguments.of(new Position(5, 5), new Position(6, 6)),
                Arguments.of(new Position(5, 5), new Position(4, 6)),
                Arguments.of(new Position(5, 5), new Position(6, 4)),
                Arguments.of(new Position(5, 5), new Position(4, 4))
        );
    }

    @DisplayName("왕은 상하좌우 한칸을 움직일 수 있다면 예외를 반환하지 않는다.")
    @ParameterizedTest
    @MethodSource("kingCanMoveByPositionProvider")
    void canMoveBy(final Position currentPosition, final Position targetPosition) {
        //given
        final King king = new King(Team.HAN);

        //when //then
        assertThatCode(() -> king.canMoveBy(currentPosition, targetPosition))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> kingCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(6, 5)),
                Arguments.of(new Position(5, 5), new Position(5, 6)),
                Arguments.of(new Position(5, 5), new Position(5, 4)),
                Arguments.of(new Position(5, 5), new Position(4, 5))
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
