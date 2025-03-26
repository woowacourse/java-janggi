package piece.player;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piece.PieceScore;
import piece.PieceType;

class PieceScoreTest {

    static Stream<Arguments> pieceTypes() {
        return Stream.of(
                Arguments.arguments(PieceType.CHA, 13),
                Arguments.arguments(PieceType.FO, 7),
                Arguments.arguments(PieceType.MA, 5),
                Arguments.arguments(PieceType.SANG, 3),
                Arguments.arguments(PieceType.SA, 3),
                Arguments.arguments(PieceType.JOL, 2),
                Arguments.arguments(PieceType.GUNG, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("pieceTypes")
    void 기물의점수를_타입으로_가져올_수_있다(PieceType pieceType, int point) {
        Assertions.assertThat(PieceScore.from(pieceType).getPoint()).isEqualTo(point);
    }
}
