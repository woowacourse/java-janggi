package janggi.domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class GungTest {
    @ParameterizedTest
    @CsvSource({
            "2,5,1,5",
            "2,5,2,4",
            "2,5,2,6",
            "2,5,3,5"
    })
    void 궁은_궁성_안에서_상하좌우로_한_칸_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Gung gung = new Gung(Side.CHO);

        Route actual = gung.findRoute(startPosition, endPosition);

        assertThat(actual.isDestinationSatisfied(position -> position.equals(endPosition))).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "2,5,1,4",
            "2,5,1,6",
            "2,5,3,4",
            "2,5,3,6"
    })
    void 궁은_궁성_안에서_십자_위치를_제외한_곳에서_대각선으로_한_칸_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Gung gung = new Gung(Side.CHO);

        Route actual = gung.findRoute(startPosition, endPosition);

        assertThat(actual.isDestinationSatisfied(position -> position.equals(endPosition))).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "1, 5, 2, 4",
            "1, 5, 2, 6",
            "2, 4, 1, 5",
            "2, 6, 3, 5",
            "9, 4, 8, 5",
            "9, 6, 10, 5"
    })
    void 궁은_궁성_내_십자_위치에서의_대각선_이동이_있을_때_에러가_발생한다(int startX, int startY, int endX, int endY) {
        Gung gung = new Gung(Side.CHO);
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        Assertions.assertThatThrownBy(() -> gung.findRoute(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "8,4,8,3",
            "8,6,8,7",
            "8,5,7,5"
    })
    void 궁은_궁성_밖으로_이동이_있을_때_에러가_발생한다(int startX, int startY, int endX, int endY) {
        Gung gung = new Gung(Side.CHO);
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        Assertions.assertThatThrownBy(() -> gung.findRoute(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }
}
