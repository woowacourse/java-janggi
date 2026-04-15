package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.common.Position;
import janggi.domain.route.Route;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PalaceTest {

    @ParameterizedTest
    @CsvSource({
            "4, 8", "5, 8", "6, 8",
            "4, 9", "5, 9", "6, 9",
            "4, 10", "5, 10", "6, 10"
    })
    @DisplayName("좌표가 초나라 궁성 범위 안에 있는지 확인한다.")
    void 초나라_궁성_좌표_확인(int x, int y) {
        assertThat(Palace.CHO.isInPalace(new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "4, 1", "5, 1", "6, 1",
            "4, 2", "5, 2", "6, 2",
            "4, 3", "5, 3", "6, 3"
    })
    @DisplayName("좌표가 초나라 궁성 범위 안에 있는지 확인한다.")
    void 한나라_궁성_좌표_확인(int x, int y) {
        assertThat(Palace.HAN.isInPalace(new Position(x, y))).isTrue();
    }

    @Test
    @DisplayName("궁성 중앙에서 갈 수 있는 대각선 경로는 4가지이다.")
    void 궁성_중앙에서의_대각선_경로_4가지() {
        // given
        Position position = new Position(5, 9);

        // when
        List<Route> palacePositions = Palace.CHO.findDiagonalRoutes(position);

        // then
        assertThat(palacePositions).hasSize(4);
    }

    @ParameterizedTest
    @CsvSource({
            "4, 8", "6, 8", "4, 10", "6, 10"
    })
    @DisplayName("궁성 꼭지점에서 갈 수 있는 대각선 경로는 1가지이다.")
    void 궁성_꼭지점에서의_대각선_경로_1가지씩_존재(int x, int y) {
        // given
        Position position = new Position(x, y);

        // when
        List<Route> palacePositions = Palace.CHO.findDiagonalRoutes(position);

        // then
        assertThat(palacePositions).hasSize(1);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 1", "4, 2", "6, 2", "5, 3"
    })
    @DisplayName("궁성 모서리에서 갈 수 있는 대각선 경로는 없다.")
    void 궁성_모서리에서의_대각선_경로_없음(int x, int y) {
        // given
        Position position = new Position(x, y);

        // when
        List<Route> palacePositions = Palace.HAN.findDiagonalRoutes(position);

        // then
        assertThat(palacePositions).isEmpty();
    }
}
