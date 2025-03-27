package janggi.piece.multiplemovepiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ElephantTest {

    @DisplayName("상은 팀과 타입을 가진다.")
    @Test
    void elephantjanggiBoardPosition() {
        //given //when
        final Elephant elephant = new Elephant(Team.HAN);

        //then
        assertThat(elephant.getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(elephant.getTeam()).isEqualTo(Team.HAN);
    }

    @DisplayName("제공된 위치를 기준으로 이동할 수 없다면 예외를 던진다.")
    @ParameterizedTest
    @MethodSource("elephantNonCanMoveByPositionProvider")
    void nonCanMoveBy(final Position currentPosition, final Position targetPosition) {
        //given
        final Elephant elephant = new Elephant(Team.HAN);

        //when //then
        assertThatThrownBy(() -> elephant.canMoveBy(currentPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    private static Stream<Arguments> elephantNonCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(2, 5)),
                Arguments.of(new Position(5, 5), new Position(2, 4)),
                Arguments.of(new Position(5, 5), new Position(2, 6)),
                Arguments.of(new Position(5, 5), new Position(4, 8)),
                Arguments.of(new Position(5, 5), new Position(5, 8)),
                Arguments.of(new Position(5, 5), new Position(6, 8)),
                Arguments.of(new Position(5, 5), new Position(8, 4)),
                Arguments.of(new Position(5, 5), new Position(8, 5)),
                Arguments.of(new Position(5, 5), new Position(8, 6)),
                Arguments.of(new Position(5, 5), new Position(4, 2)),
                Arguments.of(new Position(5, 5), new Position(5, 2)),
                Arguments.of(new Position(5, 5), new Position(6, 2))
        );
    }

    @DisplayName("상은 제공된 위치를 기준으로 직선으로 한칸 대각선으로 두칸 이동할 수 있다면 예외를 던지지 않는다.")
    @ParameterizedTest
    @MethodSource("elephantCanMoveByPositionProvider")
    void canMoveBy(final Position currentPosition, final Position targetPosition) {
        //given
        final Elephant elephant = new Elephant(Team.HAN);

        //when //then
        assertThatCode(() -> elephant.canMoveBy(currentPosition, targetPosition))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> elephantCanMoveByPositionProvider() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(2, 3)),
                Arguments.of(new Position(5, 5), new Position(2, 7)),
                Arguments.of(new Position(5, 5), new Position(7, 8)),
                Arguments.of(new Position(5, 5), new Position(3, 8)),
                Arguments.of(new Position(5, 5), new Position(8, 3)),
                Arguments.of(new Position(5, 5), new Position(8, 7)),
                Arguments.of(new Position(5, 5), new Position(3, 2)),
                Arguments.of(new Position(5, 5), new Position(7, 2))
        );
    }

    @Nested
    @DisplayName("상은 제공된 위치에서 목적지까지의 경로를 계산하여 반환한다.")
    class MakeRoute {

        @DisplayName("자신의 위치에서 위로 한칸 이동 후 왼쪽 대각선으로 두칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase1() {
            final Elephant elephant = new Elephant(Team.HAN);

            final Position currnetPosition = new Position(5, 5);
            final Position targetPosition = new Position(2, 3);

            final List<Position> actual = elephant.makeRoute(currnetPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(4, 5),
                    new Position(3, 4)
            );
        }

        @DisplayName("제공된 위치에서 위로 한칸 이동 후 오른쪽 대각선으로 두칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase2() {
            final Elephant elephant = new Elephant(Team.HAN);

            final Position currnetPosition = new Position(5, 5);
            final Position targetPosition = new Position(2, 7);

            final List<Position> actual = elephant.makeRoute(currnetPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(4, 5),
                    new Position(3, 6)
            );
        }

        @DisplayName("제공된 위치에서 오른쪽으로 한칸 이동 후 왼쪽 대각선으로 두칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase3() {
            final Elephant elephant = new Elephant(Team.HAN);

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(3, 8);

            final List<Position> actual = elephant.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(5, 6),
                    new Position(4, 7)
            );
        }

        @DisplayName("제공된 위치에서 오른쪽으로 한칸 이동 후 오른쪽 대각선으로 두칸 이동하는 경로를 계산한다")
        @Test
        void makeRouteCase4() {
            final Elephant elephant = new Elephant(Team.HAN);

            final Position currentPosition = new Position(5, 5);
            final Position targetPosition = new Position(7, 8);

            final List<Position> actual = elephant.makeRoute(currentPosition, targetPosition);

            assertThat(actual).containsExactly(
                    new Position(5, 6),
                    new Position(6, 7)
            );
        }

        @DisplayName("상이 일보 전진하는 자리에 멱(장애물)이 존재한다면 예외가 발생한다.")
        @Test
        void hasObstacle() {
            // given
            final Elephant elephant = new Elephant(Team.HAN);

            final Position currentPosition = new Position(0, 7);
            final Position targetPosition = new Position(3, 5);

            final Map<Position, Piece> janggiBoard = Map.of(
                    new Position(1, 7), new Chariot(Team.HAN)
            );

            // when // then
            assertThatThrownBy(() -> elephant.checkObstacle(currentPosition, targetPosition, janggiBoard))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageStartingWith("[ERROR]");
        }

        @DisplayName("상의 이동 경로에 장애물이 없다면 예외를 던지지 않는다.")
        @Test
        void nonObstacle() {
            // given
            final Elephant elephant = new Elephant(Team.HAN);

            final Position currentPosition = new Position(0, 7);
            final Position targetPosition = new Position(3, 5);

            final Map<Position, Piece> janggiBoard = Map.of(
                    new Position(2, 7), new Chariot(Team.HAN)
            );

            // when // then
            assertThatCode(() -> elephant.checkObstacle(currentPosition, targetPosition, janggiBoard))
                    .doesNotThrowAnyException();
        }
    }
}
