package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import janggi.domain.piece.move.Path;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("경로 테스트")
class PathTest {

    @DisplayName("방향으로 경로를 찾을 수 있다.")
    @Test
    void calculatePath_Direction() {
        // given
        Point start = new Point(1, 1);
        Point end = new Point(4, 4);
        List<Direction> availablePath = List.of(Direction.DOWN_LEFT, Direction.DOWN_RIGHT);

        // when
        Path path = Path.calculatePath(start, end, availablePath);

        // then
        assertThat(path.getPath())
                .isEqualTo(List.of(Direction.DOWN_RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT));
    }

    @DisplayName("방향으로 경로를 찾을 수 없으면 예외가 발생한다.")
    @Test
    void calculatePath_DirectionException() {
        // given
        Point start = new Point(1, 1);
        Point end = new Point(4, 3);
        List<Direction> availablePath = List.of(Direction.DOWN_LEFT, Direction.DOWN_RIGHT);

        // then
        assertThatThrownBy(() -> Path.calculatePath(start, end, availablePath))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("고정된 경로를 찾을 수 있다.")
    @Test
    void calculatePath_Path() {
        // given
        Point start = new Point(1, 1);
        Point end = new Point(3, 2);
        Set<List<Direction>> availablePath = Set.of(
                List.of(Direction.DOWN_LEFT, Direction.DOWN),
                List.of(Direction.DOWN_RIGHT, Direction.DOWN)
        );

        // when
        Path path = Path.calculatePath(start, end, availablePath);

        // then
        assertThat(path.getPath())
                .isEqualTo(List.of(Direction.DOWN_RIGHT, Direction.DOWN));
    }

    @DisplayName("고정된 경로를 찾을 수 없으면 예외가 발생한다.")
    @Test
    void calculatePath_PathException() {
        // given
        Point start = new Point(1, 1);
        Point end = new Point(3, 3);
        Set<List<Direction>> availablePath = Set.of(
                List.of(Direction.DOWN_LEFT, Direction.DOWN),
                List.of(Direction.DOWN_RIGHT, Direction.DOWN)
        );

        // then
        assertThatThrownBy(() -> Path.calculatePath(start, end, availablePath))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("경로로 이동 좌표를 알 수 있다.")
    @Test
    void getMovedPoints() {
        // given
        Point start = new Point(4, 2);
        Point end = new Point(1, 1);
        Path path = new Path(List.of(Direction.UP, Direction.UP, Direction.UP_LEFT));

        // when
        List<Point> movedPoints = path.getMovedPoints(start, end);

        // then
        assertThat(movedPoints)
                .isEqualTo(List.of(new Point(4, 2), new Point(3, 2), new Point(2, 2), new Point(1, 1)));
    }
}