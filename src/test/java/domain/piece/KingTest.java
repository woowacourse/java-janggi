package domain.piece;

import domain.Position;
import domain.Side;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KingTest {

    private static final Side IRRELEVANT_SIDE = Side.HAN;

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            "5, 2, 5, 3",
            "5, 2, 5, 1",
            "5, 2, 4, 2",
            "5, 2, 6, 2"
    })
    @DisplayName("궁이 이동 가능한 위치로 이동하면 경로를 반환한다")
    void 궁이_이동_가능한_위치로_이동하면_경로를_반환한다(
            int sourceX, int sourceY, int targetX, int targetY) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = PieceType.KING.create(IRRELEVANT_SIDE);

        Assertions.assertThatCode(() -> piece.findRoute(source, target))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            "5, 2, 5, 4"
    })
    @DisplayName("궁이 이동 불가능한 위치로 이동하면 예외가 발생한다")
    void 궁이_이동_불가능한_위치로_이동하면_예외가_발생한다(
            int sourceX, int sourceY, int targetX, int targetY) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = PieceType.KING.create(IRRELEVANT_SIDE);

        Assertions.assertThatThrownBy(() -> piece.findRoute(source, target))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
