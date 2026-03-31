package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Country;
import domain.Path;
import domain.Position;
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
        Piece guard = new General(Country.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(4, 0);

        path.add(new Position(4, 1));
        path.add(new Position(4, 0));

        assertThat(guard.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("사의 위 목적지까지의 경로를 정확히 계산한다.")
    void guardUpPathTest() {
        Piece guard = new General(Country.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(4, 2);

        path.add(new Position(4, 1));
        path.add(new Position(4, 2));

        assertThat(guard.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("사의 왼쪽 목적지까지의 경로를 정확히 계산한다.")
    void guardLeftPathTest() {
        Piece guard = new General(Country.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(3, 1);

        path.add(new Position(4, 1));
        path.add(new Position(3, 1));

        assertThat(guard.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("사의 오른쪽 목적지까지의 경로를 정확히 계산한다.")
    void guardRightPathTest() {
        Piece guard = new General(Country.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(5, 1);

        path.add(new Position(4, 1));
        path.add(new Position(5, 1));

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
    @DisplayName("사가 대각선으로 이동할 경우 예외가 발생한다.")
    void guardDiagonalExceptionTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(3, 0);
        Position choTo = new Position(2, 1);

        assertThatThrownBy(() -> guard.path(from, choTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 직선으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("도착 위치에 같은 진영의 기물이 있을 경우 예외가 발생한다.")
    void guardMoveSameCountryPieceExceptionTest() {
        Piece guard = new Guard(Country.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(1, 1), new FullState(guard));
        pathStates.put(new Position(1, 2), new FullState(new Soldier(Country.CHO)));

        assertThatThrownBy(() -> guard.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.");
    }
}
