package domain.piece;

import domain.Position;
import domain.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class KingTest {

    private static final Side IRRELEVANT_SIDE = Side.HAN;

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            // 상하좌우
            "5, 2, 5, 3",
            "5, 2, 5, 1",
            "5, 2, 4, 2",
            "5, 2, 6, 2",
            // 대각선 (궁성 중앙에서)
            "5, 2, 4, 1",
            "5, 2, 6, 1",
            "5, 2, 4, 3",
            "5, 2, 6, 3"
    })
    @DisplayName("궁이 이동 가능한 위치로 이동하면 경로를 반환한다")
    void 궁이_이동_가능한_위치로_이동하면_경로를_반환한다(
            int sourceX, int sourceY, int targetX, int targetY) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = PieceType.KING.create(IRRELEVANT_SIDE);

        assertThat(piece.findRoute(source)).contains(target);
    }

    @ParameterizedTest(name = "[{index}] ({0},{1}) -> ({2},{3})")
    @CsvSource({
            // 두 칸 이상
            "5, 2, 5, 4",
            // 비대각선 위치에서 대각선 불가
            "5, 1, 4, 2",
            "5, 1, 6, 2"
    })
    @DisplayName("궁이 이동 불가능한 위치로 이동하면 목적지에 포함되지 않는다")
    void 궁이_이동_불가능한_위치로_이동하면_목적지에_포함되지_않는다(
            int sourceX, int sourceY, int targetX, int targetY) {
        Position source = Position.of(sourceX, sourceY);
        Position target = Position.of(targetX, targetY);
        Piece piece = PieceType.KING.create(IRRELEVANT_SIDE);

        assertThat(piece.findRoute(source)).doesNotContain(target);
    }
}
