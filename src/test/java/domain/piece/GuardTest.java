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

public class GuardTest {
    private final Path path = new Path();

    @Test
    @DisplayName("사의 아래 목적지까지의 경로를 정확히 계산한다.")
    void guardDownPathTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(4, 0);

        path.add(new Position(4, 1));
        path.add(new Position(4, 0));

        assertThat(guard.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("사의 위 목적지까지의 경로를 정확히 계산한다.")
    void guardUpPathTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(4, 2);

        path.add(new Position(4, 1));
        path.add(new Position(4, 2));

        assertThat(guard.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("사의 왼쪽 목적지까지의 경로를 정확히 계산한다.")
    void guardLeftPathTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(3, 1);

        path.add(new Position(4, 1));
        path.add(new Position(3, 1));

        assertThat(guard.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("사의 오른쪽 목적지까지의 경로를 정확히 계산한다.")
    void guardRightPathTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(5, 1);

        path.add(new Position(4, 1));
        path.add(new Position(5, 1));

        assertThat(guard.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("사의 궁성 내부 오른쪽 위 목적지까지의 경로를 정확히 계산한다.")
    void guardRightUpPathInsidePalaceTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(5, 2);

        path.add(new Position(4, 1));
        path.add(new Position(5, 2));

        assertThat(guard.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("사의 궁성 내부 오른쪽 아래 목적지까지의 경로를 정확히 계산한다.")
    void guardRightDownPathInsidePalaceTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(5, 0);

        path.add(new Position(4, 1));
        path.add(new Position(5, 0));

        assertThat(guard.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("사의 궁성 내부 왼쪽 위 목적지까지의 경로를 정확히 계산한다.")
    void guardLeftUpPathInsidePalaceTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(3, 2);

        path.add(new Position(4, 1));
        path.add(new Position(3, 2));

        assertThat(guard.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("사의 궁성 내부 왼쪽 아래 목적지까지의 경로를 정확히 계산한다.")
    void guardLeftDownPathInsidePalaceTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(3, 0);

        path.add(new Position(4, 1));
        path.add(new Position(3, 0));

        assertThat(guard.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("사의 방향의 크기가 1이 아닌 경우 예외가 발생한다.")
    void guardDirectionSizeExceptionTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(3, 0);
        Position to = new Position(3, 2);

        assertThatThrownBy(() -> guard.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 한 칸만 이동할 수 있습니다.");
    }

    @Test
    @DisplayName("사가 대각선 이동이 불가한 위치에서 대각선으로 이동할 경우 예외가 발생한다.")
    void guardDiagonalExceptionTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(4, 0);
        Position to = new Position(3, 1);

        assertThatThrownBy(() -> guard.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 대각선으로 이동이 불가한 위치입니다.");
    }

    @Test
    @DisplayName("도착 위치에 같은 진영의 기물이 있을 경우 예외가 발생한다.")
    void guardMoveSameCountryPieceExceptionTest() {
        Piece guard = new Guard(Country.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(4, 1), new FullState(guard));
        pathStates.put(new Position(4, 2), new FullState(new Soldier(Country.CHO)));

        assertThatThrownBy(() -> guard.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("초나라 사가 궁성 밖으로 나갈 경우 예외가 발생한다.")
    void choGuardMoveOutsidePalaceExceptionTest() {
        Piece guard = new Guard(Country.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(3, 7), new FullState(guard));
        pathStates.put(new Position(2, 7), new EmptyState());

        assertThatThrownBy(() -> guard.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 궁성 외부로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("한나라 사가 궁성 밖으로 나갈 경우 예외가 발생한다.")
    void hanGuardMoveOutsidePalaceExceptionTest() {
        Piece guard = new Guard(Country.HAN);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(3, 7), new FullState(guard));
        pathStates.put(new Position(2, 7), new EmptyState());

        assertThatThrownBy(() -> guard.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 궁성 외부로 이동할 수 없습니다.");
    }
}
