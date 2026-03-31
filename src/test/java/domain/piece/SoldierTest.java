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

public class SoldierTest {
    private final Path path = new Path();

    @Test
    @DisplayName("초나라 졸병의 위 목적지까지의 경로를 정확히 계산한다.")
    void choSoldierDownPathTest() {
        Piece choSoldier = new Soldier(Country.CHO);

        Position from = new Position(2, 3);
        Position to = new Position(2, 4);

        path.add(new Position(2, 3));
        path.add(new Position(2, 4));

        assertThat(choSoldier.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("한나라 졸병의 아래 목적지까지의 경로를 정확히 계산한다.")
    void soldierUpPathTest() {
        Piece hanSoldier = new Soldier(Country.HAN);

        Position from = new Position(2, 6);
        Position to = new Position(2, 5);

        path.add(new Position(2, 6));
        path.add(new Position(2, 5));

        assertThat(hanSoldier.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("졸병의 왼쪽 목적지까지의 경로를 정확히 계산한다.")
    void soldierLeftPathTest() {
        Piece soldier = new Soldier(Country.CHO);

        Position from = new Position(2, 3);
        Position to = new Position(1, 3);

        path.add(new Position(2, 3));
        path.add(new Position(1, 3));

        assertThat(soldier.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("졸병의 오른쪽 목적지까지의 경로를 정확히 계산한다.")
    void soldierRightPathTest() {
        Piece soldier = new Soldier(Country.CHO);

        Position from = new Position(2, 3);
        Position to = new Position(3, 3);

        path.add(new Position(2, 3));
        path.add(new Position(3, 3));

        assertThat(soldier.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("졸병이 후진할 경우 예외가 발생한다.")
    void soldierDownExceptionTest() {
        Piece choSoldier = new Soldier(Country.CHO);
        Piece hanSoldier = new Soldier(Country.HAN);

        Position from = new Position(1, 1);
        Position choTo = new Position(1, 0);
        Position hanTo = new Position(1, 2);

        assertThatThrownBy(() -> choSoldier.path(from, choTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
        assertThatThrownBy(() -> hanSoldier.path(from, hanTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
    }

    @Test
    @DisplayName("졸병의 방향의 크기가 1이 아닌 경우 예외가 발생한다.")
    void soldierDirectionSizeExceptionTest() {
        Piece soldier = new Soldier(Country.CHO);

        Position from = new Position(1, 1);
        Position to = new Position(1, 3);

        assertThatThrownBy(() -> soldier.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 한 칸만 이동할 수 있습니다.");
    }

    @Test
    @DisplayName("졸병이 대각선으로 이동할 경우 예외가 발생한다.")
    void soldierDiagonalExceptionTest() {
        Piece choSoldier = new Soldier(Country.CHO);
        Piece hanSoldier = new Soldier(Country.HAN);

        Position from = new Position(1, 1);
        Position choTo = new Position(2, 2);
        Position hanTo = new Position(0, 0);

        assertThatThrownBy(() -> choSoldier.path(from, choTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 직선으로만 이동 가능합니다.");
        assertThatThrownBy(() -> hanSoldier.path(from, hanTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 직선으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("도착 위치에 같은 진영의 기물이 있을 경우 예외가 발생한다.")
    void soldierMoveSameCountryPieceExceptionTest() {
        Piece soldier = new Soldier(Country.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(1, 1), new FullState(soldier));
        pathStates.put(new Position(1, 2), new FullState(new Soldier(Country.CHO)));

        assertThatThrownBy(() -> soldier.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.");
    }
}
