package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Path 클래스 테스트")
class PathTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    @Test
    @DisplayName("contains: Path에 포함된 Position은 true를 반환한다")
    void containsReturnsTrueForPositionInPath() {
        Position middle = pos(Column.A, Row.TWO);
        Path path = new Path(List.of(pos(Column.A, Row.ONE), middle, pos(Column.A, Row.THREE)));

        assertThat(path.contains(middle)).isTrue();
        assertThat(path.contains(pos(Column.A, Row.ONE))).isTrue();
        assertThat(path.contains(pos(Column.A, Row.THREE))).isTrue();
    }

    @Test
    @DisplayName("contains: Path에 없는 Position은 false를 반환한다")
    void containsReturnsFalseForPositionNotInPath() {
        Path path = new Path(List.of(pos(Column.A, Row.ONE), pos(Column.A, Row.TWO)));

        assertThat(path.contains(pos(Column.B, Row.ONE))).isFalse();
        assertThat(path.contains(pos(Column.A, Row.FIVE))).isFalse();
    }

    @Test
    @DisplayName("endsAt: 마지막 Position에 true를 반환한다")
    void endsAtReturnsTrueForLastPosition() {
        Position last = pos(Column.C, Row.FIVE);
        Path path = new Path(List.of(pos(Column.A, Row.FIVE), pos(Column.B, Row.FIVE), last));

        assertThat(path.endsAt(last)).isTrue();
    }

    @Test
    @DisplayName("endsAt: 마지막이 아닌 Position에 false를 반환한다")
    void endsAtReturnsFalseForNonLastPosition() {
        Path path = new Path(List.of(pos(Column.A, Row.FIVE), pos(Column.B, Row.FIVE), pos(Column.C, Row.FIVE)));

        assertThat(path.endsAt(pos(Column.A, Row.FIVE))).isFalse();
        assertThat(path.endsAt(pos(Column.B, Row.FIVE))).isFalse();
        assertThat(path.endsAt(pos(Column.D, Row.FIVE))).isFalse();
    }

    @Test
    @DisplayName("intermediates: 단일 이동 경로의 중간 좌표는 비어있다")
    void intermediatesForSinglePositionPathIsEmpty() {
        Path path = new Path(List.of(pos(Column.A, Row.ZERO)));

        assertThat(path.intermediates()).isEmpty();
    }

    @Test
    @DisplayName("intermediates: 여러 이동 경로에서 마지막을 제외한 모든 좌표를 반환한다")
    void intermediatesReturnsAllButLastPosition() {
        List<Position> positions = List.of(
                pos(Column.A, Row.ONE),
                pos(Column.A, Row.TWO),
                pos(Column.A, Row.THREE)
        );
        Path path = new Path(positions);

        assertThat(path.intermediates())
                .containsExactly(pos(Column.A, Row.ONE), pos(Column.A, Row.TWO));
    }

}
