package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecePathTest {

    @Test
    void 다른_포지션과의_row_차이를_구한다() {
        // given
        Position src = new Position(Row.FOUR, Column.FOUR);
        Position dst = new Position(Row.FIVE, Column.FOUR);

        PiecePath path = new PiecePath(src, dst);

        // when
        int rowDifference = path.rowDifference();

        // then
        assertThat(rowDifference).isEqualTo(1);
    }

    @Test
    void 다른_포지션과의_column_차이를_구한다() {
        // given
        Position src = new Position(Row.FOUR, Column.FOUR);
        Position dst = new Position(Row.FIVE, Column.FIVE);
        PiecePath path = new PiecePath(src, dst);

        // when
        int colDifference = path.columnDifference();

        // then
        assertThat(colDifference).isEqualTo(1);
    }

    @Test
    void 두_직선_좌표_사이의_모든_좌표를_반환() {
        Position src = new Position(Row.ONE, Column.ONE);
        Position dst = new Position(Row.ONE, Column.FIVE);
        PiecePath path = new PiecePath(src, dst);

        // when
        List<Position> betweenPositions = path.getBetweenPositions();

        // then
        assertAll(
                () -> assertThat(betweenPositions).hasSize(3),
                () -> assertThat(betweenPositions.get(0)).isEqualTo(new Position(Row.ONE, Column.TWO)),
                () -> assertThat(betweenPositions.get(1)).isEqualTo(new Position(Row.ONE, Column.THREE)),
                () -> assertThat(betweenPositions.get(2)).isEqualTo(new Position(Row.ONE, Column.FOUR))
        );
    }

    @DisplayName("경로가 대각선일떄, 두 좌표 사이의 모든 좌표를 반환")
    @Test
    void DiagonalPath_getBetweenPositions() {
        // given
        Position src = new Position(Row.ONE, Column.ONE);
        Position dst = new Position(Row.FOUR, Column.FOUR);
        PiecePath path = new PiecePath(src, dst);

        // when
        List<Position> positions = path.getBetweenPositions();

        // then
        assertThat(positions).hasSize(2);
        assertThat(positions.get(0)).isEqualTo(Position.of(2, 2));
        assertThat(positions.get(1)).isEqualTo(Position.of(3, 3));
    }

    @DisplayName("시작위치와 목적위치가 모두 궁전 내에 있음을 확인한다.")
    @Test
    void isInPalacePath() {
        // given
        PiecePath path = new PiecePath(Position.of(9, 5), Position.of(8, 6));

        // when
        boolean inPalacePath = path.isInPalacePath();

        // then
        assertThat(inPalacePath).isTrue();
    }

    @DisplayName("길이 대각선일때 true를 반환한다.")
    @Test
    void isDiagonal_true() {
        // given
        PiecePath path = new PiecePath(Position.of(1, 1), Position.of(3, 3));

        // when
        boolean isDiagonal = path.isDiagonal();

        // then
        assertThat(isDiagonal).isTrue();
    }

    @DisplayName("길이 대각선이 아니면 flase를 반환한다.")
    @Test
    void isDiagonal_false() {
        // given
        PiecePath path = new PiecePath(Position.of(1, 1), Position.of(3, 2));

        // when
        boolean isDiagonal = path.isDiagonal();

        // then
        assertThat(isDiagonal).isFalse();
    }

    @DisplayName("길이 직선일때 true를 반환한다.")
    @Test
    void isStraight_true() {
        // given
        PiecePath path = new PiecePath(Position.of(1, 1), Position.of(1, 3));

        // when
        boolean straight = path.isStraight();

        // then
        assertThat(straight).isTrue();
    }

    @DisplayName("길이 직선이 아니면 false를 반환한다.")
    @Test
    void isStraight_false() {
        // given
        PiecePath path = new PiecePath(Position.of(1, 1), Position.of(2, 3));

        // when
        boolean straight = path.isStraight();

        // then
        assertThat(straight).isFalse();
    }

    @DisplayName("해당 움직임만큼 이동했을 때 목적지로 갈 수 있는지 확인한다.")
    @Test
    void canReachToDestination_whenMoveByDirection() {
        // given
        Position source = Position.of(1, 1);
        Position destination = Position.of(1, 2);

        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canReach = path.canReachToDestination(Movement.from(Direction.RIGHT));

        // then
        assertThat(canReach).isTrue();
    }

    @DisplayName("해당 움직임만큼 이동했을 때 목적지로 갈 수 없다면 False")
    @Test
    void canReachToDestination_whenMoveByDirection_False() {
        // given
        Position source = Position.of(1, 1);
        Position destination = Position.of(2, 1);

        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canReach = path.canReachToDestination(Movement.from(Direction.RIGHT));

        // then
        assertThat(canReach).isFalse();
    }


    @DisplayName("주어진 움직임의 방향을 따라서 움직인 Position을 모두 반환한다 - 목적지, 출발지 제외")
    @Test
    void tracePositionsByDirection_fromSource() {
        // given
        Position source = Position.of(1, 1);
        Position destination = Position.of(3, 3);
        PiecePath path = new PiecePath(source, destination);

        Movement movement = Movement.from(Direction.DOWN, Direction.RIGHT, Direction.DOWN_RIGHT);

        // when
        List<Position> positions = path.tracePositionsByDirection(movement);

        // then
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(positions).hasSize(3);
        softly.assertThat(positions.get(0)).isEqualTo(Position.of(2,1));
        softly.assertThat(positions.get(1)).isEqualTo(Position.of(2,2));
        softly.assertThat(positions.get(2)).isEqualTo(Position.of(3,3));

        softly.assertAll();
    }
}
