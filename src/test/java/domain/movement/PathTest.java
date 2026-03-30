package domain.movement;

import domain.board.Col;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Path 클래스 테스트")
class PathTest {

    private Position pos(Col col, Row row) {
        return new Position(col, row);
    }

    @Test
    @DisplayName("contains: Path에 포함된 Position은 true를 반환한다")
    void containsReturnsTrueForPositionInPath() {
        Position middle = pos(Col.A, Row.TWO);
        Path path = new Path(List.of(pos(Col.A, Row.ONE), middle, pos(Col.A, Row.THREE)));

        assertThat(path.contains(middle)).isTrue();
        assertThat(path.contains(pos(Col.A, Row.ONE))).isTrue();
        assertThat(path.contains(pos(Col.A, Row.THREE))).isTrue();
    }

    @Test
    @DisplayName("contains: Path에 없는 Position은 false를 반환한다")
    void containsReturnsFalseForPositionNotInPath() {
        Path path = new Path(List.of(pos(Col.A, Row.ONE), pos(Col.A, Row.TWO)));

        assertThat(path.contains(pos(Col.B, Row.ONE))).isFalse();
        assertThat(path.contains(pos(Col.A, Row.FIVE))).isFalse();
    }

    @Test
    @DisplayName("endsAt: 마지막 Position에 true를 반환한다")
    void endsAtReturnsTrueForLastPosition() {
        Position last = pos(Col.C, Row.FIVE);
        Path path = new Path(List.of(pos(Col.A, Row.FIVE), pos(Col.B, Row.FIVE), last));

        assertThat(path.endsAt(last)).isTrue();
    }

    @Test
    @DisplayName("endsAt: 마지막이 아닌 Position에 false를 반환한다")
    void endsAtReturnsFalseForNonLastPosition() {
        Path path = new Path(List.of(pos(Col.A, Row.FIVE), pos(Col.B, Row.FIVE), pos(Col.C, Row.FIVE)));

        assertThat(path.endsAt(pos(Col.A, Row.FIVE))).isFalse();
        assertThat(path.endsAt(pos(Col.B, Row.FIVE))).isFalse();
        assertThat(path.endsAt(pos(Col.D, Row.FIVE))).isFalse();
    }

    @Test
    @DisplayName("intermediates: 단일 이동 경로의 중간 좌표는 비어있다")
    void intermediatesForSinglePositionPathIsEmpty() {
        Path path = new Path(List.of(pos(Col.A, Row.ZERO)));

        assertThat(path.intermediates()).isEmpty();
    }

    @Test
    @DisplayName("intermediates: 여러 이동 경로에서 마지막을 제외한 모든 좌표를 반환한다")
    void intermediatesReturnsAllButLastPosition() {
        List<Position> positions = List.of(
                pos(Col.A, Row.ONE),
                pos(Col.A, Row.TWO),
                pos(Col.A, Row.THREE)
        );
        Path path = new Path(positions);

        assertThat(path.intermediates())
                .containsExactly(pos(Col.A, Row.ONE), pos(Col.A, Row.TWO));
    }

}
