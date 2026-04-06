package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.pieces.exception.InvalidMoveException;
import domain.pieces.exception.PieceErrorMessage;
import domain.movepolicy.MoveContext;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import domain.position.Position;

class JolByeongTest {

    private static final Position DEFAULT = new Position(3, 3);
    private Piece choJol() {
        return new JolByeong(Side.CHO);
    }

    private Piece hanByeong() {
        return new JolByeong(Side.HAN);
    }

    @Nested
    @DisplayName("초나라 졸 행마법")
    class ChoJol {

        @Nested
        @DisplayName("이동 가능한 경우")
        class ValidMove {
            @Test
            void 상_1칸_이동할_수_있다() {
                // given
                Piece jol = choJol();
                Position departure = DEFAULT;
                Position destination = departure.moveUp();
                // when & then
                assertThatCode(() -> jol.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 좌_1칸_이동할_수_있다() {
                // given
                Piece jol = choJol();
                Position departure = DEFAULT;
                Position destination = departure.moveLeft();
                // when & then
                assertThatCode(() -> jol.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 우_1칸_이동할_수_있다() {
                // given
                Piece jol = choJol();
                Position departure = DEFAULT;
                Position destination = departure.moveRight();
                // when & then
                assertThatCode(() -> jol.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 궁성_내부_대각선_전진은_이동할_수_있다() {
                // given
                Piece jol = choJol();
                Position departure = new Position(7, 3);
                Position destination = departure.moveRightUp();
                // when & then
                assertThatCode(() -> jol.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("이동 불가능한 경우")
        class InvalidMove {
            @Test
            void 왼쪽_경계의_졸은_제자리_이동이면_예외를_던진다() {
                // given
                Piece jol = choJol();
                Position departure = new Position(3, 0);
                Position destination = departure;
                // when & then
                assertThatThrownBy(() -> jol.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.JOL_BYEONG_INVALID_MOVE.message());
            }

            @Test
            void 상_2칸이_도착지인_경우_예외를_던진다() {
                // given
                Piece jol = choJol();
                Position departure = DEFAULT;
                Position destination = departure.moveUp().moveUp();
                // when & then
                assertThatThrownBy(() -> jol.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.JOL_BYEONG_INVALID_MOVE.message());
            }

            @Test
            void 대각선이_도착지인_경우_예외를_던진다() {
                // given
                Piece jol = choJol();
                Position departure = DEFAULT;
                Position destination = departure.moveRightUp();
                // when & then
                assertThatThrownBy(() -> jol.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.JOL_BYEONG_INVALID_MOVE.message());
            }

            @Test
            void 하_1칸이_도착지인_경우_예외를_던진다() {
                // given
                Piece jol = choJol();
                Position departure = DEFAULT;
                Position destination = departure.moveDown();
                // when & then
                assertThatThrownBy(() -> jol.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.JOL_BYEONG_INVALID_MOVE.message());
            }

            @Test
            void 궁성_내부_대각선_후진은_이동할_수_없다() {
                // given
                Piece jol = choJol();
                Position departure = new Position(8, 4);
                Position destination = departure.moveLeftDown();
                // when & then
                assertThatThrownBy(() -> jol.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.JOL_BYEONG_INVALID_MOVE.message());
            }
        }

        @Nested
        @DisplayName("경계에 있는 경우")
        class Boundary {
            @Test
            void 왼쪽_끝_열의_졸도_상으로_이동할_수_있다() {
                // given
                Piece jol = choJol();
                Position departure = new Position(3, 0);
                Position destination = departure.moveUp();
                // when & then
                assertThatCode(() -> jol.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 오른쪽_끝_열의_졸도_상으로_이동할_수_있다() {
                // given
                Piece jol = choJol();
                Position departure = new Position(3, 8);
                Position destination = departure.moveUp();
                // when & then
                assertThatCode(() -> jol.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 상대_진영_끝줄의_졸은_좌우로_이동할_수_있다() {
                // given
                Piece jol = choJol();
                Position departure = new Position(9, 4);
                Position destination = departure.moveLeft();
                // when & then
                assertThatCode(() -> jol.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }
        }
    }

    @Nested
    @DisplayName("한나라 병 행마법")
    class HanByeong {

        @Nested
        @DisplayName("이동 가능한 경우")
        class ValidMove {
            @Test
            void 하_1칸_이동할_수_있다() {
                // given
                Piece byeong = hanByeong();
                Position departure = DEFAULT;
                Position destination = departure.moveDown();
                // when & then
                assertThatCode(() -> byeong.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 좌_1칸_이동할_수_있다() {
                // given
                Piece byeong = hanByeong();
                Position departure = DEFAULT;
                Position destination = departure.moveLeft();
                // when & then
                assertThatCode(() -> byeong.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 우_1칸_이동할_수_있다() {
                // given
                Piece byeong = hanByeong();
                Position departure = DEFAULT;
                Position destination = departure.moveRight();
                // when & then
                assertThatCode(() -> byeong.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 궁성_내부_대각선_전진은_이동할_수_있다() {
                // given
                Piece byeong = hanByeong();
                Position departure = new Position(2, 3);
                Position destination = departure.moveRightDown();
                // when & then
                assertThatCode(() -> byeong.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("이동 불가능한 경우")
        class InvalidMove {
            @Test
            void 오른쪽_경계의_병은_제자리_이동이면_예외를_던진다() {
                // given
                Piece byeong = hanByeong();
                Position departure = new Position(6, 8);
                Position destination = departure;
                // when & then
                assertThatThrownBy(() -> byeong.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.JOL_BYEONG_INVALID_MOVE.message());
            }

            @Test
            void 하_2칸이_도착지인_경우_예외를_던진다() {
                // given
                Piece byeong = hanByeong();
                Position departure = DEFAULT;
                Position destination = departure.moveDown().moveDown();
                // when & then
                assertThatThrownBy(() -> byeong.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.JOL_BYEONG_INVALID_MOVE.message());
            }

            @Test
            void 대각선이_도착지인_경우_예외를_던진다() {
                // given
                Piece byeong = hanByeong();
                Position departure = DEFAULT;
                Position destination = departure.moveRightDown();
                // when & then
                assertThatThrownBy(() -> byeong.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.JOL_BYEONG_INVALID_MOVE.message());
            }

            @Test
            void 상_1칸이_도착지일_경우_예외를_던진다() {
                // given
                Piece byeong = hanByeong();
                Position departure = DEFAULT;
                Position destination = departure.moveUp();
                // when & then
                assertThatThrownBy(() -> byeong.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.JOL_BYEONG_INVALID_MOVE.message());
            }

            @Test
            void 궁성_내부_대각선_후진은_이동할_수_없다() {
                // given
                Piece byeong = hanByeong();
                Position departure = new Position(1, 4);
                Position destination = departure.moveLeftUp();
                // when & then
                assertThatThrownBy(() -> byeong.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.JOL_BYEONG_INVALID_MOVE.message());
            }
        }

        @Nested
        @DisplayName("경계에 있는 경우")
        class Boundary {
            @Test
            void 왼쪽_끝_열의_병도_하로_이동할_수_있다() {
                // given
                Piece byeong = hanByeong();
                Position departure = new Position(6, 0);
                Position destination = departure.moveDown();
                // when & then
                assertThatCode(() -> byeong.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 오른쪽_끝_열의_병도_하로_이동할_수_있다() {
                // given
                Piece byeong = hanByeong();
                Position departure = new Position(6, 8);
                Position destination = departure.moveDown();
                // when & then
                assertThatCode(() -> byeong.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 상대_진영_끝줄의_병은_좌우로_이동할_수_있다() {
                // given
                Piece byeong = hanByeong();
                Position departure = new Position(0, 4);
                Position destination = departure.moveLeft();
                // when & then
                assertThatCode(() -> byeong.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }
        }
    }

    @Test
    void 출발지와_도착지_사이에는_이동경로가_존재하지_않는다() {
        // given
        Piece jolByeong = new JolByeong(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveDown();
        // when
        MoveContext moveContext = jolByeong.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathPositions()).hasSize(0);
    }

    @Test
    void 졸병은_다른_진영의_기물만_공격할_수_있다() {
        // given
        Piece jolByeong = new JolByeong(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveDown();
        // when
        MoveContext moveContext = jolByeong.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.destinationRule())
                .isInstanceOf(BasicDestinationRule.class);
    }

    @Test
    void 졸병은_이동_경로에_기물이_없을_때_이동할_수_있다() {
        // given
        Piece jolByeong = new JolByeong(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveDown();
        // when
        MoveContext moveContext = jolByeong.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathRule())
                .isInstanceOf(EmptyPathRule.class);
    }

    @Test
    void 졸병은_본인의_식별자를_반환한다() {
        // given
        Piece jolByeong = new JolByeong(Side.HAN);
        // when & then
        assertThat(jolByeong.getType()).isEqualTo(PieceType.JOL_BYEONG);
    }
}
