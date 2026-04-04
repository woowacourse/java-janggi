package domain.piece;

import domain.Position;
import domain.Side;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HorseTest {

    private static final Side IRRELEVANT_SIDE = Side.HAN;

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            "3, 2, 1, 1",
            "3, 2, 5, 1"
    })
    @DisplayName("마가 이동 가능한 위치로 이동하면 경로를 반환한다")
    void 마가_이동_가능한_위치로_이동하면_경로를_반환한다(
            int sourceX, int sourceY, int targetX, int targetY) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = PieceType.HORSE.create(IRRELEVANT_SIDE);

        Assertions.assertThatCode(() -> piece.findRoute(source, target))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            "3, 2, 1, 5",
            "3, 2, 3, 5"
    })
    @DisplayName("마가 이동 불가능한 위치로 이동하면 예외가 발생한다")
    void 마가_이동_불가능한_위치로_이동하면_예외가_발생한다(
            int sourceX, int sourceY, int targetX, int targetY) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = PieceType.HORSE.create(IRRELEVANT_SIDE);

        Assertions.assertThatThrownBy(() -> piece.findRoute(source, target))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
