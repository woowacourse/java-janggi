package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Country;
import domain.Path;
import domain.Position;
import domain.state.EmptyState;
import domain.state.FullState;
import domain.state.State;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CannonTest {
    private final Path path = new Path();

    @Test
    @DisplayName("포의 아래 목적지까지의 경로를 정확히 계산한다.")
    void cannonDownPathTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(4, 0);

        path.add(new Position(4, 4));
        path.add(new Position(4, 3));
        path.add(new Position(4, 2));
        path.add(new Position(4, 1));
        path.add(new Position(4, 0));

        assertThat(cannon.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("포의 위 목적지까지의 경로를 정확히 계산한다.")
    void cannonUpPathTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(4, 8);

        path.add(new Position(4, 4));
        path.add(new Position(4, 5));
        path.add(new Position(4, 6));
        path.add(new Position(4, 7));
        path.add(new Position(4, 8));

        assertThat(cannon.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("포의 왼쪽 목적지까지의 경로를 정확히 계산한다.")
    void cannonLeftPathTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(0, 4);

        path.add(new Position(4, 4));
        path.add(new Position(3, 4));
        path.add(new Position(2, 4));
        path.add(new Position(1, 4));
        path.add(new Position(0, 4));

        assertThat(cannon.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("포의 오른쪽 목적지까지의 경로를 정확히 계산한다.")
    void cannonRightPathTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(8, 4);

        path.add(new Position(4, 4));
        path.add(new Position(5, 4));
        path.add(new Position(6, 4));
        path.add(new Position(7, 4));
        path.add(new Position(8, 4));

        assertThat(cannon.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("포의 궁성 내부 오른쪽 위 목적지까지의 경로를 정확히 계산한다.")
    void cannonRightUpPathInsidePalaceTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(3, 0);
        Position to = new Position(5, 2);

        path.add(new Position(3, 0));
        path.add(new Position(4, 1));
        path.add(new Position(5, 2));

        assertThat(cannon.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("포의 궁성 내부 오른쪽 아래 목적지까지의 경로를 정확히 계산한다.")
    void cannonRightDownPathInsidePalaceTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(3, 2);
        Position to = new Position(5, 0);

        path.add(new Position(3, 2));
        path.add(new Position(4, 1));
        path.add(new Position(5, 0));

        assertThat(cannon.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("포의 궁성 내부 왼쪽 위 목적지까지의 경로를 정확히 계산한다.")
    void cannonLeftUpPathInsidePalaceTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(5, 0);
        Position to = new Position(3, 2);

        path.add(new Position(5, 0));
        path.add(new Position(4, 1));
        path.add(new Position(3, 2));

        assertThat(cannon.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("포의 궁성 내부 왼쪽 아래 목적지까지의 경로를 정확히 계산한다.")
    void cannonLeftDownPathInsidePalaceTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(5, 2);
        Position to = new Position(3, 0);

        path.add(new Position(5, 2));
        path.add(new Position(4, 1));
        path.add(new Position(3, 0));

        assertThat(cannon.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("포가 하나의 방향으로만 이동하지 않을 경우 예외가 발생한다.")
    void cannonOneDirectionExceptionTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(1, 2);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> cannon.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 하나의 방향으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("포가 대각선 이동이 불가한 위치에서 대각선으로 이동할 경우 예외가 발생한다.")
    void cannonDiagonalExceptionTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(1, 1);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> cannon.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 대각선으로 이동이 불가한 위치입니다.");
    }

    @Test
    @DisplayName("포가 궁성 내부에서 바깥까지 대각선으로 이동할 경우 예외가 발생한다.")
    void cannonDiagonalOutsidePalaceExceptionTest() {
        Piece cannon = new Cannon(Country.CHO);

        Position from = new Position(5, 9);
        Position to = new Position(2, 6);

        assertThatThrownBy(() -> cannon.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 대각선으로 이동이 불가한 위치입니다.");
    }

    @Test
    @DisplayName("포의 from, to 사이 경로에 기물이 1개가 아니면 예외가 발생한다.")
    void cannonJumpPieceCountExceptionTest() {
        Cannon cannon = new Cannon(Country.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(1, 1), new FullState(cannon));
        pathStates.put(new Position(1, 2), new FullState(new Soldier(Country.HAN)));
        pathStates.put(new Position(1, 3), new FullState(new Soldier(Country.HAN)));
        pathStates.put(new Position(1, 4), new EmptyState());

        assertThatThrownBy(() -> cannon.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 하나의 기물만 뛰어 넘을 수 있습니다.");
    }

    @Test
    @DisplayName("포가 포를 뛰어 넘으려 할 경우 예외가 발생한다.")
    void cannonJumpCannonExceptionTest() {
        Cannon cannon = new Cannon(Country.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(1, 1), new FullState(cannon));
        pathStates.put(new Position(1, 2), new FullState(new Cannon(Country.HAN)));
        pathStates.put(new Position(1, 3), new EmptyState());

        assertThatThrownBy(() -> cannon.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 뛰어 넘을 수 없습니다.");
    }

    @Test
    @DisplayName("포가 포를 잡으려 할 경우 예외가 발생한다.")
    void cannonKillCannonExceptionTest() {
        Piece cannon = new Cannon(Country.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(1, 1), new FullState(cannon));
        pathStates.put(new Position(1, 2), new FullState(new Soldier(Country.HAN)));
        pathStates.put(new Position(1, 3), new FullState(new Cannon(Country.HAN)));

        assertThatThrownBy(() -> cannon.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 잡을 수 없습니다.");
    }

    @Test
    @DisplayName("도착 위치에 같은 진영의 기물이 있을 경우 예외가 발생한다.")
    void cannonMoveSameCountryPieceExceptionTest() {
        Piece cannon = new Cannon(Country.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(1, 1), new FullState(cannon));
        pathStates.put(new Position(1, 2), new FullState(new Soldier(Country.HAN)));
        pathStates.put(new Position(1, 3), new FullState(new Soldier(Country.CHO)));

        assertThatThrownBy(() -> cannon.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.");
    }
}
