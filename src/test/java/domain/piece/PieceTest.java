package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import domain.game.Side;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PieceTest {

    @ParameterizedTest
    @MethodSource("provideSideCase")
    void 주어진_진영과_자신의_진영이_같은지_올바르게_판별한다(Side side, Side other, boolean expected) {
        Piece piece = PieceFactory.createSoldier(side);

        boolean actual = piece.isAlly(other);

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> provideSideCase() {
        return Stream.of(
                arguments(Side.CHO, Side.CHO, true),
                arguments(Side.HAN, Side.HAN, true),
                arguments(Side.CHO, Side.HAN, false),
                arguments(Side.HAN, Side.CHO, false)
        );
    }
}
