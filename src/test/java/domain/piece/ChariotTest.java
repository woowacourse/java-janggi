package domain.piece;

import domain.Position;
import domain.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ChariotTest {

    private static final Side IRRELEVANT_SIDE = Side.CHO;

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            "2, 3, 2, 7",
            "8, 3, 2, 3"
    })
    @DisplayName("차가 이동 가능한 위치로 이동하면 경로를 반환한다")
    void 차가_이동_가능한_위치로_이동하면_경로를_반환한다(
            int sourceX, int sourceY, int targetX, int targetY) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = PieceType.CHARIOT.create(IRRELEVANT_SIDE);

        assertThat(piece.findRoute(source)).contains(target);
    }

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            "2, 3, 4, 6",
            "8, 3, 5, 9"
    })
    @DisplayName("차가 이동 불가능한 위치로 이동하면 예외가 발생한다")
    void 차가_이동_불가능한_위치로_이동하면_예외가_발생한다(
            int sourceX, int sourceY, int targetX, int targetY) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = PieceType.CHARIOT.create(IRRELEVANT_SIDE);

        assertThat(piece.findRoute(source)).doesNotContain(target);
    }
}
