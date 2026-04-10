package janggi.domain.piece;

import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.domain.Side;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PawnTest {
    @ParameterizedTest
    @CsvSource({
            "2,3,3,3",
            "2,3,2,2",
            "2,3,2,4"
    })
    void 폰은_한_진영일때_하좌우로_한_칸_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Pawn pawn = new Pawn(Side.HAN, PalaceTopology.from());

        List<Position> actual = pawn.findRoute(startPosition, endPosition);

        assertThat(actual.getLast()).isEqualTo(endPosition);
    }

    @ParameterizedTest
    @CsvSource({
            "2,3,1,3",
            "2,3,2,2",
            "2,3,2,4"
    })
    void 폰은_초_진영일때_상좌우로_한_칸_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Pawn pawn = new Pawn(Side.CHO, PalaceTopology.from());

        List<Position> actual = pawn.findRoute(startPosition, endPosition);

        assertThat(actual.getLast()).isEqualTo(endPosition);
    }

    @ParameterizedTest
    @CsvSource({
            "5,3,4,3",
            "4,3,3,3",
            "9,5,8,4",
            "10,4,9,5"
    })
    void 폰은_한_진영일_때_상으로_이동할_수_없다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Pawn pawn = new Pawn(Side.HAN, PalaceTopology.from());

        assertThatThrownBy(() -> pawn.findRoute(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("병은 뒤로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "8,3,9,3",
            "7,3,8,3",
            "2,5,3,4",
            "1,4,2,5"
    })
    void 폰은_초_진영일_때_하로_이동할_수_없다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Pawn pawn = new Pawn(Side.CHO, PalaceTopology.from());

        assertThatThrownBy(() -> pawn.findRoute(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("병은 뒤로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "3,3,3,1",
            "3,3,4,5",
            "3,3,3,7"
    })
    void 폰은_한_진영일_때_허용된_하좌우_한_칸_이동_외에는_이동할_수_없다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Pawn pawn = new Pawn(Side.HAN, PalaceTopology.from());

        assertThatThrownBy(() -> pawn.findRoute(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "7,3,3,1",
            "7,3,4,5",
            "7,3,3,7"
    })
    void 폰은_초_진영일_때_허용된_상좌우_한_칸_이동_외에는_이동할_수_없다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Pawn pawn = new Pawn(Side.CHO, PalaceTopology.from());

        assertThatThrownBy(() -> pawn.findRoute(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "8,4,9,5",
            "8,6,9,5",
            "9,5,10,4",
            "9,5,10,6"
    })
    void 폰은_한_진영일_때_궁성_영역_에서_연결된_앞쪽_대각선으로_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Pawn pawn = new Pawn(Side.HAN, PalaceTopology.from());

        List<Position> actual = pawn.findRoute(startPosition, endPosition);

        assertThat(actual.getLast()).isEqualTo(endPosition);
    }

    @ParameterizedTest
    @CsvSource({
            "3,4,2,5",
            "3,6,2,5",
            "2,5,1,4",
            "2,5,1,6"
    })
    void 폰은_초_진영일_때_궁성_영역_에서_연결된_앞쪽_대각선으로_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Pawn pawn = new Pawn(Side.CHO, PalaceTopology.from());

        List<Position> actual = pawn.findRoute(startPosition, endPosition);

        assertThat(actual.getLast()).isEqualTo(endPosition);
    }
}