package domain.movement;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Path 클래스 테스트")
class PathTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    @Test
    @DisplayName("positions: 생성 시 전달한 좌표 목록을 그대로 반환한다")
    void positionsReturnGivenPath() {
        List<Position> positions = List.of(
                pos(Column.A, Row.ONE),
                pos(Column.A, Row.TWO),
                pos(Column.A, Row.THREE)
        );
        Path path = new Path(positions);

        assertThat(path.positions()).containsExactlyElementsOf(positions);
    }

    @Test
    @DisplayName("positionsBeforeDestination: 마지막 도착 좌표를 제외한 좌표를 반환한다")
    void positionsBeforeDestinationExcludeLastPosition() {
        Path path = new Path(List.of(
                pos(Column.A, Row.ONE),
                pos(Column.A, Row.TWO),
                pos(Column.A, Row.THREE)
        ));

        assertThat(path.positionsBeforeDestination()).containsExactly(
                pos(Column.A, Row.ONE),
                pos(Column.A, Row.TWO)
        );
    }

    @Test
    @DisplayName("positions: 외부에서 수정할 수 없는 목록을 반환한다")
    void positionsAreUnmodifiable() {
        Path path = new Path(List.of(pos(Column.A, Row.ONE), pos(Column.A, Row.TWO)));

        assertThatThrownBy(() -> path.positions().add(pos(Column.A, Row.THREE)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

}
