package pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.pieces.Cha;
import domain.pieces.Piece;
import domain.pieces.PieceType;
import domain.pieces.Side;
import java.util.List;
import domain.movepolicy.MoveContext;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import domain.position.Position;

class ChaTest {

    private static final Position DEFAULT = new Position(3, 3);

    @Nested
    @DisplayName("차의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @Test
        void 상으로_여러_칸_이동할_수_있다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveUp();
            // when & then
            assertThatCode(() -> cha.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 하로_여러_칸_이동할_수_있다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown().moveDown();
            // when & then
            assertThatCode(() -> cha.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 좌로_여러_칸_이동할_수_있다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft().moveLeft();
            // when & then
            assertThatCode(() -> cha.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 우로_여러_칸_이동할_수_있다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight().moveRight();
            // when & then
            assertThatCode(() -> cha.askMoveContext(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 우상향이_도착지인_경우_예외를_던진다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRightUp();
            // when & then
            assertThatThrownBy(() -> cha.askMoveContext(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("차의 이동 경로를 검증한다")
    class PathPosition {

        @Test
        void 출발지와_도착지_차이_만큼의_이동_경로가_존재한다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown().moveDown();
            // when
            MoveContext moveContext = cha.askMoveContext(departure, destination);
            // then
            int gapSize = departure.row() - destination.row() - 1;
            assertThat(moveContext.pathPositions()).hasSize(gapSize);
        }

        @Test
        void 상_1칸_차이인_경우_이동_경로는_존재하지_않는다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp();
            // when
            MoveContext moveContext = cha.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.pathPositions()).hasSize(0);
        }

        @Test
        void 하_1칸_차이인_경우_이동_경로는_존재하지_않는다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown();
            // when
            MoveContext moveContext = cha.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.pathPositions()).hasSize(0);
        }

        @Test
        void 좌_1칸_차이인_경우_이동_경로는_존재하지_않는다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft();
            // when
            MoveContext moveContext = cha.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.pathPositions()).hasSize(0);
        }

        @Test
        void 우_1칸_차이인_경우_이동_경로는_존재하지_않는다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight();
            // when
            MoveContext moveContext = cha.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.pathPositions()).hasSize(0);
        }

        @Test
        void 상으로_여러_칸_차이인_경우의_이동_경로를_반환한다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveUp().moveUp();
            // when
            MoveContext moveContext = cha.askMoveContext(departure, destination);
            // then
            List<Position> pathPositions = moveContext.pathPositions();
            assertThat(pathPositions.get(0)).isEqualTo(departure.moveUp());
            assertThat(pathPositions.get(1)).isEqualTo(departure.moveUp().moveUp());
        }

        @Test
        void 하로_여러_칸_차이인_경우의_이동_경로를_반환한다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown().moveDown().moveDown();
            // when
            MoveContext moveContext = cha.askMoveContext(departure, destination);
            // then
            List<Position> pathPositions = moveContext.pathPositions();
            assertThat(pathPositions.get(0)).isEqualTo(departure.moveDown());
            assertThat(pathPositions.get(1)).isEqualTo(departure.moveDown().moveDown());
        }

        @Test
        void 좌로_여러_칸_차이인_경우의_이동_경로를_반환한다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft().moveLeft().moveLeft();
            // when
            MoveContext moveContext = cha.askMoveContext(departure, destination);
            // then
            List<Position> pathPositions = moveContext.pathPositions();
            assertThat(pathPositions.get(0)).isEqualTo(departure.moveLeft());
            assertThat(pathPositions.get(1)).isEqualTo(departure.moveLeft().moveLeft());
        }

        @Test
        void 우로_여러_칸_차이인_경우의_이동_경로를_반환한다() {
            // given
            Piece cha = new Cha(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight().moveRight().moveRight();
            // when
            MoveContext moveContext = cha.askMoveContext(departure, destination);
            // then
            List<Position> pathPositions = moveContext.pathPositions();
            assertThat(pathPositions.get(0)).isEqualTo(departure.moveRight());
            assertThat(pathPositions.get(1)).isEqualTo(departure.moveRight().moveRight());
        }
    }

    @Test
    void 차는_다른_진영의_기물만_공격할_수_있다() {
        // given
        Piece cha = new Cha(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveUp();
        // when
        MoveContext moveContext = cha.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.destinationRule())
            .isInstanceOf(BasicDestinationRule.class);
    }

    @Test
    void 차는_이동_경로에_기물이_없을_때_이동할_수_있다() {
        // given
        Piece cha = new Cha(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveUp();
        // when
        MoveContext moveContext = cha.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathRule())
            .isInstanceOf(EmptyPathRule.class);
    }

    @Test
    void 차는_본인의_식별자를_반환한다() {
        // given
        Piece cha = new Cha(Side.HAN);
        // when & then
        assertThat(cha.getType()).isEqualTo(PieceType.CHA);
    }
}
