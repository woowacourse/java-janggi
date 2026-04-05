package janggi.domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PawnTest {
    @ParameterizedTest
    @CsvSource({
            "2,3,3,3",
            "2,3,2,2",
            "2,3,2,4",
    })
    void 폰은_한_진영일때_하좌우로_한_칸_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Pawn pawn = new Pawn(Side.HAN);

        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Route actual = pawn.findRoute(startPosition, endPosition);

        assertThat(actual.isDestinationSatisfied(position -> position.equals(endPosition))).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "9,5,10,4",
            "9,5,10,6"
    })
    void 폰은_한_진영일때_궁성에서_아래_대각선으로_한_칸_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Pawn pawn = new Pawn(Side.HAN);

        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Route actual = pawn.findRoute(startPosition, endPosition);

        assertThat(actual.isDestinationSatisfied(position -> position.equals(endPosition))).isTrue();
    }



    @ParameterizedTest
    @CsvSource({
            "2,3,1,3",
            "2,3,2,2",
            "2,3,2,4"
    })
    void 폰은_초_진영일때_상좌우로_한_칸_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Pawn pawn = new Pawn(Side.CHO);

        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Route actual = pawn.findRoute(startPosition, endPosition);

        assertThat(actual.isDestinationSatisfied(position -> position.equals(endPosition))).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "2,5,1,4",
            "2,5,1,6",
    })
    void 폰은_초_진영일때_궁성에서_위_대각선으로_한_칸_이동할_수_있다(int startX, int startY, int endX, int endY) {
        Pawn pawn = new Pawn(Side.CHO);

        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        Route actual = pawn.findRoute(startPosition, endPosition);

        assertThat(actual.isDestinationSatisfied(position -> position.equals(endPosition))).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "2,3,1,3",
            "2,3,1,6",
            "2,3,3,1"
    })
    void 폰은_한_진영일_때_하좌우가_아닌_좌표로는_이동할_수_없다(int startX, int startY, int endX, int endY) {
        Pawn pawn = new Pawn(Side.HAN);

        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        assertThatThrownBy(() -> pawn.findRoute(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "9,5,8,4",
            "9,5,8,6",
    })
    void 폰은_한_진영일_때_궁성에서_위_대각선으로_이동할_수_없다(int startX, int startY, int endX, int endY) {
        Pawn pawn = new Pawn(Side.HAN);

        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        assertThatThrownBy(() -> pawn.findRoute(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "2,5,3,4",
            "2,5,3,6",
    })
    void 폰은_초_진영일_때_궁성에서_아래_대각선으로_이동할_수_없다(int startX, int startY, int endX, int endY) {
        Pawn pawn = new Pawn(Side.CHO);

        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);

        assertThatThrownBy(() -> pawn.findRoute(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "1,4,1,6",
            "1,4,3,4",
            "1,4,3,6"
    })
    void 폰은_궁성_안이라도_두_칸_이상_이동_이동이_있을_때_에러가_발생한다(int startX, int startY, int endX, int endY) {
        Pawn pawn = new Pawn(Side.CHO);

        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        Assertions.assertThatThrownBy(() -> pawn.findRoute(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }
}
