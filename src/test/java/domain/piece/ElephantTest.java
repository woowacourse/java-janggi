package domain.piece;

import domain.Position;
import domain.Side;
import domain.strategy.PathMovement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {

    private static final Side IRRELEVANT_SIDE = Side.HAN;

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            "3, 1, 5, 4",
            "3, 1, 1, 4"
    })
    @DisplayName("상이 이동 가능한 위치로 이동하면 경로를 반환한다")
    void 상이_이동_가능한_위치로_이동하면_경로를_반환한다(
            int sourceX, int sourceY, int targetX, int targetY
    ) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = new Elephant(IRRELEVANT_SIDE, new PathMovement());

        assertThat(piece.findRoute(source)).contains(target);
    }

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            "3, 1, 3, 2",
            "3, 1, 4, 1"
    })
    @DisplayName("상이 이동 불가능한 위치로 이동하면 예외가 발생한다")
    void 상이_이동_불가능한_위치로_이동하면_예외가_발생한다(
            int sourceX, int sourceY, int targetX, int targetY
    ) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = new Elephant(IRRELEVANT_SIDE, new PathMovement());

        assertThat(piece.findRoute(source)).doesNotContain(target);
    }
}
