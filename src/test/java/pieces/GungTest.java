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

class GungTest {

    private static final Position DEFAULT = new Position(1, 1);

    @Nested
    @DisplayName("궁의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @Test
        void 앞_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Gung(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 뒤_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Gung(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Gung(side);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Gung(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 앞_2칸이_도착지인_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Gung(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side).moveForward(side);
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 대각선이_도착지인_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Gung(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRightForward(side);
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 출발지와_도착지_사이에는_이동경로가_존재하지_않는다() {
        // given
        Side side = Side.CHO;
        Piece piece = new Gung(side);
        Position departure = DEFAULT;
        Position destination = departure.moveForward(side);
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathPositions()).hasSize(0);
    }

    @Test
    void 궁은_다른_진영의_기물만_공격할_수_있는_규칙을_반환한다() {
        // given
        Side side = Side.CHO;
        Piece piece = new Gung(side);
        Position departure = DEFAULT;
        Position destination = departure.moveForward(side);
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.destinationRule())
            .isInstanceOf(BasicDestinationRule.class);
    }

    @Test
    void 궁은_이동_경로에_기물이_없을_때_이동할_수_있는_규칙을_반환한다() {
        // given
        Side side = Side.CHO;
        Piece piece = new Gung(side);
        Position departure = DEFAULT;
        Position destination = departure.moveForward(side);
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathRule())
            .isInstanceOf(EmptyPathRule.class);
    }
}
