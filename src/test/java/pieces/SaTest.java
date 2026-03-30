package pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import movepolicy.rule.BasicMoveRule;
import movepolicy.rule.MoveRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import position.Position;

class SaTest {

    private static final Position DEFAULT = new Position(3, 3);

    @Nested
    @DisplayName("사의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_1칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Sa(side);
            Position departure = DEFAULT;
            Position destination = departure.move(side.forwardDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 후방_1칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Sa(side);
            Position departure = DEFAULT;
            Position destination = departure.move(side.backDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 좌_1칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Sa(side);
            Position departure = DEFAULT;
            Position destination = departure.move(side.leftDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우_1칸_이동할_수_있다(Side side) {
            // given
            FullPiece piece = new Sa(side);
            Position departure = DEFAULT;
            Position destination = departure.move(side.rightDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 전방_2칸이_도착지인_경우_예외를_던진다(Side side) {
            // given
            FullPiece piece = new Sa(side);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.forwardDelta());
            // when & then
            assertThatThrownBy(() -> piece.validateDestination(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 우전방이_도착지인_경우_예외를_던진다(Side side) {
            // given
            FullPiece piece = new Sa(side);
            Position departure = DEFAULT;
            Position destination = departure.move(side.rightForwardDelta());
            // when & then
            assertThatThrownBy(() -> piece.validateDestination(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 출발지와_도착지_사이에는_이동_경로가_존재하지_않는다(Side side) {
        // given
        FullPiece piece = new Sa(side);
        Position departure = DEFAULT;
        Position destination = departure.move(side.forwardDelta());
        // when
        List<Position> positions = piece.getInterveningPositions(departure, destination);
        // then
        assertThat(positions).hasSize(0);
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 사는_다른_진영의_기물만_공격할_수_있는_규칙을_반환한다(Side side) {
        // given
        FullPiece piece = new Sa(side);
        // when
        MoveRule moveRule = piece.getMoveRule();
        // then
        assertThat(moveRule).isInstanceOf(BasicMoveRule.class);
    }
}