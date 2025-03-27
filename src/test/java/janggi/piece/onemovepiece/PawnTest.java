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

class PawnTest {

    @DisplayName("졸은 팀과 타입을 가진다.")
    @Test
    void pawnBoardPosition() {
        //given //when
        final Pawn pawn = new Pawn(Team.HAN);

        //then
        assertThat(pawn.getPieceType()).isEqualTo(PieceType.PAWN);
        assertThat(pawn.getTeam()).isEqualTo(Team.HAN);
    }

    @DisplayName("제공된 위치를 기준으로 이동할 수 없다면 예외를 반환한다.")
    @ParameterizedTest
    @MethodSource("pawnNonCanMoveByPositionProvider")
    void nonCanMoveBy(final Position currentPosition, final Position targetPosition) {
        //given
        final Pawn pawn = new Pawn(Team.HAN);

        //when //then
        assertThatThrownBy(() -> pawn.canMoveBy(currentPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    private static Stream<Arguments> pawnNonCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(6, 5)),
                Arguments.of(new Position(5, 5), new Position(6, 3)),
                Arguments.of(new Position(5, 5), new Position(6, 6)));
    }

    @DisplayName("제공된 위치를 기준으로 뒤를 제외한 가로,세로 한칸 이동이 가능하다면 예외를 반환하지 않는다.")
    @ParameterizedTest
    @MethodSource("pawnCanMoveByPositionProvider")
    void canMoveBy(final Position currentPosition, final Position targetPosition) {
        //given
        final Pawn pawn = new Pawn(Team.HAN);

        //when //then
        assertThatCode(() -> pawn.canMoveBy(currentPosition, targetPosition))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> pawnCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(5, 4)),
                Arguments.of(new Position(5, 5), new Position(5, 6)),
                Arguments.of(new Position(5, 5), new Position(4, 5)));
    }

    @DisplayName("졸은 제공된 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        final Pawn pawn = new Pawn(Team.HAN);

        final Position currentPosition = new Position(3, 5);
        final Position targetPosition = new Position(4, 5);

        //when
        final List<Position> actual = pawn.makeRoute(currentPosition, targetPosition);

        //then
        assertThat(actual.isEmpty()).isTrue();
    }
}
