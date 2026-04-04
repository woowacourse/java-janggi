package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Path;
import domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StraightMoveStrategyTest {
    private final Path path = new Path();

    @Test
    @DisplayName("아래 목적지까지의 경로를 정확히 계산한다.")
    void straightMoveDownPathTest() {
        MoveStrategy moveStrategy = new StraightMoveStrategy();

        Position from = new Position(4, 4);
        Position to = new Position(4, 0);

        path.add(new Position(4, 4));
        path.add(new Position(4, 3));
        path.add(new Position(4, 2));
        path.add(new Position(4, 1));
        path.add(new Position(4, 0));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("위 목적지까지의 경로를 정확히 계산한다.")
    void straightMoveUpPathTest() {
        MoveStrategy moveStrategy = new StraightMoveStrategy();

        Position from = new Position(4, 4);
        Position to = new Position(4, 8);

        path.add(new Position(4, 4));
        path.add(new Position(4, 5));
        path.add(new Position(4, 6));
        path.add(new Position(4, 7));
        path.add(new Position(4, 8));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("왼쪽 목적지까지의 경로를 정확히 계산한다.")
    void straightMoveLeftPathTest() {
        MoveStrategy moveStrategy = new StraightMoveStrategy();

        Position from = new Position(4, 4);
        Position to = new Position(0, 4);

        path.add(new Position(4, 4));
        path.add(new Position(3, 4));
        path.add(new Position(2, 4));
        path.add(new Position(1, 4));
        path.add(new Position(0, 4));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("오른쪽 목적지까지의 경로를 정확히 계산한다.")
    void straightMoveRightPathTest() {
        MoveStrategy moveStrategy = new StraightMoveStrategy();

        Position from = new Position(4, 4);
        Position to = new Position(8, 4);

        path.add(new Position(4, 4));
        path.add(new Position(5, 4));
        path.add(new Position(6, 4));
        path.add(new Position(7, 4));
        path.add(new Position(8, 4));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("궁성 내부 오른쪽 위 목적지까지의 경로를 정확히 계산한다.")
    void straightMoveRightUpPathInsidePalaceTest() {
        MoveStrategy moveStrategy = new StraightMoveStrategy();

        Position from = new Position(3, 0);
        Position to = new Position(5, 2);

        path.add(new Position(3, 0));
        path.add(new Position(4, 1));
        path.add(new Position(5, 2));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("궁성 내부 오른쪽 아래 목적지까지의 경로를 정확히 계산한다.")
    void straightMoveRightDownPathInsidePalaceTest() {
        MoveStrategy moveStrategy = new StraightMoveStrategy();

        Position from = new Position(3, 2);
        Position to = new Position(5, 0);

        path.add(new Position(3, 2));
        path.add(new Position(4, 1));
        path.add(new Position(5, 0));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("궁성 내부 왼쪽 위 목적지까지의 경로를 정확히 계산한다.")
    void straightMoveLeftUpPathInsidePalaceTest() {
        MoveStrategy moveStrategy = new StraightMoveStrategy();

        Position from = new Position(5, 0);
        Position to = new Position(3, 2);

        path.add(new Position(5, 0));
        path.add(new Position(4, 1));
        path.add(new Position(3, 2));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("궁성 내부 왼쪽 아래 목적지까지의 경로를 정확히 계산한다.")
    void straightMoveLeftDownPathInsidePalaceTest() {
        MoveStrategy moveStrategy = new StraightMoveStrategy();

        Position from = new Position(5, 2);
        Position to = new Position(3, 0);

        path.add(new Position(5, 2));
        path.add(new Position(4, 1));
        path.add(new Position(3, 0));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("하나의 방향으로만 이동하지 않을 경우 예외가 발생한다.")
    void straightMoveOneDirectionExceptionTest() {
        MoveStrategy moveStrategy = new StraightMoveStrategy();

        Position from = new Position(1, 2);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> moveStrategy.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 하나의 방향으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("대각선 이동이 불가한 위치에서 대각선으로 이동할 경우 예외가 발생한다.")
    void straightMoveDiagonalExceptionTest() {
        MoveStrategy moveStrategy = new StraightMoveStrategy();

        Position from = new Position(1, 1);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> moveStrategy.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 대각선으로 이동이 불가한 위치입니다.");
    }

    @Test
    @DisplayName("궁성 내부에서 바깥까지 대각선으로 이동할 경우 예외가 발생한다.")
    void straightMoveDiagonalOutsidePalaceExceptionTest() {
        MoveStrategy moveStrategy = new StraightMoveStrategy();

        Position from = new Position(5, 9);
        Position to = new Position(2, 6);

        assertThatThrownBy(() -> moveStrategy.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 대각선으로 이동이 불가한 위치입니다.");
    }
}
