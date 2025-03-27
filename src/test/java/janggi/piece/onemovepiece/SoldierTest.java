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

class SoldierTest {

    @DisplayName("병은 팀과 타입을 가진다.")
    @Test
    void soldierBoardPosition() {
        //given //when
        final Soldier soldier = new Soldier(Team.HAN);

        //then
        assertThat(soldier.getPieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(soldier.getTeam()).isEqualTo(Team.HAN);
    }

    @DisplayName("제공된 위치를 기준으로 이동할 수 없다면(가로,세로 한칸을 제외한 경로) 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("soldierNonCanMoveByPositionProvider")
    void nonCanMoveBy(final Position currentPosition, final Position targetPosition) {
        //given
        final Soldier soldier = new Soldier(Team.HAN);

        //when
        assertThatThrownBy(() -> soldier.canMoveBy(currentPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    private static Stream<Arguments> soldierNonCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(4, 5)),
                Arguments.of(new Position(5, 5), new Position(6, 3)),
                Arguments.of(new Position(5, 5), new Position(6, 6))
        );
    }

    @DisplayName("제공된 위치를 기준으로 뒤를 제외한 가로,세로 한칸 이동을 할 수 있다면 예외를 던지지 않는다.")
    @ParameterizedTest
    @MethodSource("soldierCanMoveByPositionProvider")
    void canMoveBy(final Position currentPosition, final Position targetPosition) {
        //given
        final Soldier soldier = new Soldier(Team.HAN);

        //when //then
        assertThatCode(() -> soldier.canMoveBy(currentPosition, targetPosition))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> soldierCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(6, 5)),
                Arguments.of(new Position(5, 5), new Position(5, 6)),
                Arguments.of(new Position(5, 5), new Position(5, 4))
        );
    }

    @DisplayName("병은 제공된 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    @Test
    void makeRoute() {
        //given
        final Soldier soldier = new Soldier(Team.HAN);

        final Position currentPosition = new Position(3, 5);
        final Position targetPosition = new Position(4, 5);

        //when
        final List<Position> actual = soldier.makeRoute(currentPosition, targetPosition);

        //then
        assertThat(actual.isEmpty()).isTrue();
    }

}
