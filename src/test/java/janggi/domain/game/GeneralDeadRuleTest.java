package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GeneralDeadRuleTest {

    private static final Rule RULE = new GeneralDeadRule();

    public static Stream<Arguments> isEnd() {
        return Stream.of(
                Arguments.of(Set.of(new General(Side.CHO), new General(Side.HAN)), false),
                Arguments.of(Set.of(new General(Side.CHO)), true),
                Arguments.of(Set.of(new General(Side.HAN)), true)
        );
    }

    public static Stream<Arguments> getWinSide() {
        return Stream.of(
                Arguments.of(Set.of(new General(Side.CHO)), Side.HAN),
                Arguments.of(Set.of(new General(Side.HAN)), Side.CHO)
        );
    }

    @ParameterizedTest
    @DisplayName("왕이 죽으면 게임이 종료된다.")
    @MethodSource
    void isEnd(Collection<Piece> pieces, boolean expected) {
        assertThat(RULE.isEnd(pieces)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("왕이 죽지않은 Side를 리턴한다.")
    @MethodSource
    void getWinSide(Collection<Piece> pieces, Side expected) {
        assertThat(RULE.getWinSide(pieces)).isEqualTo(expected);
    }
}
