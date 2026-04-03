package janggi.domain.piece;

import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.domain.Side;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class MaTest {
    @ParameterizedTest
    @CsvSource({
            "2,3,4,2",
            "2,3,4,4",
            "2,3,3,5",
            "2,3,1,5",
            "2,3,0,4",
            "2,3,0,2",
            "2,3,3,1",
            "2,3,1,1"
    })
    void 마는_상하좌우와_대각선_한_칸_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Ma ma = new Ma(Side.CHO, PalaceTopology.from());

        List<Position> actual = ma.findRoute(startPosition, endPosition);

        assertThat(actual.getLast()).isEqualTo(endPosition);
    }

    @ParameterizedTest
    @CsvSource({
            "2,3,2,5",
            "2,3,2,1",
            "2,3,1,6",
            "2,3,3,2"
    })
    void 마는_상하좌우와_대각선_한_칸이_아닌_좌표로는_이동할_수_없다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Ma ma = new Ma(Side.CHO, PalaceTopology.from());

        assertThatThrownBy(() -> ma.findRoute(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }
}
