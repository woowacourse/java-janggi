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

class MaTest {

    private static final Position DEFAULT = new Position(3, 3);

    @Nested
    @DisplayName("마의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @Test
        void 전방_1칸_우전방_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side).moveRightForward(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 전방_1칸_좌전방_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side).moveLeftForward(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 후방_1칸_좌후방_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side).moveLeftBack(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 후방_1칸_우후방_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side).moveRightBack(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_좌전방_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft(side).moveLeftForward(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_좌후방_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft(side).moveLeftBack(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_우전방_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side).moveRightForward(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_우후방_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side).moveRightBack(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 전방_2칸이_도착지인_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side).moveForward(side);
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("마의 이동 경로를 검증한다")
    class PathPosition {

        @Test
        void 출발지와_도착지_사이에는_1칸의_이동_경로만_존재한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side).moveRightForward(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.pathPositions()).hasSize(1);
        }

        @Test
        void 전방_1칸_좌전방_1칸이_도착지인_경우_전방_1칸_포지션을_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side).moveLeftForward(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveForward(side));
        }

        @Test
        void 전방_1칸_우전방_1칸이_도착지인_경우_전방_1칸_포지션을_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side).moveRightForward(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveForward(side));
        }

        @Test
        void 후방_1칸_좌후방_1칸이_도착지인_경우_후방_1칸_포지션을_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side).moveLeftBack(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveBack(side));
        }

        @Test
        void 후방_1칸_우후방_1칸이_도착지인_경우_후방_1칸_포지션을_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side).moveRightBack(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveBack(side));
        }

        @Test
        void 좌_1칸_좌전방_1칸이_도착지인_경우_좌_1칸_포지션을_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft(side).moveLeftForward(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveLeft(side));
        }

        @Test
        void 좌_1칸_좌후방_1칸이_도착지인_경우_좌_1칸_포지션을_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft(side).moveLeftBack(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveLeft(side));
        }

        @Test
        void 우_1칸_우전방_1칸이_도착지인_경우_우_1칸_포지션을_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side).moveRightForward(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveRight(side));
        }

        @Test
        void 우_1칸_우후방_1칸이_도착지인_경우_우_1칸_포지션을_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side).moveRightBack(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveRight(side));
        }

        @Test
        void 도착지가_유효하지_않은_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Ma(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side).moveRight(side);
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 마는_다른_진영의_기물만_공격할_수_있는_규칙을_반환한다() {
        // given
        Side side = Side.CHO;
        Piece piece = new Ma(side);
        Position departure = DEFAULT;
        Position destination = departure.moveForward(side).moveRightForward(side);
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.destinationRule())
            .isInstanceOf(BasicDestinationRule.class);
    }

    @Test
    void 마는_이동_경로에_기물이_없을_때_이동할_수_있는_규칙을_반환한다() {
        // given
        Side side = Side.CHO;
        Piece piece = new Ma(side);
        Position departure = DEFAULT;
        Position destination = departure.moveForward(side).moveRightForward(side);
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathRule())
            .isInstanceOf(EmptyPathRule.class);
    }
}