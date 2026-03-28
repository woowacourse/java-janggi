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
import participant.ChoTurn;
import participant.HanTurn;
import participant.Turn;
import position.Position;

class JolByeongTest {

    private static final Position DEFAULT = new Position(3, 3);

    @Nested
    @DisplayName("초나라 졸 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class ChoCanMove {
        @Test
        void 상_1칸_이동할_수_있다() {
            // given
            Turn turn = new ChoTurn();
            Piece jol = new JolByeong(Side.CHO);
            Position departure = DEFAULT;
            Position destination = departure.moveUp();
            // when & then
            assertThatCode(() -> jol.askMoveContext(departure, destination, turn))
                    .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_이동할_수_있다() {
            // given
            Turn turn = new ChoTurn();
            Piece jol = new JolByeong(Side.CHO);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft();
            // when & then
            assertThatCode(() -> jol.askMoveContext(departure, destination, turn))
                    .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_이동할_수_있다() {
            // given
            Turn turn = new ChoTurn();
            Piece jol = new JolByeong(Side.CHO);
            Position departure = DEFAULT;
            Position destination = departure.moveRight();
            // when & then
            assertThatCode(() -> jol.askMoveContext(departure, destination, turn))
                    .doesNotThrowAnyException();
        }

        @Test
        void 상_2칸이_도착지인_경우_예외를_던진다() {
            // given
            Turn turn = new ChoTurn();
            Piece jol = new JolByeong(Side.CHO);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveUp();
            // when & then
            assertThatThrownBy(() -> jol.askMoveContext(departure, destination, turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 대각선이_도착지인_경우_예외를_던진다() {
            // given
            Turn turn = new ChoTurn();
            Piece jol = new JolByeong(Side.CHO);
            Position departure = DEFAULT;
            Position destination = departure.moveRightUp();
            // when & then
            assertThatThrownBy(() -> jol.askMoveContext(departure, destination, turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 하_1칸이_도착지인_경우_예외를_던진다() {
            // given
            Turn turn = new ChoTurn();
            Piece jol = new JolByeong(Side.CHO);
            Position departure = DEFAULT;
            Position destination = departure.moveDown();
            // when & then
            assertThatThrownBy(() -> jol.askMoveContext(departure, destination, turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }

    @Nested
    @DisplayName("한나라 병 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class HanCanMove {

        @Test
        void 하_1칸_이동할_수_있다() {
            // given
            Turn turn = new HanTurn();
            Piece byeong = new JolByeong(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown();
            // when & then
            assertThatCode(() -> byeong.askMoveContext(departure, destination, turn))
                    .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_이동할_수_있다() {
            // given
            Turn turn = new HanTurn();
            Piece byeong = new JolByeong(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft();
            // when & then
            assertThatCode(() -> byeong.askMoveContext(departure, destination, turn))
                .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_이동할_수_있다() {
            // given
            Turn turn = new HanTurn();
            Piece byeong = new JolByeong(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight();
            // when & then
            assertThatCode(() -> byeong.askMoveContext(departure, destination, turn))
                .doesNotThrowAnyException();
        }

        @Test
        void 하_2칸이_도착지인_경우_예외를_던진다() {
            // given
            Turn turn = new HanTurn();
            Piece byeong = new JolByeong(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown().moveDown();
            // when & then
            assertThatThrownBy(() -> byeong.askMoveContext(departure, destination, turn))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 대각선이_도착지인_경우_예외를_던진다() {
            // given
            Turn turn = new HanTurn();
            Piece byeong = new JolByeong(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRightDown();
            // when & then
            assertThatThrownBy(() -> byeong.askMoveContext(departure, destination, turn))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 상_1칸이_도착지일_경우_예외를_던진다() {
            // given
            Turn turn = new HanTurn();
            Piece byeong = new JolByeong(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp();
            // when & then
            assertThatThrownBy(() -> byeong.askMoveContext(departure, destination, turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 출발지와_도착지_사이에는_이동경로가_존재하지_않는다() {
        // given
        Turn turn = new HanTurn();
        Piece jolByeong = new JolByeong(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveDown();
        // when
        MoveContext moveContext = jolByeong.askMoveContext(departure, destination, turn);
        // then
        assertThat(moveContext.pathPositions()).hasSize(0);
    }

    @Test
    void 졸병은_다른_진영의_기물만_공격할_수_있다() {
        // given
        Turn turn = new HanTurn();
        Piece jolByeong = new JolByeong(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveDown();
        // when
        MoveContext moveContext = jolByeong.askMoveContext(departure, destination, turn);
        // then
        assertThat(moveContext.destinationRule())
            .isInstanceOf(BasicDestinationRule.class);
    }

    @Test
    void 졸병은_이동_경로에_기물이_없을_때_이동할_수_있다() {
        // given
        Turn turn = new HanTurn();
        Piece jolByeong = new JolByeong(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveDown();
        // when
        MoveContext moveContext = jolByeong.askMoveContext(departure, destination, turn);
        // then
        assertThat(moveContext.pathRule())
            .isInstanceOf(EmptyPathRule.class);
    }
}
