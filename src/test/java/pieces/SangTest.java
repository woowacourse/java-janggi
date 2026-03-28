package pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import movepolicy.MoveContext;
import movepolicy.destination.BasicDestinationRule;
import movepolicy.path.EmptyPathRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import position.Position;

class SangTest {

    private static final Position DEFAULT = new Position(3, 3);

    @Nested
    @DisplayName("상의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @Test
        void 전방_1칸_우전방_2칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side)
                .moveRightForward(side)
                .moveRightForward(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 전방_1칸_좌전방_2칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side)
                .moveLeftForward(side)
                .moveLeftForward(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 후방_1칸_우후방_2칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side)
                .moveRightBack(side)
                .moveRightBack(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 후방_1칸_좌후방_2칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side)
                .moveLeftBack(side)
                .moveLeftBack(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_좌전방_2칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft(side)
                .moveLeftForward(side)
                .moveLeftForward(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_좌후방_2칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft(side)
                .moveLeftBack(side)
                .moveLeftBack(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_우전방_2칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side)
                .moveRightForward(side)
                .moveRightForward(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_우후방_2칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side)
                .moveRightBack(side)
                .moveRightBack(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 전방_2칸이_도착지인_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side).moveForward(side);
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 우전방이_도착지인_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRightForward(side);
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("상의 이동 경로를 검증한다")
    class PathPosition {

        @Test
        void 출발지와_도착지_사이에는_2칸의_이동_경로만_존재한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side)
                .moveRightForward(side)
                .moveRightForward(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.pathPositions()).hasSize(2);
        }

        @Test
        void 전방_1칸_우전방_2칸_이동의_경로를_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side)
                .moveRightForward(side)
                .moveRightForward(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveForward(side));
            assertThat(pathPosition.get(1)).isEqualTo(
                departure.moveForward(side).moveRightForward(side)
            );
        }

        @Test
        void 전방_1칸_좌전방_2칸_이동의_경로를_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side)
                .moveLeftForward(side)
                .moveLeftForward(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveForward(side));
            assertThat(pathPosition.get(1)).isEqualTo(
                departure.moveForward(side).moveLeftForward(side)
            );
        }

        @Test
        void 후방_1칸_우후방_2칸_이동의_경로를_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side)
                .moveRightBack(side)
                .moveRightBack(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveBack(side));
            assertThat(pathPosition.get(1)).isEqualTo(
                departure.moveBack(side).moveRightBack(side)
            );
        }

        @Test
        void 후방_1칸_좌후방_2칸_이동의_경로를_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side)
                .moveLeftBack(side)
                .moveLeftBack(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveBack(side));
            assertThat(pathPosition.get(1)).isEqualTo(
                departure.moveBack(side).moveLeftBack(side)
            );
        }

        @Test
        void 좌_1칸_좌전방_2칸_이동의_경로를_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft(side)
                .moveLeftForward(side)
                .moveLeftForward(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveLeft(side));
            assertThat(pathPosition.get(1)).isEqualTo(
                departure.moveLeft(side).moveLeftForward(side)
            );
        }

        @Test
        void 좌_1칸_좌후방_2칸_이동의_경로를_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft(side)
                .moveLeftBack(side)
                .moveLeftBack(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveLeft(side));
            assertThat(pathPosition.get(1)).isEqualTo(
                departure.moveLeft(side).moveLeftBack(side)
            );
        }

        @Test
        void 우_1칸_우전방_2칸_이동의_경로를_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side)
                .moveRightForward(side)
                .moveRightForward(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveRight(side));
            assertThat(pathPosition.get(1)).isEqualTo(
                departure.moveRight(side).moveRightForward(side)
            );
        }

        @Test
        void 우_1칸_우후방_2칸_이동의_경로를_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side)
                .moveRightBack(side)
                .moveRightBack(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveRight(side));
            assertThat(pathPosition.get(1)).isEqualTo(
                departure.moveRight(side).moveRightBack(side)
            );
        }

        @Test
        void 도착지가_유효하지_않은_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Sang(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side).moveRight(side);
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 상은_다른_진영의_기물만_공격할_수_있는_규칙을_반환한다() {
        // given
        Side side = Side.CHO;
        Piece piece = new Sang(side);
        Position departure = DEFAULT;
        Position destination = departure.moveForward(side)
            .moveRightForward(side)
            .moveRightForward(side);
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.destinationRule())
            .isInstanceOf(BasicDestinationRule.class);
    }

    @Test
    void 상은_이동_경로에_기물이_없을_때_이동할_수_있는_규칙을_반환한다() {
        // given
        Side side = Side.CHO;
        Piece piece = new Sang(side);
        Position departure = DEFAULT;
        Position destination = departure.moveForward(side)
            .moveRightForward(side)
            .moveRightForward(side);
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathRule())
            .isInstanceOf(EmptyPathRule.class);
    }
}