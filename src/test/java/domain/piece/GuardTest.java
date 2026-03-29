package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Country;
import domain.Position;
import domain.state.FullState;
import domain.state.State;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GuardTest {
    @ParameterizedTest
    @DisplayName("사의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedChoGuardPaths")
    void choGuardPathTest(Position from, Position to, List<Position> paths) {
        Piece choGuard = new Guard(Country.CHO);
        Piece handGuard = new Guard(Country.HAN);

        assertThat(choGuard.path(from, to)).isEqualTo(paths);
        assertThat(handGuard.path(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedChoGuardPaths() {
        return Stream.of(
                Arguments.arguments(new Position(1, 1), new Position(1, 2),
                        List.of(new Position(1, 1), new Position(1, 2))),
                Arguments.arguments(new Position(1, 1), new Position(0, 1),
                        List.of(new Position(1, 1), new Position(0, 1))),
                Arguments.arguments(new Position(1, 1), new Position(2, 1),
                        List.of(new Position(1, 1), new Position(2, 1))),
                Arguments.arguments(new Position(1, 1), new Position(1, 0),
                        List.of(new Position(1, 1), new Position(1, 0)))
        );
    }

    @Test
    @DisplayName("사의 방향의 크기가 1이 아닌 경우 예외가 발생한다.")
    void guardDirectionSizeExceptionTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(3, 0);
        Position to = new Position(3, 2);

        assertThatThrownBy(() -> guard.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 사는 한 칸만 이동할 수 있습니다.");
    }

    @Test
    @DisplayName("사가 대각선으로 이동할 경우 예외가 발생한다.")
    void soldierDiagonalExceptionTest() {
        Piece guard = new Guard(Country.CHO);

        Position from = new Position(3, 0);
        Position choTo = new Position(2, 1);

        assertThatThrownBy(() -> guard.path(from, choTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 사는 직선으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("도착 위치에 같은 진영의 기물이 있을 경우 예외가 발생한다.")
    void guardMoveSameCountryPieceExceptionTest() {
        Piece guard = new Guard(Country.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(1, 1), new FullState(guard));
        pathStates.put(new Position(1, 2), new FullState(new Soldier(Country.CHO)));

        assertThatThrownBy(() -> guard.canMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.");
    }
}
