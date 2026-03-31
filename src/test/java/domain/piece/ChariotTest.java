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

public class ChariotTest {
    private final Path path = new Path();

    @Test
    @DisplayName("차의 아래 목적지까지의 경로를 정확히 계산한다.")
    void chariotDownPathTest() {
        Piece chariot = new Chariot(Country.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(4, 0);

        path.add(new Position(4, 4));
        path.add(new Position(4, 3));
        path.add(new Position(4, 2));
        path.add(new Position(4, 1));
        path.add(new Position(4, 0));

        assertThat(chariot.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("차의 위 목적지까지의 경로를 정확히 계산한다.")
    void chariotUpPathTest() {
        Piece chariot = new Chariot(Country.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(4, 8);

        path.add(new Position(4, 4));
        path.add(new Position(4, 5));
        path.add(new Position(4, 6));
        path.add(new Position(4, 7));
        path.add(new Position(4, 8));

        assertThat(chariot.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("차의 왼쪽 목적지까지의 경로를 정확히 계산한다.")
    void chariotLeftPathTest() {
        Piece chariot = new Chariot(Country.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(0, 4);

        path.add(new Position(4, 4));
        path.add(new Position(3, 4));
        path.add(new Position(2, 4));
        path.add(new Position(1, 4));
        path.add(new Position(0, 4));

        assertThat(chariot.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("차의 오른쪽 목적지까지의 경로를 정확히 계산한다.")
    void chariotRightPathTest() {
        Piece chariot = new Chariot(Country.CHO);

        Position from = new Position(4, 4);
        Position to = new Position(8, 4);

        path.add(new Position(4, 4));
        path.add(new Position(5, 4));
        path.add(new Position(6, 4));
        path.add(new Position(7, 4));
        path.add(new Position(8, 4));

        assertThat(chariot.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("차가 하나의 방향으로만 이동하지 않을 경우 예외가 발생한다.")
    void chariotOneDirectionExceptionTest() {
        Piece chariot = new Chariot(Country.CHO);

        Position from = new Position(1, 2);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> chariot.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 하나의 방향으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("차가 대각선으로 이동할 경우 예외가 발생한다.")
    void chariotDiagonalExceptionTest() {
        Piece chariot = new Chariot(Country.CHO);

        Position from = new Position(1, 1);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> chariot.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 직선으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("차의 경로에 다른 기물이 존재하면 예외가 발생한다.")
    void chariotOtherPieceExistPathExceptionTest() {
        Piece chariot = new Chariot(Country.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(1, 1), new FullState(chariot));
        pathStates.put(new Position(1, 2), new FullState(new Soldier(Country.HAN)));
        pathStates.put(new Position(1, 3), new FullState(new Soldier(Country.HAN)));

        assertThatThrownBy(() -> chariot.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동 경로에 다른 기물이 존재해 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 위치에 같은 진영의 기물이 있을 경우 예외가 발생한다.")
    void chariotMoveSameCountryPieceExceptionTest() {
        Piece chariot = new Chariot(Country.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(1, 1), new FullState(chariot));
        pathStates.put(new Position(1, 2), new EmptyState());
        pathStates.put(new Position(1, 3), new FullState(new Soldier(Country.CHO)));

        assertThatThrownBy(() -> chariot.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.");
    }
}
