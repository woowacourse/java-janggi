package domain.move.rule;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import domain.move.Path;
import domain.piece.AlivePieces;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("궁성 이동 제약 테스트")
class PalaceRuleTest {

    private PalaceRule palaceRule;

    @BeforeEach
    void setUp() {
        palaceRule = new PalaceRule();
    }

    @DisplayName("궁성 밖으로는 이동할 수 없다")
    @Nested
    class 궁성_밖_이동_불가 {

        @DisplayName("초(CHO) 진영")
        @Test
        void 초_진영() {
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
            List<Path> candidatePaths = List.of(
                    new Path(position(8, 4), List.of()),
                    new Path(position(9, 5), List.of()),
                    new Path(position(10, 4), List.of()),
                    new Path(position(9, 3), List.of())
            );

            List<Intersection> movableDestinations = palaceRule.movableDestinations(
                    Side.CHO,
                    candidatePaths,
                    emptyAlivePieces
            );

            assertThat(movableDestinations).containsExactlyInAnyOrder(
                    position(8, 4),
                    position(9, 5),
                    position(10, 4)
            );
        }

        @DisplayName("한(HAN) 진영")
        @Test
        void 한_진영() {
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
            List<Path> candidatePaths = List.of(
                    new Path(position(1, 4), List.of()),
                    new Path(position(2, 5), List.of()),
                    new Path(position(3, 4), List.of()),
                    new Path(position(2, 3), List.of())
            );

            List<Intersection> movableDestinations = palaceRule.movableDestinations(
                    Side.HAN,
                    candidatePaths,
                    emptyAlivePieces
            );

            assertThat(movableDestinations).containsExactlyInAnyOrder(
                    position(1, 4),
                    position(2, 5),
                    position(3, 4)
            );
        }
    }

    private static Intersection position(int row, int file) {
        return new Intersection(row, file);
    }
}
