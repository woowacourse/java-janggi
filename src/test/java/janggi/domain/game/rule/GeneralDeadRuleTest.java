package janggi.domain.game.rule;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Piece;
import janggi.domain.piece.fixed.General;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GeneralDeadRuleTest {
    private static final Rule RULE = new GeneralDeadRule();

    public static Stream<Arguments> isEnd() {
        return Stream.of(
                Arguments.of(Map.of(
                        new Point(1, 4), new General(Side.CHO),
                        new Point(8, 4), new General(Side.HAN)), false),
                Arguments.of(Map.of(new Point(1, 4), new General(Side.CHO)), true),
                Arguments.of(Map.of(new Point(8, 4), new General(Side.HAN)), true)
        );
    }

    public static Stream<Arguments> getWinSide() {
        return Stream.of(
                Arguments.of(Map.of(new Point(1, 4), new General(Side.CHO)), Side.CHO),
                Arguments.of(Map.of(new Point(8, 4), new General(Side.HAN)), Side.HAN)
        );
    }

    @ParameterizedTest
    @DisplayName("왕이 죽으면 게임이 종료된다.")
    @MethodSource
    void isEnd(Map<Point, Piece> pieces, boolean expected) {
        assertThat(RULE.isEnd(pieces)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("왕이 죽지않은 Side를 리턴한다.")
    @MethodSource
    void getWinSide(Map<Point, Piece> pieces, Side expected) {
        assertThat(RULE.getWinSide(pieces)).isEqualTo(expected);
    }
}
