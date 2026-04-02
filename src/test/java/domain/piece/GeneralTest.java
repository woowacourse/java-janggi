package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.CountryType;
import domain.Path;
import domain.Position;
import domain.state.EmptyState;
import domain.state.FullState;
import domain.state.State;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GeneralTest {
    private final Path path = new Path();

    @Test
    @DisplayName("궁의 아래 목적지까지의 경로를 정확히 계산한다.")
    void generalDownPathTest() {
        Piece general = new General(CountryType.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(4, 0);

        path.add(new Position(4, 1));
        path.add(new Position(4, 0));

        assertThat(general.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("궁의 위 목적지까지의 경로를 정확히 계산한다.")
    void generalUpPathTest() {
        Piece general = new General(CountryType.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(4, 2);

        path.add(new Position(4, 1));
        path.add(new Position(4, 2));

        assertThat(general.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("궁의 왼쪽 목적지까지의 경로를 정확히 계산한다.")
    void generalLeftPathTest() {
        Piece general = new General(CountryType.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(3, 1);

        path.add(new Position(4, 1));
        path.add(new Position(3, 1));

        assertThat(general.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("궁의 오른쪽 목적지까지의 경로를 정확히 계산한다.")
    void generalRightPathTest() {
        Piece general = new General(CountryType.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(5, 1);

        path.add(new Position(4, 1));
        path.add(new Position(5, 1));

        assertThat(general.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("궁의 궁성 내부 오른쪽 위 목적지까지의 경로를 정확히 계산한다.")
    void generalRightUpPathInsidePalaceTest() {
        Piece general = new General(CountryType.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(5, 2);

        path.add(new Position(4, 1));
        path.add(new Position(5, 2));

        assertThat(general.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("궁의 궁성 내부 오른쪽 아래 목적지까지의 경로를 정확히 계산한다.")
    void generalRightDownPathInsidePalaceTest() {
        Piece general = new General(CountryType.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(5, 0);

        path.add(new Position(4, 1));
        path.add(new Position(5, 0));

        assertThat(general.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("궁의 궁성 내부 왼쪽 위 목적지까지의 경로를 정확히 계산한다.")
    void generalLeftUpPathInsidePalaceTest() {
        Piece general = new General(CountryType.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(3, 2);

        path.add(new Position(4, 1));
        path.add(new Position(3, 2));

        assertThat(general.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("궁의 궁성 내부 왼쪽 아래 목적지까지의 경로를 정확히 계산한다.")
    void generalLeftDownPathInsidePalaceTest() {
        Piece general = new General(CountryType.CHO);

        Position from = new Position(4, 1);
        Position to = new Position(3, 0);

        path.add(new Position(4, 1));
        path.add(new Position(3, 0));

        assertThat(general.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("궁의 방향의 크기가 1이 아닌 경우 예외가 발생한다.")
    void generalDirectionSizeExceptionTest() {
        Piece general = new General(CountryType.CHO);

        Position from = new Position(3, 0);
        Position to = new Position(3, 2);

        assertThatThrownBy(() -> general.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 한 칸만 이동할 수 있습니다.");
    }

    @Test
    @DisplayName("궁이 대각선 이동이 불가한 위치에서 대각선으로 이동할 경우 예외가 발생한다.")
    void generalDiagonalExceptionTest() {
        Piece general = new General(CountryType.CHO);

        Position from = new Position(4, 0);
        Position to = new Position(3, 1);

        assertThatThrownBy(() -> general.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 대각선으로 이동이 불가한 위치입니다.");
    }

    @Test
    @DisplayName("도착 위치에 같은 진영의 기물이 있을 경우 예외가 발생한다.")
    void generalMoveSameCountryPieceExceptionTest() {
        Piece general = new General(CountryType.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(4, 1), new FullState(general));
        pathStates.put(new Position(4, 2), new FullState(new Soldier(CountryType.CHO)));

        assertThatThrownBy(() -> general.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 진영의 기물이 있는 위치로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("초나라 궁이 궁성 밖으로 나갈 경우 예외가 발생한다.")
    void choGeneralMoveOutsidePalaceExceptionTest() {
        Piece general = new General(CountryType.CHO);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(3, 0), new FullState(general));
        pathStates.put(new Position(2, 0), new EmptyState());

        assertThatThrownBy(() -> general.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 궁성 외부로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("한나라 궁이 궁성 밖으로 나갈 경우 예외가 발생한다.")
    void hanGeneralMoveOutsidePalaceExceptionTest() {
        Piece general = new General(CountryType.HAN);

        Map<Position, State> pathStates = new LinkedHashMap<>();
        pathStates.put(new Position(3, 7), new FullState(general));
        pathStates.put(new Position(2, 7), new EmptyState());

        assertThatThrownBy(() -> general.validateMove(pathStates))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 궁성 외부로 이동할 수 없습니다.");
    }
}
