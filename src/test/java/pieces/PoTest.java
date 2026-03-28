package pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import movepolicy.MoveContext;
import movepolicy.destination.PoDestinationRule;
import movepolicy.path.PoPathRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import position.Position;

class PoTest {

    private static final Position DEFAULT = new Position(3, 3);

    @Nested
    @DisplayName("포의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @Test
        void 앞으로_여러_칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side).moveForward(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 뒤로_여러_칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side).moveBack(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 좌로_여러_칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft(side).moveLeft(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 우로_여러_칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side).moveRight(side);
            // when & then
            assertThatCode(() -> piece.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 대각이_도착지인_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRightForward(side);
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("포의 이동 경로를 검증한다")
    class PathPosition {

        @Test
        void 출발지와_도착지_차이_만큼의_이동_경로가_존재한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side).moveBack(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            int gapSize = Math.abs(departure.row().index() - destination.row().index()) - 1;
            assertThat(moveContext.pathPositions()).hasSize(gapSize);
        }

        @Test
        void 앞_1칸_차이인_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side);
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 뒤_1칸_차이인_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side);
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 좌_1칸_차이인_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft(side);
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 우_1칸_차이인_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side);
            // when & then
            assertThatThrownBy(() -> piece.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 앞으로_여러_칸_차이인_경우의_이동_경로를_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveForward(side).moveForward(side).moveForward(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPositions = moveContext.pathPositions();
            assertThat(pathPositions.get(0)).isEqualTo(departure.moveForward(side));
            assertThat(pathPositions.get(1)).isEqualTo(
                departure.moveForward(side).moveForward(side)
            );
        }

        @Test
        void 뒤로_여러_칸_차이인_경우의_이동_경로를_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveBack(side).moveBack(side).moveBack(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPositions = moveContext.pathPositions();
            assertThat(pathPositions.get(0)).isEqualTo(departure.moveBack(side));
            assertThat(pathPositions.get(1)).isEqualTo(
                departure.moveBack(side).moveBack(side)
            );
        }

        @Test
        void 좌로_여러_칸_차이인_경우의_이동_경로를_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft(side).moveLeft(side).moveLeft(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPositions = moveContext.pathPositions();
            assertThat(pathPositions.get(0)).isEqualTo(departure.moveLeft(side));
            assertThat(pathPositions.get(1)).isEqualTo(
                departure.moveLeft(side).moveLeft(side)
            );
        }

        @Test
        void 우로_여러_칸_차이인_경우의_이동_경로를_반환한다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Po(side);
            Position departure = DEFAULT;
            Position destination = departure.moveRight(side).moveRight(side).moveRight(side);
            // when
            MoveContext moveContext = piece.askMoveContext(departure, destination);
            // then
            List<Position> pathPositions = moveContext.pathPositions();
            assertThat(pathPositions.get(0)).isEqualTo(departure.moveRight(side));
            assertThat(pathPositions.get(1)).isEqualTo(
                departure.moveRight(side).moveRight(side)
            );
        }
    }

    @Test
    void 포는_포를_제외한_다른_진영의_기물만_공격할_수_있는_규칙을_반환한다() {
        // given
        Side side = Side.CHO;
        Piece piece = new Po(side);
        Position departure = DEFAULT;
        Position destination = departure.moveForward(side).moveForward(side);
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.destinationRule())
            .isInstanceOf(PoDestinationRule.class);
    }

    @Test
    void 포는_이동_경로에_포를_제외한_기물이_1개_있을_때_이동할_수_있는_규칙을_반환한다() {
        // given
        Side side = Side.CHO;
        Piece piece = new Po(side);
        Position departure = DEFAULT;
        Position destination = departure.moveForward(side).moveForward(side);
        // when
        MoveContext moveContext = piece.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathRule())
            .isInstanceOf(PoPathRule.class);
    }
}