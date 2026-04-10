package janggi.domain.piece;

import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.domain.Side;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SangTest {
    @ParameterizedTest
    @CsvSource({
            "2,3,5,1",
            "2,3,5,5",
            "2,3,4,6",
            "2,3,0,6",
            "4,3,1,1",
            "4,3,1,1",
            "4,3,6,0",
            "4,3,2,0"
    })
    void 상은_상하좌우와_대각선_두_칸_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Sang sang = new Sang(Side.CHO, PalaceTopology.from());

        List<Position> actual = sang.findRoute(startPosition, endPosition);

        assertThat(actual.getLast()).isEqualTo(endPosition);
    }

    @ParameterizedTest
    @CsvSource({
            "2,3,2,5",
            "2,3,2,1",
            "2,3,1,6",
            "2,3,3,2"
    })
    void 상은_상하좌우와_대각선_두_칸이_아닌_좌표로는_이동할_수_없다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Sang sang = new Sang(Side.CHO, PalaceTopology.from());

        assertThatThrownBy(() -> sang.findRoute(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }
}