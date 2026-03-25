import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PieceTest {

    @ParameterizedTest
    @MethodSource("provideSideCase")
    void 같은_진영_여부를_판단한다(Side side, Side ohter, boolean expected) {
        Piece piece = new SubPiece(side);

        boolean actual = piece.isSameSideAs(ohter);

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

    static class SubPiece extends Piece {

        public SubPiece(Side side) {
            super(side);
        }
    }
}
