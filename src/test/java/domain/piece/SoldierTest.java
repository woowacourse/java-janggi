package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.country.CountryType;
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
        Piece choSoldier = new Soldier(CountryType.CHO);

        Position from = new Position(2, 3);
        Position to = new Position(2, 4);

        path.add(new Position(2, 3));
        path.add(new Position(2, 4));

        assertThat(choSoldier.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("한나라 졸병의 아래 목적지까지의 경로를 정확히 계산한다.")
    void soldierUpPathTest() {
        Piece hanSoldier = new Soldier(CountryType.HAN);

        Position from = new Position(2, 6);
        Position to = new Position(2, 5);

        path.add(new Position(2, 6));
        path.add(new Position(2, 5));

        assertThat(hanSoldier.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("졸병의 왼쪽 목적지까지의 경로를 정확히 계산한다.")
    void soldierLeftPathTest() {
        Piece soldier = new Soldier(CountryType.CHO);

        Position from = new Position(2, 3);
        Position to = new Position(1, 3);

        path.add(new Position(2, 3));
        path.add(new Position(1, 3));

        assertThat(soldier.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("졸병의 오른쪽 목적지까지의 경로를 정확히 계산한다.")
    void soldierRightPathTest() {
        Piece soldier = new Soldier(CountryType.CHO);

        Position from = new Position(2, 3);
        Position to = new Position(3, 3);

        path.add(new Position(2, 3));
        path.add(new Position(3, 3));

        assertThat(soldier.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("초나라 졸병의 상대 궁성 내부 오른쪽 위 목적지까지의 경로를 정확히 계산한다.")
    void soldierRightUpPathInsidePalaceTest() {
        Piece soldier = new Soldier(CountryType.CHO);

        Position from = new Position(4, 8);
        Position to = new Position(5, 9);

        path.add(new Position(4, 8));
        path.add(new Position(5, 9));

        assertThat(soldier.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("초나라 졸병의 상대 궁성 내부 왼쪽 위 목적지까지의 경로를 정확히 계산한다.")
    void soldierLeftUpPathInsidePalaceTest() {
        Piece soldier = new Soldier(CountryType.CHO);

        Position from = new Position(4, 8);
        Position to = new Position(3, 9);

        path.add(new Position(4, 8));
        path.add(new Position(3, 9));

        assertThat(soldier.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("한나라 졸병의 상대 궁성 내부 오른쪽 아래 목적지까지의 경로를 정확히 계산한다.")
    void soldierRightDownPathInsidePalaceTest() {
        Piece soldier = new Soldier(CountryType.HAN);

        Position from = new Position(4, 1);
        Position to = new Position(5, 0);

        path.add(new Position(4, 1));
        path.add(new Position(5, 0));

        assertThat(soldier.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("한나라 졸병의 궁성 내부 왼쪽 아래 목적지까지의 경로를 정확히 계산한다.")
    void soldierLeftDownPathInsidePalaceTest() {
        Piece soldier = new Soldier(CountryType.HAN);

        Position from = new Position(4, 1);
        Position to = new Position(3, 0);

        path.add(new Position(4, 1));
        path.add(new Position(3, 0));

        assertThat(soldier.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("초나라 졸병이 후진할 경우 예외가 발생한다.")
    void choSoldierDownExceptionTest() {
        Piece soldier = new Soldier(CountryType.CHO);

        Position from = new Position(4, 1);
        Position straightTo = new Position(4, 0);
        Position diagonalTo = new Position(3, 0);

        assertThatThrownBy(() -> soldier.path(from, straightTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
        assertThatThrownBy(() -> soldier.path(from, diagonalTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
    }

    @Test
    @DisplayName("한나라 졸병이 후진할 경우 예외가 발생한다.")
    void hanSoldierDownExceptionTest() {
        Piece soldier = new Soldier(CountryType.HAN);

        Position from = new Position(4, 8);
        Position straightTo = new Position(4, 9);
        Position diagonalTo = new Position(3, 9);

        assertThatThrownBy(() -> soldier.path(from, straightTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
        assertThatThrownBy(() -> soldier.path(from, diagonalTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
    }


    @Test
    @DisplayName("졸병의 방향의 크기가 1이 아닌 경우 예외가 발생한다.")
    void soldierDirectionSizeExceptionTest() {
        Piece soldier = new Soldier(CountryType.CHO);

        Position from = new Position(1, 1);
        Position to = new Position(1, 3);

        assertThatThrownBy(() -> soldier.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 한 칸만 이동할 수 있습니다.");
    }

    @Test
    @DisplayName("졸병이 궁성 내부에서 바깥까지 대각선으로 이동할 경우 예외가 발생한다.")
    void soliderDiagonalOutsidePalaceExceptionTest() {
        Piece solider = new Soldier(CountryType.CHO);

        Position from = new Position(3, 7);
        Position to = new Position(2, 6);

        assertThatThrownBy(() -> solider.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 대각선으로 이동이 불가한 위치입니다.");
    }

    @Test
    @DisplayName("졸병이 대각선 이동이 불가한 위치에서 대각선으로 이동할 경우 예외가 발생한다.")
    void soldierDiagonalExceptionTest() {
        Piece choSoldier = new Soldier(CountryType.CHO);
        Piece hanSoldier = new Soldier(CountryType.HAN);

        Position from = new Position(1, 1);
        Position choTo = new Position(2, 2);
        Position hanTo = new Position(0, 0);

        assertThatThrownBy(() -> choSoldier.path(from, choTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 대각선으로 이동이 불가한 위치입니다.");
        assertThatThrownBy(() -> hanSoldier.path(from, hanTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 대각선으로 이동이 불가한 위치입니다.");
    }

    @Test
    @DisplayName("도착 위치에 같은 진영의 기물이 있을 경우 예외가 발생한다.")
    void soldierMoveSameCountryPieceExceptionTest() {
        Piece soldier = new Soldier(CountryType.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(1, 1), new FullState(soldier));
        pathStates.put(new Position(1, 2), new FullState(new Soldier(CountryType.CHO)));

        assertThatThrownBy(() -> soldier.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.");
    }
}
