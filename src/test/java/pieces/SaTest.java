package pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import movepolicy.MoveContext;
import movepolicy.destination.BasicDestinationRule;
import movepolicy.path.EmptyPathRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import position.Position;

class SaTest {
    private static final Position DEFAULT = new Position(1, 1);

    @Nested
    @DisplayName("사의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {
        @Test
        void 위쪽으로_한_칸_이동할_수_있다() {
            // given
            Piece sa = new Sa(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp();
            // when & then
            assertThatCode(() -> sa.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 아래쪽으로_한_칸_이동할_수_있다() {
            // given
            Piece sa = new Sa(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown();
            // when & then
            assertThatCode(() -> sa.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 왼쪽으로_한_칸_이동할_수_있다() {
            // given
            Piece sa = new Sa(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft();
            // when & then
            assertThatCode(() -> sa.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 오른쪽으로_한_칸_이동할_수_있다() {
            // given
            Piece sa = new Sa(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight();
            // when & then
            assertThatCode(() -> sa.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 두_칸_위쪽이_도착지인_경우_예외를_던진다() {
            // given
            Piece sa = new Sa(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveUp();
            // when & then
            assertThatThrownBy(() -> sa.askMoveContext(departure, destination))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 대각선이_도착지인_경우_예외를_던진다() {
            // given
            Piece sa = new Sa(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveRight();
            // when & then
            assertThatThrownBy(() -> sa.askMoveContext(departure, destination))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 출발지와_도착지_사이에는_이동경로가_존재하지_않는다() {
        // given
        Piece sa = new Sa(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveUp();
        // when
        MoveContext moveContext = sa.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathPositions()).hasSize(0);
    }

    @Test
    void 사는_다른_진영의_기물만_공격할_수_있다() {
        // given
        Piece sa = new Sa(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveUp();
        // when
        MoveContext moveContext = sa.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.destinationRule())
                .isInstanceOf(BasicDestinationRule.class);
    }

    @Test
    void 사는_이동_경로에_기물이_없을_때_이동할_수_있다() {
        // given
        Piece sa = new Sa(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveUp();
        // when
        MoveContext moveContext = sa.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathRule())
                .isInstanceOf(EmptyPathRule.class);
    }
}
