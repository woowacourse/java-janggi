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
import participant.HanTurn;
import participant.Turn;
import position.Position;

class MaTest {

    private static final Position DEFAULT = new Position(3, 3);

    @Nested
    @DisplayName("마의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @Test
        void 상_1칸_우상향_1칸_이동할_수_있다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveRightUp();
            // when & then
            assertThatCode(() -> ma.askMoveContext(departure, destination, turn))
                .doesNotThrowAnyException();
        }

        @Test
        void 상_1칸_좌상향_1칸_이동할_수_있다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveLeftUp();
            // when & then
            assertThatCode(() -> ma.askMoveContext(departure, destination, turn))
                .doesNotThrowAnyException();
        }

        @Test
        void 하_1칸_우하향_1칸_이동할_수_있다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown().moveRightDown();
            // when & then
            assertThatCode(() -> ma.askMoveContext(departure, destination, turn))
                .doesNotThrowAnyException();
        }

        @Test
        void 하_1칸_좌하향_1칸_이동할_수_있다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown().moveLeftDown();
            // when & then
            assertThatCode(() -> ma.askMoveContext(departure, destination, turn))
                .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_좌상향_1칸_이동할_수_있다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft().moveLeftUp();
            // when & then
            assertThatCode(() -> ma.askMoveContext(departure, destination, turn))
                .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_좌하향_1칸_이동할_수_있다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft().moveLeftDown();
            // when & then
            assertThatCode(() -> ma.askMoveContext(departure, destination, turn))
                .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_우상향_1칸_이동할_수_있다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight().moveRightUp();
            // when & then
            assertThatCode(() -> ma.askMoveContext(departure, destination, turn))
                .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_우하향_1칸_이동할_수_있다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight().moveRightDown();
            // when & then
            assertThatCode(() -> ma.askMoveContext(departure, destination, turn))
                .doesNotThrowAnyException();
        }

        @Test
        void 상_2칸이_도착지인_경우_예외를_던진다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveUp();
            // when & then
            assertThatThrownBy(() -> ma.askMoveContext(departure, destination, turn))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 우상향이_도착지인_경우_예외를_던진다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRightUp();
            // when & then
            assertThatThrownBy(() -> ma.askMoveContext(departure, destination, turn))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("마의 이동 경로를 검증한다")
    class PathPosition {
        @Test
        void 출발지와_도착지_사이에는_1칸의_이동_경로만_존재한다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveRightUp();
            // when
            MoveContext moveContext = ma.askMoveContext(departure, destination, turn);
            // then
            assertThat(moveContext.pathPositions()).hasSize(1);
        }

        @Test
        void 상_1칸_우상향_1칸이_도착지인_경우_상_1칸_포지션을_반환한다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveRightUp();
            // when
            MoveContext moveContext = ma.askMoveContext(departure, destination, turn);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveUp());
        }

        @Test
        void 상_1칸_좌상향_1칸이_도착지인_경우_상_1칸_포지션을_반환한다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveLeftUp();
            // when
            MoveContext moveContext = ma.askMoveContext(departure, destination, turn);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveUp());
        }

        @Test
        void 하_1칸_우하향_1칸이_도착지인_경우_하_1칸_포지션을_반환한다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown().moveRightDown();
            // when
            MoveContext moveContext = ma.askMoveContext(departure, destination, turn);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveDown());
        }

        @Test
        void 하_1칸_좌하향_1칸이_도착지인_경우_하_1칸_포지션을_반환한다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown().moveLeftDown();
            // when
            MoveContext moveContext = ma.askMoveContext(departure, destination, turn);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveDown());
        }

        @Test
        void 좌_1칸_좌상향_1칸이_도착지인_경우_화_1칸_포지션을_반환한다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft().moveLeftUp();
            // when
            MoveContext moveContext = ma.askMoveContext(departure, destination, turn);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveLeft());
        }

        @Test
        void 좌_1칸_좌하향_1칸이_도착지인_경우_좌_1칸_포지션을_반환한다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft().moveLeftDown();
            // when
            MoveContext moveContext = ma.askMoveContext(departure, destination, turn);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveLeft());
        }

        @Test
        void 우_1칸_우상향_1칸이_도착지인_경우_우_1칸_포지션을_반환한다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight().moveRightUp();
            // when
            MoveContext moveContext = ma.askMoveContext(departure, destination, turn);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveRight());
        }

        @Test
        void 우_1칸_우하향_1칸이_도착지인_경우_우_1칸_포지션을_반환한다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight().moveRightDown();
            // when
            MoveContext moveContext = ma.askMoveContext(departure, destination, turn);
            // then
            Position pathPosition = moveContext.pathPositions().getFirst();
            assertThat(pathPosition).isEqualTo(departure.moveRight());
        }

        @Test
        void 도착지가_유효하지_않은_경우_예외를_던진다() {
            // given
            Turn turn = new HanTurn();
            Piece ma = new Ma(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight().moveRight();
            // when & then
            assertThatThrownBy(() -> ma.askMoveContext(departure, destination, turn))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 마는_다른_진영의_기물만_공격할_수_있다() {
        // given
        Turn turn = new HanTurn();
        Piece ma = new Ma(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveUp().moveRightUp();
        // when
        MoveContext moveContext = ma.askMoveContext(departure, destination, turn);
        // then
        assertThat(moveContext.destinationRule())
            .isInstanceOf(BasicDestinationRule.class);
    }

    @Test
    void 마는_이동_경로에_기물이_없을_때_이동할_수_있다() {
        // given
        Turn turn = new HanTurn();
        Piece ma = new Ma(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveUp().moveRightUp();
        // when
        MoveContext moveContext = ma.askMoveContext(departure, destination, turn);
        // then
        assertThat(moveContext.pathRule())
            .isInstanceOf(EmptyPathRule.class);
    }
}
