package janggi.domain.piece;

import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.domain.Side;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class SaTest {
    @ParameterizedTest
    @CsvSource({
            "1,4,2,5",
            "1,4,2,4",
            "1,6,1,5",
            "1,6,2,5",
            "3,4,2,4",
            "3,6,2,5"
    })
    void 사는_한_진영일때_궁성_내_인접_좌표로_한_칸_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Sa sa = new Sa(Side.HAN, PalaceTopology.from());

        List<Position> actual = sa.findRoute(startPosition, endPosition);

        assertThat(actual.getLast()).isEqualTo(endPosition);
    }

    @ParameterizedTest
    @CsvSource({
            "10,4,9,5",
            "10,4,9,4",
            "10,6,10,5",
            "10,6,9,5",
            "8,4,9,4",
            "8,6,9,5"
    })
    void 사는_초_진영일때_궁성_내_인접_좌표로_한_칸_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Sa sa = new Sa(Side.CHO, PalaceTopology.from());

        List<Position> actual = sa.findRoute(startPosition, endPosition);

        assertThat(actual.getLast()).isEqualTo(endPosition);
    }

    @ParameterizedTest
    @CsvSource({
            "1,4,3,6",
            "1,4,3,4",
            "1,6,3,5",
    })
    void 사는_한_칸_초과로_이동할_수_없다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Sa sa = new Sa(Side.HAN, PalaceTopology.from());

        assertThatThrownBy(() -> sa.findRoute(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "1,4,1,3",
            "1,6,1,7",
            "3,4,4,4",
            "3,6,3,7"
    })
    void 사는_궁성_외부로_이동할_수_없다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Sa sa = new Sa(Side.HAN, PalaceTopology.from());

        assertThatThrownBy(() -> sa.findRoute(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사는 궁성 밖으로 나갈 수 없습니다.");
    }
}